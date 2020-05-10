package com.appbase.uikit.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "KKExceptionHandler";

    private Context context;
    private Thread.UncaughtExceptionHandler defaultUEH;

    // 当前的日志记录类型为存储在SD卡下面
    private static final int MEMORY_TYPE = 1;
    // 当前的日志记录类型为存储在内存中
    private static final int SDCARD_TYPE = 0;

    // 当前的日志记录类型
    private int CURR_LOG_TYPE = SDCARD_TYPE;

    // 日志文件在内存中的路径(日志文件在安装目录中的路径)
    private String LOG_PATH_SDCARD_DIR;
    // 本服务输出的日志文件名称
    private static String CURR_INSTALL_LOG_NAME;

    // 日志文件在sdcard中的路径
    private String logServiceLogName = "uicrash.txt";
    private String LOG_PATH_MEMORY_DIR;

    // 内存中日志文件最大值，1K
    public static final int MEMORY_LOG_FILE_MAX_SIZE = 4 * 1024;

    private SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private OutputStreamWriter writer;
    private File file;
    private static File fileLog;
    String filename;
    private static final long QUICK_CRASH_ELAPSE = 10 * 1000;
    public static final int MAX_CRASH_COUNT = 3;
    private static final String DALVIK_XPOSED_CRASH = "Class ref in pre-verified class resolved to unexpected implementation";
    public static final String LOGGER = "log";
    private static String LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "log";

    public ExceptionHandler(Context context) {
        this.context = context;
        init();
        LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + context.getPackageName();
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    private void init() {
        LOG_PATH_MEMORY_DIR = context.getFilesDir().getAbsolutePath() + File.separator;
        LOG_PATH_SDCARD_DIR = LOG_PATH;
        CURR_LOG_TYPE = getCurrLogType();
        createLogDir();
    }

    /**
     * 获取错误的信息
     * @param arg1
     * @return
     */
    public static String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        // 1.获取当前程序的版本号. 版本的id
        String versioninfo = getVersionInfo();

        // 2.获取手机的硬件信息.
        String mobileInfo = getMobileInfo();

        // 3.把错误的堆栈信息 获取出来
        String errorinfo = getErrorInfo(ex);

        recordLogServiceLog(versioninfo + "\n" + mobileInfo + "\n" + errorinfo);
        ex.printStackTrace();

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //干掉当前的程序
            Log.e(TAG, "uncaughtException");
            defaultUEH.uncaughtException(t, ex);
        }
    }

    private void recordLogServiceLog(String msg) {
        try {
            Log.e("Exception", "catch :"+msg);
            // 字节流变为字符流
            writer = new OutputStreamWriter(new FileOutputStream(fileLog,false));
            Date time = new Date();
            writer.write(myLogSdf.format(time) + " : " + msg);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前应存储在内存中还是存储在SDCard中
     *
     * @return
     */
    public int getCurrLogType() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
            Log.e(TAG, "MEMORY_TYPE");
            return MEMORY_TYPE;
        } else {
            Log.e(TAG, "SDCARD_TYPE");
            return SDCARD_TYPE;
        }
    }


    /**
     * 获取手机的版本信息
     *
     * @return
     */
    public  String getVersionInfo() {
        try {
            PackageManager pm = context .getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            String versionInfo = " Version:" + info.versionName + " Build:" + info.versionCode;
            return versionInfo;
        } catch (Exception e) {
            Log.e(TAG, "load VersionInfo error");
            return "版本号未知";
        }
    }

    /**
     * 获取手机的硬件信息
     * @return
     */
    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        // 通过反射获取系统的硬件信息
        try {

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                // 暴力反射 ,获取私有的信息
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                if( sb.toString().getBytes().length < 512 )
                {
                    sb.append(name + "=" + value);
                    sb.append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getLogPath() {
        String logPath = null;
        if (fileLog != null) {
            logPath = fileLog.getAbsolutePath();
        }
        return logPath;
    }

    /**
     * 创建日志目录
     */
    private void createLogDir() {
        filename = CURR_LOG_TYPE == MEMORY_TYPE ? LOG_PATH_MEMORY_DIR : LOG_PATH_SDCARD_DIR;
        Log.e(TAG, "filename: " + filename);

        file = new File(filename);
        boolean mkOk;
        if (!file.isDirectory()) {
            mkOk = file.mkdirs();
            if (!mkOk) {
                mkOk = file.mkdirs();
            }
        }

        CURR_INSTALL_LOG_NAME = filename + File.separator + logServiceLogName;
        fileLog = new File(CURR_INSTALL_LOG_NAME);
    }
    public static void init(Context context){
        ExceptionHandler exceptionHandler = new ExceptionHandler(context);
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }

}

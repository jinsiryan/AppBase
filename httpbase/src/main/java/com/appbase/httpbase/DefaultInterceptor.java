package com.appbase.httpbase;


import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * 添加默认参数
 * multipart 没有自动添加 transID
 * Created by fanxiaoyong on 2017/3/21.
 */

public class DefaultInterceptor implements Interceptor {

    private static final String TAG = "DefaultInterceptor";

    private static DefaultInterceptor ins;

    private Map<String, String> paramMap = Collections.synchronizedMap(new HashMap<String, String>());
    private String transId;


    private DefaultInterceptor() {

    }

    public void refreshPublicParam() {
        paramMap.put("deviceType",  "ANDROID");
        paramMap.put("deviceModel", Build.MODEL);
        paramMap.put("brand", Build.BRAND);
        paramMap.put("appName", "TEST");
//        paramMap.put("version", BuildConfig.VERSION_NAME + "");
        paramMap.put("lang", Locale.getDefault().toString());
        paramMap.put("token", Config.INSTANCE.getToken());
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public static DefaultInterceptor get() {
        if (ins == null) {
            ins = new DefaultInterceptor();
        }
        return ins;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request req = chain.request();
        Request request = null;
        try {
            request = addDefaultParameter(req);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            request = req;
            e.printStackTrace();
        }
        transId = null;
        return chain.proceed(request);
    }

    private Request addDefaultParameter(Request request)
            throws Exception {
        Request.Builder builder;
        RequestBody body = request.body();
        if (body != null) {
            MediaType mediaType = body.contentType();
            if (mediaType != null && "application".equals(mediaType.type()) && "json".equals(mediaType.subtype())) {
                RequestBody requestBody = addJsonParamter(request, mediaType);
                return request.newBuilder().post(requestBody).build();
            }
        }

        String method = request.method();
        if (method.equals("GET")) {
            builder = appendDefaultParameterToUrl(request);
        } else if (method.equals("POST")) {
            builder = addDefaultParameterToBody(request);
        } else {
            builder = request.newBuilder();
        }
        return builder.build();
    }

    private RequestBody addJsonParamter(Request request, MediaType mediaType) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sink sink = Okio.sink(baos);
        BufferedSink bufferedSink = Okio.buffer(sink);
        // Write old params
        request.body().writeTo(bufferedSink);
        bufferedSink.emit();
        String s = new String(baos.toByteArray());
        JSONObject js = null;
        try {
            js = new JSONObject(s);
            refreshPublicParam();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                if (!js.has(entry.getKey())) {
                    js.put(entry.getKey(), entry.getValue());
                }
            }
            s = js.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(
                mediaType,
                s.getBytes());
    }

    private Request.Builder appendDefaultParameterToUrl(Request request) {
        Request.Builder builder;
        HttpUrl originalHttpUrl = request.url();
        HttpUrl.Builder paramerBuilder = originalHttpUrl.newBuilder();
        refreshPublicParam();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            if (originalHttpUrl.queryParameter(key) == null) {
                paramerBuilder.addQueryParameter(key, entry.getValue());
            }
        }

        builder = request.newBuilder().url(paramerBuilder.build());
        return builder;
    }

    private Request.Builder addDefaultParameterToBody(Request request) throws IOException {
        Request.Builder builder;
        RequestBody newRequestBody;
        MediaType mediaType = null;
        RequestBody body = request.body();
        refreshPublicParam();
        if (body == null || body.contentLength() == 0) {
            return appendDefaultParameterToUrl(request);
        } else {
            mediaType = body.contentType();
            if (mediaType != null && "multipart".equals(mediaType.type()) && "form-data".equals(mediaType.subtype())) {
                MultipartBody.Builder bodyBuild = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    bodyBuild.addPart(MultipartBody.Part.createFormData(entry.getKey(), entry.getValue()));
                }
                if (body instanceof MultipartBody) {
                    MultipartBody multipartBody = (MultipartBody) body;
                    for (MultipartBody.Part part : multipartBody.parts()) {
                        bodyBuild.addPart(part);
                    }

                }
                newRequestBody = bodyBuild.build();
            } else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Sink sink = Okio.sink(baos);
                BufferedSink bufferedSink = Okio.buffer(sink);
                // Write old params
                body.writeTo(bufferedSink);
                // write to buffer additional params
                StringBuilder sb = new StringBuilder();
                sb.append("&");
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                bufferedSink.writeString(sb.toString(), Charset.defaultCharset());
                newRequestBody = RequestBody.create(
                        mediaType,
                        bufferedSink.buffer().readUtf8());
            }
        }

        builder = request.newBuilder().post(newRequestBody);
        return builder;
    }
}

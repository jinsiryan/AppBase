import android.content.Context
import com.appbase.httpbase.GsonUtil
import com.appbase.httpbase.HttpCommonInterceptor
import com.appbase.httpbase.R
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

object HttpManager {
    private const val DEFAULT_TIME_OUT = 5 //超时时间 5s
    private const val DEFAULT_READ_TIME_OUT = 10

    private lateinit var client: OkHttpClient
    lateinit var retrofit: Retrofit

    fun init(context: Context){

        // 创建 OKHttpClient
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(
            DEFAULT_TIME_OUT.toLong(),
            TimeUnit.SECONDS
        ) //连接超时时间

        builder.writeTimeout(
            DEFAULT_READ_TIME_OUT.toLong(),
            TimeUnit.SECONDS
        ) //写操作 超时时间

        builder.readTimeout(
            DEFAULT_READ_TIME_OUT.toLong(),
            TimeUnit.SECONDS
        ) //读操作超时时间
        // 添加公共参数拦截器


        // 添加公共参数拦截器
        val commonInterceptor = HttpCommonInterceptor.Builder()
            .addHeaderParams("Accept", "application/json")
            .addHeaderParams("Content-Type", "application/json")
            .addHeaderParams("DeviceType", "android")
            .build()
        builder.addInterceptor(commonInterceptor)
        builder.sslSocketFactory(createSSLSocketFactory(), TrustAllCerts())
        builder.hostnameVerifier(TrustAllHostnameVerifier())

        builder.addInterceptor(ChuckInterceptor(context))
        client = builder.build()
        retrofit = createRetrofit(context.getString(R.string.base_url))
    }
    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonUtil.GSON))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }
}

private class TrustAllCerts : X509TrustManager {
    @Throws(CertificateException::class)
    override fun checkClientTrusted(
        chain: Array<X509Certificate>,
        authType: String
    ) {
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(
        chain: Array<X509Certificate>,
        authType: String
    ) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate?> {
        return arrayOfNulls(0)
    }
}

private class TrustAllHostnameVerifier : HostnameVerifier {
    override fun verify(
        hostname: String,
        session: SSLSession
    ): Boolean {
        return true
    }
}

private fun createSSLSocketFactory(): SSLSocketFactory? {
    var ssfFactory: SSLSocketFactory? = null
    try {
        val sc = SSLContext.getInstance("TLS")
        sc.init(
            null,
            arrayOf<TrustManager>(TrustAllCerts()),
            SecureRandom()
        )
        ssfFactory = sc.socketFactory
    } catch (e: Exception) {
    }
    return ssfFactory
}
package byh.adong.modi.service

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {
    val BASE_URL = "http://112.158.99.134:3000"

    var retrofit: Retrofit? = null
    val httpclient = OkHttpClient.Builder()
    val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    fun <T> creatService(serviceClass: Class<T>): T {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpclient.addInterceptor(interceptor)
        retrofit = builder
                .client(httpclient.build())
                .build()
        return retrofit!!.create(serviceClass)
    }

    fun <T> creatService(serviceClass: Class<T>, authToken: String): T {
        val interceptorlevel = HttpLoggingInterceptor()
        interceptorlevel.level = HttpLoggingInterceptor.Level.BODY
        if (!TextUtils.isEmpty(authToken)) {
            val interceptor = AuthenticationInterceptor(authToken)
            if (!httpclient.interceptors().contains(interceptor)) {
                httpclient.addInterceptor(interceptor)
                httpclient.addInterceptor(interceptorlevel)
                builder.client(httpclient.build())
                retrofit = builder.build()
                return retrofit!!.create(serviceClass)
            }
        }
        return retrofit!!.create(serviceClass)
    }
}

class AuthenticationInterceptor(val authToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val original = chain!!.request()
        val builder = original.newBuilder()
                .header("Authorization", authToken)
        val request = builder.build()

        return chain.proceed(request)
    }

}

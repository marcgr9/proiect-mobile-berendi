package ro.marc.android.util

import android.content.Context
import android.widget.Toast
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Utils {

    fun toast(ctx: Context, text: String) {
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
    }

    fun getRetrofit(baseUrl: String, ctx: Context? = null): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .apply {
                if (ctx == null) return@apply

                addInterceptor {
                    val req = it.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            ctx.getSharedPreferences("app", Context.MODE_PRIVATE).getString("JWT", "")!!,
                        )
                        .build()

                    return@addInterceptor it.proceed(req)
                }
            }
            .build()

        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

}

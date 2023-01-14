package ro.marc.android.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ro.marc.android.activity.login.Login
import ro.marc.android.data.api.dto.EntityDTO
import ro.marc.android.data.db.entity.DbEntity
import ro.marc.android.data.model.Entity
import ro.marc.android.databinding.CompItemBinding
import java.text.SimpleDateFormat
import java.util.*
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

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
    }

    fun logout(ctx: Context) {
        ctx.getSharedPreferences("app", Context.MODE_PRIVATE).edit()
            .clear()
            .commit()

        ctx.startActivity(Intent(
            ctx,
            Login::class.java,
        ))
    }

    fun formatDate(date: Date): String = SimpleDateFormat("dd-MM-yyyy").format(date)

    fun getDateFromString(date: String) = SimpleDateFormat("dd-MM-yyyy").parse(date)

    fun fillEntityCard(item: CompItemBinding, entity: Entity) {
        item.name.text = entity.name
        item.quantity.text = entity.quantity.toString()
        item.date.text = formatDate(entity.date)
        item.favourite.visibility = if (entity.isFavourite) View.VISIBLE else View.INVISIBLE
    }

    fun asEntity(entity: DbEntity) = Entity(
        entity.localId,
        entity.id,
        entity.name,
        entity.quantity,
        entity.date,
        entity.isFavourite
    )

    fun asEntity(dto: EntityDTO) = Entity(
        null,
        dto.id,
        dto.name,
        dto.quantity,
        dto.date,
        dto.isFavourite
    )

}

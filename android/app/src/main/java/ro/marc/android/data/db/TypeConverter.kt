package ro.marc.android.data.db

import androidx.room.TypeConverter
import ro.marc.android.util.Utils
import java.util.*

object TypeConverter {

    @TypeConverter
    fun fromDate(date: Date) = Utils.formatDate(date)

    @TypeConverter
    fun toDate(string: String) = Utils.getDateFromString(string)!!

}

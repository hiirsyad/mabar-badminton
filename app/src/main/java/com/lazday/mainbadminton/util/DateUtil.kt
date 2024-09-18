package com.lazday.mainbadminton.util

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

private const val FORMAT_TIME = "dd/MM/yyyy hh:mm:ss"

//fun dateToString(date: Long?): String? {
//    val calendar = Calendar.getInstance()
//    calendar.timeInMillis = date!!
//    return calendar[Calendar.DAY_OF_MONTH].toString() + "/" +
//            (calendar[Calendar.MONTH] + 1) + "/" +
//            calendar[Calendar.YEAR] + " " +
//            calendar[Calendar.HOUR] + ":" +
//            calendar[Calendar.MINUTE] + ":" +
//            calendar[Calendar.SECOND]
//}

fun dateToString(date: Long?): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date!!
    return calendar[Calendar.HOUR].toString() + ":" +
            calendar[Calendar.MINUTE] + ":" +
            calendar[Calendar.SECOND] + " "  +
            calendar[Calendar.DAY_OF_MONTH] + "/" +
            (calendar[Calendar.MONTH] + 1) + "/" +
            calendar[Calendar.YEAR]
}

fun timeToString(date: Long?): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date!!
    val amPm = if (calendar[Calendar.AM_PM] == calendar[Calendar.AM]) "am" else "pm"
    return calendar[Calendar.HOUR].toString() + ":" +
            calendar[Calendar.MINUTE] + amPm
//            calendar[Calendar.MINUTE] + ":" +
//            calendar[Calendar.SECOND] + " " + amPm

}

fun dateTimeToString(date: Long?): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date!!
    val amPm = if (calendar[Calendar.AM_PM] == calendar[Calendar.AM]) "am" else "pm"
    return calendar[Calendar.DAY_OF_MONTH].toString() + "/" +
            (calendar[Calendar.MONTH] + 1) + "/" +
            calendar[Calendar.YEAR] + " " +
            calendar[Calendar.HOUR] + ":" +
            calendar[Calendar.MINUTE] + amPm

}

fun dateToLong(
    date: String,
    isCompleted: Boolean = false
): Long {
    val dateFormat = SimpleDateFormat(FORMAT_TIME, Locale.getDefault())
//    return if (isCompleted) dateFormat.parse(date).time else dateFormat.parse("$date 00:00:00").time
    return dateFormat.parse(
        if (isCompleted) date else "$date 00:00:00"
    ).time
}

fun dateToDialog(context: Context?, datePicker: OnDateSetListener?): DatePickerDialog? {
    val calendar = Calendar.getInstance()
    return DatePickerDialog(
        context!!,
        datePicker,
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    )
}

fun dateToString(year: Int, monthOfYear: Int, dayOfMonth: Int): String? {
    return dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
}

fun dateToday(): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    return calendar[
        Calendar.DAY_OF_MONTH].toString() + "/" +
            (calendar[Calendar.MONTH] + 1) + "/" +
            calendar[Calendar.YEAR]
}

fun dateCompleted(): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    return calendar[
        Calendar.DAY_OF_MONTH].toString() + "/" +
            (calendar[Calendar.MONTH] + 1) + "/" +
            calendar[Calendar.YEAR] + " " +
            calendar[Calendar.HOUR] + ":" +
            calendar[Calendar.MINUTE] + ":" +
            calendar[Calendar.SECOND]
}
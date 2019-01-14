package com.yayanheryanto.footballmatchschedule.util

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun dateToStr(date: Date?):String{
    val format = SimpleDateFormat("EEE, dd MMM yyyy")
    return format.format(date)
}

fun strToDate(strDate: String?, pattern: String = "EEE MMM dd HH:mm:ss zzzz yyyy"):Date{
    val format = SimpleDateFormat(pattern, Locale.ENGLISH)
    val date = format.parse(strDate)

    return date
}

fun changeFormatDate(date: Date?): String? = with(date ?: Date()){
    SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()).format(this)
}

fun formatTime(time:String?):String{
    val format = SimpleDateFormat("HH:mm:ss")
    format.timeZone = TimeZone.getTimeZone("GMT")
    val dateTime = format.parse(time)
    val newTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return newTimeFormat.format(dateTime)
}
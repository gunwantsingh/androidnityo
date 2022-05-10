package com.bb.vib.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        private val apiDateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.UK)

        private val apiDateFormatTime: DateFormat =
            SimpleDateFormat("HH:mm", Locale.UK)//24 hour time

        private val apiDateFormatDayMonthYear: DateFormat =
            SimpleDateFormat("EEEE, dd MMM yyyy", Locale.UK)//MOnday,19 June 2021


        private val apiDateFormatTimeAmPM: DateFormat =
            SimpleDateFormat("hh:mm", Locale.UK)//Am and Pm  time

        private val appDateFormat: DateFormat =
//            SimpleDateFormat("dd MMM,yyyy", Locale.UK)
            SimpleDateFormat("yyyy-MM-dd", Locale.UK)  //2013-11-11

        private val newapidateformate2: DateFormat =
            SimpleDateFormat("dd-MM-yyyy", Locale.UK)

        private val newapidateformatedot: DateFormat =
            SimpleDateFormat("dd.MM.yyyy", Locale.UK)

        private val appDateDobFormat: DateFormat =
            SimpleDateFormat("dd MMMM yyyy", Locale.UK)

        private val appDateBookingFormat: DateFormat =
            SimpleDateFormat("MMM dd, yy", Locale.UK)

        private val appDateSelectFormat: DateFormat =
            SimpleDateFormat("dd MMMM", Locale.UK)

        fun getAppDateFromDate(date: Date?): String? {
            return appDateFormat.format(date!!)
        }

        fun getApiDateFromDate(date: Date): String {
            return apiDateFormat.format(date)
        }

        fun getAppDateFromApiDate(apiDate: String?): String? {
            try {
                val date = apiDateFormat.parse(apiDate!!)!!
                return appDateDobFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "NA"
        }

        fun getAppDateFromCalendarDate(calendarDate: Date?): String? {
            try {
                return appDateBookingFormat.format(calendarDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "NA"
        }

        fun getAppSelectDateFromCalendarDate(calendarDate: Date?): String? {
            try {
                return appDateSelectFormat.format(calendarDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "NA"
        }

        fun getDashDateFromCalendarDate(calendarDate: Date?): String? {
            try {
                return appDateFormat.format(calendarDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "NA"
        }

        fun getApiDateFromCalender(apiDate: String?): String? {
            try {
                val date = appDateFormat.parse(apiDate!!)!!
                return newapidateformate2.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "NA"
        }


        fun getnewapiformate(apiDate: String?): String? {
            try {
                val date = apiDateFormat.parse(apiDate!!)!!
                return apiDateFormatTime.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "NA"
        }
        fun getnewapiformateinAmPm(apiDate: String?): String? {
            try {
                val date = apiDateFormat.parse(apiDate!!)!!
                return apiDateFormatTimeAmPM.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "NA"
        }
        fun getnewapiformateMonthDay(apiDate: String?): String? {
            try {
                val date = newapidateformatedot.parse(apiDate!!)!!
                return apiDateFormatDayMonthYear.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "NA"
        }

    }


}
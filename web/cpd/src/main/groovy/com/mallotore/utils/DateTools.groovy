package com.mallotore.utils

class DateTools {

	static Date convertToYYYYMMDDHHMMSS(String date){
		Date.parse( "yyyy-MM-dd'T'HH:mm:ss", date)
	}
}
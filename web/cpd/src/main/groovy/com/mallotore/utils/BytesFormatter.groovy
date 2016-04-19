package com.mallotore.utils

class BytesFormatter {

	static final LABELS = [ ' bytes', 'KB', 'MB', 'GB', 'TB' ]

	static String format = { bytes ->

		def label = LABELS.find {
			if( bytes < 1024 ) {
				true
			}
			else {
				bytes /= 1024  
				false
			}
		}
		"${new java.text.DecimalFormat( '0.##' ).format( bytes )}$label"
	}

	static String formatToMB(long value) {
        return "${new Long(value / 1024)}MB"
    }
}
package com.mallotore.utils

class BytesFormatter {

	static final LABELS = [ ' bytes', 'KB', 'MB', 'GB', 'TB' ]

	static format = { spaceInBytes ->

		def label = LABELS.find {
			if( spaceInBytes < 1024 ) {
				true
			}
			else {
				spaceInBytes /= 1024  
				false
			}
		}
		"${new java.text.DecimalFormat( '0.##' ).format( spaceInBytes )}$label"
	}

	static String formatToMB(long value) {
        return "${new Long(value / 1024)}MB"
    }
}
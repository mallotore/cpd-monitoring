package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class ActiveServices {
    boolean apache
    boolean mysql
    boolean iis
    boolean tomcat
    boolean ftp
    boolean http
    boolean oracle
    boolean sql
}
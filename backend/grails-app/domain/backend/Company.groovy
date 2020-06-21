package backend

import grails.rest.*

@Resource(uri='/company')
class Company {

String name
String segment
static hasMany = [stocks: Stock]

    static constraints = {
        name size: 5..40, blank: false, unique: true
        segment size: 5..40, blank: false
    }

    String toString() {
        name
    }
}

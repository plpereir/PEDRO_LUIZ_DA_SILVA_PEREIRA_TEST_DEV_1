package backend
import grails.rest.*

@Resource(uri='/stock')
class Stock {

static belongsTo = [company: Company]
double price
Date priceDate

    static constraints = {
        price  blank: false
        priceDate  blank: false, date: true
    }
    
    String toString() {
        name
    }
}

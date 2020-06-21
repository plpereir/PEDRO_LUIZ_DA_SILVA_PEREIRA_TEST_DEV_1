package backend
import grails.rest.*

@Resource(uri='/stock')
class Stock {

static belongsTo = [company: Company]
float price
Date priceDate

    static constraints = {
        price min: 1, blank: false
        priceDate  blank: false, date: true
    }
    
    String toString() {
        name
    }
}

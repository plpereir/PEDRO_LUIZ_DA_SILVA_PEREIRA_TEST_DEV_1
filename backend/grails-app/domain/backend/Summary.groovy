package backend

import grails.rest.*

@Resource(uri='/api/summary', formats=['json', 'xml'])
class Summary {

    String name
    String segment
    double price
    static constraints = {
        name size: 5..40, blank: false, unique: true
        segment size: 5..40, blank: false
        price  blank: false
    }

    Summary(name, segment, price)
    {
        this.name = name
        this.segment = segment
        this.price = price
    }
}

package backend

import grails.gorm.services.Service

@Service(Stock)
interface StockService {

    Stock get(Serializable id)

    List<Stock> list(Map args)

    Long count()

    void delete(Serializable id)

    Stock save(Stock stock)

}
package backend

import grails.gorm.transactions.Transactional

import java.util.Date
import groovy.sql.Sql

@Transactional
class PrintService {

    def serviceMethod() {

    }
    void getStocks(String company, int numbersOfHoursUntilNow)
    {
        //Set Database
        def url = 'jdbc:h2:mem:devDb'
        def user = 'sa'
        def password = ''
        def driver = 'org.h2.Driver'
        def sql = Sql.newInstance(url, user, password, driver)

        //Read data
        sql.eachRow("SELECT stock.price_date as TIME, TO_CHAR(stock.price,'9990D00')  as Stock FROM stock, company WHERE (stock.company_id = company.id) and (company.name like 'Apple') and (price_date >= '2020-06-22 17:57:00') ORDER BY price_date DESC")   
        { p -> println "Time = ${p[0]}, Stock = ${p[1]}"}
        // close connection
        sql.close()

                Date yesterday = new Date()
                print "Data: "+yesterday

                Calendar cal = Calendar.getInstance()
                cal.setTime(yesterday)
                cal.set(Calendar.HOUR_OF_DAY,-45);
                yesterday = cal.getTime()

                print "Data -45"+yesterday


        print " "
        print " getStocks - "
        print " company: "+company
        print " numbersOfHoursUntilNow: "+numbersOfHoursUntilNow
        print " "
    }
}

package backend

import grails.rest.*;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar; 

    import groovy.sql.Sql;

import grails.gorm.transactions.Transactional

@RestController
@RequestMapping("/api")

@Transactional
class QuoteService {

    @RequestMapping(value = "/quotes/{company}/{numbersOfHoursUntilNow}")
    @ResponseBody
    Map<String,String> getStocksByCompanyByHours(
    @PathVariable String company, @PathVariable int numbersOfHoursUntilNow) {
        Map<String,String> example = new HashMap<String,String>();
        example.put( "Company", company);
        example.put( "Quotes from the last", Integer.toString(numbersOfHoursUntilNow));

        //Set Database
        def url = 'jdbc:h2:mem:devDb'
        def user = 'sa'
        def password = ''
        def driver = 'org.h2.Driver'
        def sql = Sql.newInstance(url, user, password, driver)

        Date yesterday = new Date()
        Calendar cal = Calendar.getInstance()
        cal.setTime(yesterday)
        cal.set(Calendar.HOUR_OF_DAY,-numbersOfHoursUntilNow);
        cal.set(Calendar.SECOND,0)
        cal.set(Calendar.MILLISECOND,0)
        yesterday = cal.getTime()

        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dia = (sdff.format(yesterday));

        Date dateStart = new Date()

        //Read data
        sql.eachRow("SELECT stock.price_date as TIME, TO_CHAR(stock.price,'9990D00')  as Stock FROM stock, company WHERE (stock.company_id = company.id) and (company.name like '"+company+"') and (price_date >= '"+dia+"') ORDER BY price_date DESC")   
        { p ->  example.put( sdff.format(p[0]) , p[1]); }
        // close connection
    
        //Read data
        sql.eachRow("SELECT count(stock.id) as Count FROM stock, company WHERE (stock.company_id = company.id) and (company.name like '"+company+"') and (price_date >= '"+dia+"') ")   
        { p ->  String retrieved = p[0];
                example.put( "The number of quotes retrieved", retrieved);}
        // close connection
        sql.close()

        Date dateEnd = new Date()

        long diferenceMiliseconds = (dateEnd.getTime() - dateStart.getTime()) 

        example.put( "The executed total time, in millisseconds", Long.toString(diferenceMiliseconds));

        Map<String, String> treeMap = new TreeMap<String, String>(example);

        return treeMap
    }


     void getStocks(String company, int numbersOfHoursUntilNow)
    {

        this.serviceMethod(company,numbersOfHoursUntilNow)
      
    }

       void serviceMethod(String company, int numbersOfHoursUntilNow) {
        //Set Database
        def url = 'jdbc:h2:mem:devDb'
        def user = 'sa'
        def password = ''
        def driver = 'org.h2.Driver'
        def sql = Sql.newInstance(url, user, password, driver)

        Date yesterday = new Date()
        Calendar cal = Calendar.getInstance()
        cal.setTime(yesterday)
        cal.set(Calendar.HOUR_OF_DAY,-numbersOfHoursUntilNow);
        cal.set(Calendar.SECOND,0)
        cal.set(Calendar.MILLISECOND,0)
        yesterday = cal.getTime()

        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dia = (sdff.format(yesterday));

        Date dateStart = new Date()

        print " "

        print "Quotes from the last "+numbersOfHoursUntilNow+" hours, from each minute, for the "+company+" "

        //Read data
        sql.eachRow("SELECT stock.price_date as TIME, TO_CHAR(stock.price,'9990D00')  as Stock FROM stock, company WHERE (stock.company_id = company.id) and (company.name like '"+company+"') and (price_date >= '"+dia+"') ORDER BY price_date DESC")   
        { p -> println " Time = ${p[0]} -> Quote = ${p[1]} "}
        // close connection
    
        print " "

    
        //Read data
        sql.eachRow("SELECT count(stock.id) as Count FROM stock, company WHERE (stock.company_id = company.id) and (company.name like '"+company+"') and (price_date >= '"+dia+"') ")   
        { p -> println " The number of quotes retrieved: ${p[0]} "}
        // close connection
        sql.close()

        print " "

        Date dateEnd = new Date()

        long diferenceMiliseconds = (dateEnd.getTime() - dateStart.getTime()) 

        print " The executed total time, in millisseconds: "+diferenceMiliseconds+" "

    }
}

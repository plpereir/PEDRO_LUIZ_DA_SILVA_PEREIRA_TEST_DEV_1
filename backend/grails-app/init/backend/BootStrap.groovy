package backend
import groovy.transform.CompileStatic
import java.util.Date
import groovy.sql.Sql


@CompileStatic
class BootStrap {

    StockService stockService
    CompanyService companyService
    SummaryService summaryService
    def init = { servletContext ->
        
        new Company(name:'Apple',segment:'technology').save()
        new Company(name:'Volkswagen',segment:'vehicles').save()
        new Company(name:'Amazon',segment:'technology').save()
        
        def url = 'jdbc:h2:mem:devDb'
        def user = 'sa'
        def password = ''
        def driver = 'org.h2.Driver'
        def sql = Sql.newInstance(url, user, password, driver)
        
        
         /*list by Companies*/
        for (int i = 1; i < 4; i++) {
            /*list the last 30 days: int j = 30; j > -1; j--*/
            for (int j = 30; j > -1; j--) {

                Date yesterday2 = new Date()
                Calendar cal2 = Calendar.getInstance()
                cal2.setTime(yesterday2)
                int hours = cal2.get(Calendar.HOUR_OF_DAY);
                int minutes = cal2.get(Calendar.MINUTE);                
                cal2.set(Calendar.SECOND,0)
                cal2.set(Calendar.MILLISECOND,0)

                Random rnd = new Random()

                if (j==0)
                {
                    if ((hours>=10) && (hours<=17))
                    {
                        for (int h=10; h < hours+1; h++)
                        {
                            for (int m=0; m < minutes+1; m++)
                            {
                                cal2.add(Calendar.DATE, -j)
                                cal2.set(Calendar.HOUR_OF_DAY,h)
                                cal2.set(Calendar.MINUTE,m)

                                yesterday2 = cal2.getTime()

                                //select STDDEV(price) as "STDDEV" from stock
                                sql.execute( "insert into STOCK (VERSION, COMPANY_ID, PRICE, PRICE_DATE) values (?, ?, ?, ?)".toString(), 0, i,rnd.nextInt(50)+Math.random(), yesterday2)
                            }
                        }
                    } else
                    {
                        break;
                    }
                }else
                {
                    /*list set minutes: int k = 59; k > -1; k-- */
                    for (int k = 59; k > -1; k--) {
                        /*list set hours: int l = 18; l > 9; l-- */
                        for (int l = 17; l > 9; l--) {
                            Date yesterday = new Date()
                            Calendar cal = Calendar.getInstance()
                            cal.setTime(yesterday)
                            cal.add(Calendar.DATE, -j)
                            cal.set(Calendar.HOUR_OF_DAY,l)
                            cal.set(Calendar.MINUTE,k)
                            cal.set(Calendar.SECOND,0)
                            cal.set(Calendar.MILLISECOND,0)

                            yesterday = cal.getTime()

                            //select STDDEV(price) as "STDDEV" from stock
                            sql.execute( "insert into STOCK (VERSION, COMPANY_ID, PRICE, PRICE_DATE) values (?, ?, ?, ?)".toString(), 0, i,rnd.nextInt(50)+Math.random(), yesterday)
                        }
                    }
                }
            }
        }
        
        //Read data
        sql.eachRow("select company.name as Name, company.segment as Segment, TO_CHAR(STDDEV(stock.price),'9990D00') as StandardDeviation from stock, company where (company.id=stock.company_id) group by Name order by Name;")   
        { p -> new Summary(name:p[0], segment:p[1], price: p[2]).save() } 
       // Double d = new Double("0.0");
       // new Summary(name:{p[0]}, segment:{p[1]}, price: d).save()}
            // close connection
        sql.close()

    
    }
    def destroy = {
    }
}

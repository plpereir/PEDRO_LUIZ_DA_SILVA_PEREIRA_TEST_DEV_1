package backend
import groovy.transform.CompileStatic
import java.util.Date
import groovy.sql.Sql

@CompileStatic
class BootStrap {

    PrintService printService
    StockService stockService
    CompanyService companyService
    def init = { servletContext ->
        
        Company a = new Company(name:'Apple',segment:'technology')
        Company b = new Company(name:'Volkswagen',segment:'vehicles')
        Company c = new Company(name:'Amazon',segment:'technology')

        companyService.save(a).save()
        companyService.save(b).save()
        companyService.save(c).save()
        
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
        
        sql.close()
        printService.getStocks("Ford", 39)
    }
    def destroy = {
    }
}

#THE 72H APPLICATION FOR DEVELOPER POSITION
informations: https://bitbucket.org/nitryx-team/rh-dev-1/wiki/Home



3.1 - TASK 1
- Grails: Grails application running at http://localhost:8080 in environment: development;
- npm i: audited 1555 packages in 34.236s;
- npm start >frontend@0.0.0 start ...PEDRO_LUIZ_DA_SILVA_PEREIRA_TEST_DEV_1/frontend >ng serve

3.2 - TASK 2
#APIs:
- Company -> http://localhost:8080/api/company
- Stock -> http://localhost:8080/api/stock
- Summary -> http://localhost:8080/api/summary
- getStocks -> http://localhost:8080/api/quotes/{company}/{numbersOfHoursUntilNow} 
#Console:
/grails-app/services/backend/QuoteService.groovy.getStocks(company,numbersOfHoursUntilNow)
#BootStrap.groovy:
- call getStocks(company,numbersOfHoursUntilNow)
- load initial data (Company and Stock)

3.3 - TASK 3
- button called "GET COMPANIES"
- For each company shows:
    - Name
    - Segment
    - Standard Deviation for its quotes for all time.

3.4 - TASK 4
 - npx cypress open
 - It looks like this is your first time using Cypress: 4.0.1
  ✔  Verified Cypress! /Users/plpereir/Library/Caches/Cypress/4.0.1/Cypress.app
 - Opening Cypress...

describe('Test to be fulfilled by the candidate', () => {
  it('push the button implemented on task #3 and shows the company names', () => {
      cy.visit('http://localhost:4200')
      cy.get('.btn-primary').click() 
      cy.contains('Amazon')
  })






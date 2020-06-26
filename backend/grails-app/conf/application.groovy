// File: grails-app/conf/application.groovy
import grails.util.Environment
// Property info.app.grailsEnv.
// Because it starts with info. it ends
// up in the /info endpoint.
info {
    app {
        grailsEnv = Environment.isSystemSet() ? Environment.current.name : Environment.PRODUCTION.name
    }
}
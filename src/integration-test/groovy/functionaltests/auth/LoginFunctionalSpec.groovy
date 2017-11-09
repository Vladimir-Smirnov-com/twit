package functionaltests.auth

import functionaltests.pages.HomePage
import functionaltests.pages.LoginPage
import functionaltests.pages.StatusPage
import functionaltests.pages.UserPage
import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@Integration
class LoginFunctionalSpec extends GebSpec{

    def setup() {
        go '/logoff'
    }

    void 'test expected redirects'() {
        when:
        go '/'

        then:
        at HomePage

        when:
        go '/status'

        then:
        at LoginPage

        when:
        go '/users'

        then:
        at LoginPage
    }

    void 'test authentication'() {
        when:
        go '/logoff'

        then:
        at HomePage

        when:
        go '/status'

        then:
        at LoginPage

        when:
        username = 'vova'
        password = '123'
        loginButton.click()

        then:
        at StatusPage

        when:
        go '/person/index'

        then:
        at UserPage

        when:
        go '/'

        then:
        at HomePage

        when:
        go '/status'

        then:
        at StatusPage

        when:
        go '/logoff'

        then:
        at HomePage

        when:
        go '/status'

        then:
        at LoginPage
    }
}

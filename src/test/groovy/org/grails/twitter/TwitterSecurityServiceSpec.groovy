package org.grails.twitter

import grails.buildtestdata.mixin.Build
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.grails.twitter.auth.Person
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

@TestFor(TwitterSecurityService)
@Mock([Person])
@Build(Person)
class TwitterSecurityServiceSpec extends Specification {

    void "Test the getCurrentUser method returns the valid instance"() {

        setup:
        Person currentUser = Person.build().save();
        def springSecurityService = Mock(SpringSecurityService)
        springSecurityService.currentUser >> currentUser
        service.springSecurityService = springSecurityService

        when: "The getCurrentUser method is executed"
        Person person = service.getCurrentUser()

        then: "The getCurrentUser method is returns the valid instance"
        person == currentUser
    }

    void "Test the getCurrentUsername method returns the valid instance"() {

        setup:
        Person currentUser = Person.build().save();
        GrailsUser grailsUser = Mock(GrailsUser)
        grailsUser.username >> currentUser.getUserName()

        def springSecurityService = Mock(SpringSecurityService)
        springSecurityService.principal >> grailsUser
        service.springSecurityService = springSecurityService

        when: "The getCurrentUsername method is executed"
        String currentUsername = service.getCurrentUsername()

        then: "The getCurrentUsername method is returns the valid instance"
        currentUsername == currentUser.getUserName()
    }

    void "Test the loadCurrentUser method returns the valid instance"() {

        setup:
        Person currentUser = Person.build().save();

        def springSecurityService = Mock(SpringSecurityService)
        springSecurityService.principal >> currentUser
        service.springSecurityService = springSecurityService

        when: "The loadCurrentUser method is executed"
        Person loadedUser = service.loadCurrentUser()

        then: "The loadCurrentUser method is returns the valid instance"
        loadedUser == currentUser
    }
}

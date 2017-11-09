package org.grails.twitter

import grails.buildtestdata.mixin.Build
import grails.gsp.PageRenderer
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.grails.twitter.auth.Person
import org.grails.twitter.auth.PersonRole
import org.grails.twitter.auth.Role
import org.springframework.messaging.simp.SimpMessagingTemplate
import spock.lang.Specification

@TestFor(StatusService)
@Mock([Person, PersonRole, Status, Role, TimelineService])
@Build(Person)
class StatusServiceSpec extends Specification {

    void setup() {
        defineBeans {
            Person.build().save()
        }
    }

    void "Test the updateStatus method correctly persists an instance"() {

        setup:
        service.groovyPageRenderer = Stub(PageRenderer)
        //service.brokerMessagingTemplate = Mock(SimpMessagingTemplate)

        when: "The updateStatus method is executed"
        String testMessage = 'test message'
        String html = service.updateStatus(testMessage, Person.build().getUserName())

        then: "The updateStatus method is executed with a valid instance"
        Status.findAll().get(0).message.equals(testMessage)
        //html.size() == 0
    }

    void "Test the follow method add user to followed"() {

        setup:
        Person currentUser = Person.build().save();
        def twitterSecurityService = Mock(TwitterSecurityService)
        twitterSecurityService.getCurrentUser() >> currentUser
        service.twitterSecurityService = twitterSecurityService
        Person followedPerson = Person.build()
        followedPerson.userName = 'followedperson'
        followedPerson.save()

        when: "The follow method is executed"
        service.follow('followedperson')

        then: "The follow method is executed with a valid instance"
        currentUser.getFollowed().size() == 1
        currentUser.getFollowed().first().getUserName().equals(followedPerson.getUserName())
    }

    void "Test the unfollow method remove user from followed"() {

        setup:
        Person currentUser = Person.build().save();
        def twitterSecurityService = Mock(TwitterSecurityService)
        twitterSecurityService.getCurrentUser() >> currentUser
        service.twitterSecurityService = twitterSecurityService
        Person followedPerson = Person.build()
        followedPerson.userName = 'followedperson'
        followedPerson.save()
        service.follow('followedperson')

        when: "The unfollow method is executed"
        service.unfollow('followedperson')

        then: "The unfollow method is executed with a valid instance"
        currentUser.getFollowed().size() == 0
    }
}

package org.grails.twitter

import grails.buildtestdata.mixin.Build
import grails.gsp.PageRenderer
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.grails.twitter.auth.Person
import org.springframework.messaging.simp.SimpMessagingTemplate
import spock.lang.Specification

@TestFor(TimelineService)
@Mock([Person, Status])
@Build(Person)
class TimelineServiceSpec extends Specification {

    void "Test the getTimelineForAnonymousUser method returns the valid instance"() {

        setup:
        Person currentUser = Person.build().save();
        def twitterSecurityService = Mock(TwitterSecurityService)
        twitterSecurityService.getCurrentUser() >> currentUser
        service.twitterSecurityService = twitterSecurityService
        new Status(message: 'test message', author: currentUser).save()

        when: "The getTimelineForAnonymousUser method is executed"
        List list = service.getTimelineForAnonymousUser()

        then: "The getTimelineForAnonymousUser method is returns the valid instance"
        list.size() == 1
    }

    void "Test the getTimelineForUser method returns the correct model"() {

        setup:
        Person currentUser = Person.build().save();
        def twitterSecurityService = Mock(TwitterSecurityService)
        twitterSecurityService.getCurrentUser() >> currentUser
        service.twitterSecurityService = twitterSecurityService
        new Status(message: 'test message', author: currentUser).save()

        when: "The getTimelineForUser method is executed"
        List list = service.getTimelineForUser(currentUser.getUserName())

        then: "The getTimelineForUser method is returns the valid instance"
        list.size() == 1
    }
}

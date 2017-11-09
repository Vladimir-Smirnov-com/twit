package org.grails.twitter

import grails.buildtestdata.mixin.Build
import grails.gorm.PagedResultList
import grails.gsp.PageRenderer
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.grails.twitter.auth.Person
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Ignore
import spock.lang.Specification

import javax.rmi.CORBA.Stub
import java.security.Principal

@TestFor(StatusController)
@Mock([Person, Status, StatusService, PersonService])
@Build(Person)
class StatusControllerSpec extends Specification {

    void "Test the index action returns the correct model"() {

        setup:
        Person currentUser = Person.build().save()

        Set followedPersonSet = new LinkedHashSet()

        Person followedPerson = Person.build()
        followedPerson.userName = 'followedperson'
        followedPerson.save()
        followedPersonSet.add(followedPerson)

        currentUser.followed = followedPersonSet

        def twitterSecurityService = Mock(TwitterSecurityService)
        twitterSecurityService.getCurrentUser() >> currentUser
        controller.twitterSecurityService = twitterSecurityService

        Status status = new Status(message: 'test message', author: currentUser).save()

        def timelineService = Stub(TimelineService)
        timelineService.twitterSecurityService = twitterSecurityService
        List list = new ArrayList()
        list.add(status)
        timelineService.timelineForUser >> list
        controller.timelineService = timelineService

        when: "The index action is executed"
        Map map = controller.index()

        then: "The model is correct"
        map.statusMessages.size() == 1
        map.totalStatusCount == 1
        map.following.size() == 1
        map.followers.size() == 0
        map.otherUsers.size() == 0

    }

    void "Test the updateStatus action returns the correct model"() {

        setup:
        StatusService statusService = Stub(StatusService)
        controller.statusService = statusService
        Person currentUser = Person.build().save();
        def twitterSecurityService = Mock(TwitterSecurityService)
        twitterSecurityService.getCurrentUser() >> currentUser
        controller.twitterSecurityService = twitterSecurityService

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = Mock(UsernamePasswordAuthenticationToken)
        GrailsUser grailsUser = Mock(GrailsUser)
        grailsUser.username >> currentUser.getUserName()
        usernamePasswordAuthenticationToken.principal >> grailsUser

        when: "The updateStatus action is executed"
        String result = controller.updateStatus("test message", usernamePasswordAuthenticationToken)

        then: "The model is correct"
        result.isEmpty()
    }
}

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

    void "Test the index action invoke correct"() {

        setup:
        List userList = new ArrayList()
        Person currentUser = Person.build().save()

        Set followedPersonSet = new LinkedHashSet()

        Person followedPerson = Person.build()
        followedPerson.userName = 'followedperson'
        followedPerson.save()

        followedPersonSet.add(followedPerson)

        currentUser.followed = followedPersonSet

        Set followersPersonSet = new LinkedHashSet()

        Status status = new Status(message: 'test message', author: currentUser).save()
        List statusList = new ArrayList()
        statusList.add(status)

        and: "Mocks"
        controller.twitterSecurityService = Mock(TwitterSecurityService)
        controller.timelineService = Mock(TimelineService)
        controller.statusService = Mock(StatusService)
        controller.personService = Mock(PersonService)

        when: "The index action is executed"
        Map result = controller.index()

        then: "The invoked is correct"
        1 * controller.timelineService.timelineForUser >> statusList
        1 * controller.twitterSecurityService.currentUser >> currentUser
        1 * controller.statusService.totalStatusCountByUser(currentUser) >> statusList.size()
        1 * controller.personService.getFollowed(currentUser) >> followedPersonSet
        1 * controller.personService.getFollowers(currentUser) >> followersPersonSet
        1 * controller.personService.personInstanceList(null) >> userList
        result.statusMessages == statusList
        result.person == currentUser
        result.totalStatusCount == statusList.size()
        result.following == followedPersonSet
        result.followers == followersPersonSet
        result.otherUsers == userList
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

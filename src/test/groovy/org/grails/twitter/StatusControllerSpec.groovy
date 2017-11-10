package org.grails.twitter

import grails.buildtestdata.mixin.Build
import grails.gsp.PageRenderer
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.grails.twitter.auth.Person
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

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
        1 * controller.personService.getPersonInstanceList(null) >> userList
        result.statusMessages == statusList
        result.person == currentUser
        result.totalStatusCount == statusList.size()
        result.following == followedPersonSet
        result.followers == followersPersonSet
        result.otherUsers == userList
    }

    void "Test the updateStatus action returns the correct model"() {

        setup:
        controller.statusService = Mock(StatusService)
        PageRenderer pageRenderer = Mock(PageRenderer)
        //pageRenderer.render(new HashMap()) { "Test html string" }
        controller.groovyPageRenderer = pageRenderer
        SimpMessagingTemplate messagingTemplate = Mock(SimpMessagingTemplate)
        controller.brokerMessagingTemplate = messagingTemplate
        Person currentUser = Person.build().save();
        def twitterSecurityService = Mock(TwitterSecurityService)
        twitterSecurityService.getCurrentUser() >> currentUser
        controller.twitterSecurityService = twitterSecurityService

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = Mock(UsernamePasswordAuthenticationToken)
        GrailsUser grailsUser = Mock(GrailsUser)
        grailsUser.username >> currentUser.getUserName()
        usernamePasswordAuthenticationToken.principal >> grailsUser
        String testMessage = 'test message'

        when: "The updateStatus action is executed"
        String result = controller.updateStatus(testMessage, usernamePasswordAuthenticationToken)

        then: "The model is correct"
        //result.isEmpty()
        1 * controller.statusService.updateStatus (testMessage, currentUser.getUserName())
        1 * controller.groovyPageRenderer.render(['template':'/status/statusMessage', 'model':['statusMessage':null]])
        //1 * controller.groovyPageRenderer.render
        //1 * <StatusService>.getFollowersOf('userName')
        1 * controller.statusService.getFollowersOf(currentUser.getUserName()) { new ArrayList<>().add(currentUser) }
        //brokerMessagingTemplate.convertAndSendToUser user, '/queue/timeline', html
        1 * messagingTemplate.convertAndSendToUser('u', '/queue/timeline', null)
    }
}

package org.grails.twitter

import grails.plugin.springsecurity.annotation.Secured
import java.security.Principal

import org.springframework.messaging.handler.annotation.MessageMapping

@Secured('isAuthenticated()')
class StatusController {

    def statusService
    def personService
    def timelineService
    def twitterSecurityService
    def brokerMessagingTemplate
    def groovyPageRenderer

    def index() {
        def messages = timelineService.timelineForUser
        def person = twitterSecurityService.currentUser
        def totalStatusCount = statusService.totalStatusCountByUser(person)

        def following = personService.getFollowed(person)
        def followers = personService.getFollowers(person)
        def otherUsers = personService.getPersonInstanceList() - following - followers - person

        [statusMessages  : messages,
         person          : person,
         totalStatusCount: totalStatusCount,
         following       : following,
         followers       : followers,
         otherUsers      : otherUsers]
    }

    @MessageMapping('/updateStatus')
    protected String updateStatus(String message, Principal author) {

        String userName = author.principal.username;

        Status status = statusService.updateStatus message, userName

        def html = groovyPageRenderer.render template: '/status/statusMessage', model: [statusMessage: status]

        def sendTo = statusService.getFollowersOf(userName) + userName
        sendTo.each { user ->
            brokerMessagingTemplate.convertAndSendToUser user, '/queue/timeline', html
        }

        html
    }
}

package org.grails.twitter

import grails.plugin.springsecurity.annotation.Secured
import org.grails.twitter.builders.StatusIndexBuilder

import java.security.Principal

import org.springframework.messaging.handler.annotation.MessageMapping

@Secured('isAuthenticated()')
class StatusController {

    def statusService
    def brokerMessagingTemplate
    def groovyPageRenderer

    def index() {
        StatusIndexBuilder statusIndexBuilder = new StatusIndexBuilder().build()
        statusIndexBuilder.toMap()
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

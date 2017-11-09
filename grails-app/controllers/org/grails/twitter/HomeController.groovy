package org.grails.twitter

class HomeController {

    def timelineService
    def twitterSecurityService

    def index() {
        def person = twitterSecurityService.currentUser
        def messages;
        if (person == null) {
            messages = timelineService.timelineForAnonymousUser
        } else {
            messages = timelineService.timelineForUser
        }

        [statusMessages: messages]
    }
}

package org.grails.twitter.builders

class StatusIndexBuilder {

    def statusService
    def personService
    def timelineService
    def twitterSecurityService

    def messages
    def person
    def totalStatusCount
    def following
    def followers
    def otherUsers

    def build() {
        messages = timelineService.timelineForUser
        person = twitterSecurityService.currentUser
        totalStatusCount = statusService.totalStatusCountByUser(person)

        following = personService.getFollowed(person)
        followers = personService.getFollowers(person)
        otherUsers = personService.getPersonInstanceList() - following - followers - person
    }

    Map toMap() {
        [statusMessages  : messages,
         person          : person,
         totalStatusCount: totalStatusCount,
         following       : following,
         followers       : followers,
         otherUsers      : otherUsers]
    }


}

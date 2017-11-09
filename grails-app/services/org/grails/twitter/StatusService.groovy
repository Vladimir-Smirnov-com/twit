package org.grails.twitter

import org.grails.twitter.auth.Person

import grails.transaction.Transactional

@Transactional
class StatusService {

    def twitterSecurityService

    def getFollowersOf(String userName) {
        Person.where {
            followed.userName == userName
        }.property('userName').list()
    }

    Status updateStatus(String message, String userName) {
        def author = findPerson(userName)
        def status = new Status(message: message, author: author)
        status.save()
    }

    void unfollow(String userName) {
        def person = findPerson(userName)
        if (person) {
            def currentUser = twitterSecurityService.currentUser
            currentUser.removeFromFollowed(person)
        }
    }

    void follow(String userName) {
        def person = findPerson(userName)
        if (person) {
            def currentUser = twitterSecurityService.currentUser
            currentUser.addToFollowed(person)
        }
    }

    List getStatusByAuthor(Person person) {
        Status.findAllByAuthor(person, [max: 10, sort: "dateCreated", order: "desc"])
    }

    Number totalStatusCountByUser (Person person) {
        Status.where { author.userName == person.userName }.count()
    }

    private Person findPerson(String userName) {
        Person.findByUserName(userName)
    }
}

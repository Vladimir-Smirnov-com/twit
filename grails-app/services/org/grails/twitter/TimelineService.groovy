package org.grails.twitter

import org.grails.datastore.mapping.query.api.BuildableCriteria
import org.grails.twitter.auth.Person

class TimelineService {

    static transactional = false

    def twitterSecurityService

    def getTimelineForUser(String username = twitterSecurityService.currentUsername) {
        def person = Person.findByUserName(username)
        if (person) {
            def query = Status.whereAny {
                author == person
                if (person.followed) {
                    author in person.followed
                }
            }
            query.list(max: 10, sort: 'dateCreated', order: 'desc')
        }
    }

    def getTimelineForAnonymousUser() {
        //def query = Status.whereAny {}
        //query.list(max: 10, sort: 'dateCreated', order: 'desc')
        //Status.findAll([max: 10, sort: 'dateCreated', order: 'desc'])
        //Status.list([max: 10, sort: 'dateCreated', order: 'desc'])
        BuildableCriteria buildableCriteria = Status.createCriteria()
        def results = buildableCriteria.list (max: 10) {
            order("dateCreated", "desc")
        }
        results
    }
}

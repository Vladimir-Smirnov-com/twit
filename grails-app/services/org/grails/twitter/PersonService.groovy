package org.grails.twitter

import grails.transaction.Transactional
import org.grails.twitter.auth.Person

import static org.springframework.http.HttpStatus.CREATED

@Transactional
class PersonService {

    void save(Person person) {
        person.save()
    }

    Integer getPersonCount() {
        Person.count()
    }

    List getPersonInstanceList(Map params) {
        Person.list(params)
    }

    Set getFollowed(Person person) {
        person.followed
    }

    Set getFollowers(Person person) {
        Person.where { followed.userName == person.userName }.list()
    }
}

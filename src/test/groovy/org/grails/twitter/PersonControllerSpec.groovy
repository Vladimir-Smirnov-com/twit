package org.grails.twitter

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.grails.twitter.PersonController
import org.grails.twitter.PersonService
import org.grails.twitter.Status
import org.grails.twitter.StatusService
import org.grails.twitter.auth.Person
import spock.lang.Ignore
import spock.lang.Specification

@TestFor(PersonController)
@Mock([Person, Status, PersonService, StatusService])
class PersonControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        params["firstName"] = 'TestFirstName'
        params["lastName"] = 'TestLastName'
        params["userName"] = 'testfirstname'
        params["email"] = 'testfirstname@mail.com'
        params["password"] = '123'
        assert true
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.personList
        model.personCount == null
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.person != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def person = new Person()
        person.validate()
        controller.save(person)

        then: "The create view is rendered again with the correct model"
        model.person != null
        view == 'create'
    }

    void "Test the redirect is issued to the show action"() {

        when: "The save action is executed with a valid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        populateValidParams(params)
        Person person = new Person(params)

        controller.save(person)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/person/show/1'
        controller.flash.message != null
        Person.count() == 1
    }

    void "Test that the show action returns the correct model"() {

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def person = new Person(params)
        Map map = controller.show(person)

        then: "A model is populated containing the domain instance"
        map.person == person
        map.messages.size() == 0
    }
}

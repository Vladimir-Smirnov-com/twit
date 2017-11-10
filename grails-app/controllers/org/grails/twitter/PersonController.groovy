package org.grails.twitter

import org.grails.twitter.auth.Person

import static org.springframework.http.HttpStatus.CREATED

class PersonController {

    def statusService
    def personService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [personInstanceCount: personService.getPersonCount(), personInstanceList: personService.getPersonInstanceList(params)]
    }

    def unfollow(String userToUnfollow) {
        statusService.unfollow userToUnfollow
        redirect action: 'index'
    }

    def follow(String userToFollow) {
        statusService.follow userToFollow
        redirect action: 'index'
    }

    def show(Person person) {
        [person: person, messages: statusService.getStatusByAuthor(person)]
    }

    def create() {
        respond new Person(params)
    }

    def save(Person person) {
        params
        if (person == null) {
            notFound()
            return
        }

        if (person.hasErrors()) {
            respond person.errors, view: 'create'
            return
        }

        personService.save(person)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), person.id])
                redirect person
            }
            '*' { respond person, [status: CREATED] }
        }
    }
}

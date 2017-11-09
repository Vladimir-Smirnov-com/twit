package twitter

import org.grails.twitter.auth.Person
import org.grails.twitter.auth.PersonRole
import org.grails.twitter.auth.Role
import org.omg.CORBA.PERSIST_STORE

class BootStrap {

    def init = { servletContext ->
        Role roleUser = new Role(authority: 'ROLE_USER').save(failOnError: false)

        def createPerson = { String firstName, String lastName, String email ->
            def person = new Person(firstName: firstName, lastName: lastName,
                                     email: email,
            	                     userName: firstName.toLowerCase(),
            	                     password: '123').save(failOnError: false)
            PersonRole.create person, roleUser
        }

        createPerson 'Max', 'M', 'max@mail.com'
        createPerson 'Dima', 'D', 'dima@mail.com'
        createPerson 'Vova', 'V', 'vova@mail.com'
    }

    def destroy = {
    }

}

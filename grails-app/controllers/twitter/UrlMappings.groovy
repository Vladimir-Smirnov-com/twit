package twitter

class UrlMappings {

    static mappings = {
        "/login/$action?"(controller: 'login')
        "/logout/$action?"(controller: 'logout')
        "/registration/$action?"(controller: 'registration')
        "/status"(controller: 'status', action: 'index')
        "/updateStatus"(controller: 'status', action: 'updateStatus')
        "/person/index"(controller: 'person', action: 'index')
        "/person/show/${id}"(controller: 'person', action: 'show')
        "/person/create"(controller: 'person', action: 'create')
        "/login/registration"(controller: 'person', action: 'create')
        "/person/save"(controller: 'person', action: 'save')
        "/unfollow/$userToUnfollow"(controller: 'person', action: 'unfollow')
        "/follow/$userToFollow"(controller: 'person', action: 'follow')
        "/report"(controller: 'report', action: 'index')
        //"/"(view:"/index")
        "/"(controller: 'home', action: 'index')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

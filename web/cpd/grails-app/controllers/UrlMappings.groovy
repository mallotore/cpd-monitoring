class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/home/home")
        "/configuration" (controller: "ServerConfiguration", action: "index")
        "/configuration/servers" (controller: "ServerConfiguration", action: "create")
        "/configuration/servers/edit" (controller: "ServerConfiguration", action: "edit")
        "/configuration/servers/$id/delete" (controller: "ServerConfiguration", action: "delete")
        "/stats"(view:"/index")
        "/error"(view:"/error")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

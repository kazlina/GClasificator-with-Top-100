package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.*;
import play.db.jpa.*;

import views.html.*;

import models.*;
import play.libs.*;

import java.io.IOException;

public class Links extends Controller {

	//static Form<Link> linkForm = form(Link.class);
    
    public static Result links() {
    	Form<Link> linkForm = form(Link.class);
    	return ok(views.html.link.render(Link.all(), linkForm));
    }

    public static Result newLink() {
    	Form<Link> linkForm = form(Link.class);
    	Form<Link> filledForm = linkForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.link.render(Link.all(), filledForm));
        } else {
            Link.add(filledForm.get());
            return redirect(routes.Links.links());
        }
    }

    public static Result deleteLink(Long id) {
        Link.delete(id);
        return redirect(routes.Links.links());
    }
}

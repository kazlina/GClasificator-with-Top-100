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
    	return ok(views.html.links.render(Link.all(), linkForm));
    }

    public static Result newLink() {
    	Form<Link> linkForm = form(Link.class);
    	Form<Link> filledForm = linkForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.links.render(Link.all(), filledForm));
        } else {
            Link.add(filledForm.get());
            return redirect(routes.Links.links());
        }
    }

    public static Result deleteLink(Long id) {
        Link.delete(id);
        return redirect(routes.Links.links());
    }

	//you can change link
    public static Result changeLink(Long id) {
        Form<Link> filledForm = form(Link.class).bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.link.render(id, filledForm));
        } else {
            Link.updateLink(id, filledForm.get());
            return redirect(routes.Links.links());
        }
    }
	
    //you can view information about link
    public static Result viewLink(Long linkId) {
        return ok(views.html.link.render(linkId, form(Link.class).fill(Link.findById(linkId))));
    }

}

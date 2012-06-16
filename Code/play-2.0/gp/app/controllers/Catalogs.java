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

import views.html.*;

public class Catalogs extends Controller {

	//CATALOGS
	public static Result main() {
        return ok(views.html.catalogs.render());
    }

    // GENDER
    public static Result genders() {
    	Form<Gender> genderForm = form(Gender.class);
    	return ok(views.html.genders.render(Gender.all(), genderForm));
    }

    public static Result addGender() {
    	Form<Gender> genderForm = form(Gender.class);
        Form<Gender> filledForm = genderForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.genders.render(Gender.all(), filledForm));
        } else {
            Gender gg = filledForm.get();
            Gender.add(gg.value);
            return redirect(routes.Catalogs.genders());
        }
    }

    public static Result deleteGender(Long id) {
        Gender.delete(id);
        return redirect(routes.Catalogs.genders());
    }

    public static Result createGenderList() {
        Gender.createList();
        return redirect(routes.Catalogs.genders());
    }

    // CONTENT
    public static Result contents() {
    	Form<models.Content> contentForm = form(models.Content.class);
        return ok(views.html.contents.render(models.Content.all(), contentForm));
    }

    public static Result addContent() {
    	Form<models.Content> contentForm = form(models.Content.class);
        Form<models.Content> filledForm = contentForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.contents.render(models.Content.all(), filledForm));
        } else {
            models.Content gg = filledForm.get();
            models.Content.add(gg.kind);
            return redirect(routes.Catalogs.contents());
        }
    }

    public static Result deleteContent(Long id) {
        models.Content.delete(id);
        return redirect(routes.Catalogs.contents());
    }

    public static Result createContentList() {
        models.Content.createList();
        return redirect(routes.Catalogs.contents());
    }

    // RELATIONSHIP
    public static Result relationships() {
    	Form<Relationship> relationshipForm = form(Relationship.class);
        return ok(views.html.relationships.render(Relationship.all(), relationshipForm));
    }

    public static Result addRelation() {
    	Form<Relationship> relationshipForm = form(Relationship.class);
        Form<Relationship> filledForm = relationshipForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.relationships.render(Relationship.all(), filledForm));
        } else {
            Relationship gg = filledForm.get();
            Relationship.add(gg.status);
            return redirect(routes.Catalogs.relationships());
        }
    }

    public static Result deleteRelation(Long id) {
        Relationship.delete(id);
        return redirect(routes.Catalogs.relationships());
    }

    public static Result createRelationList() {
        Relationship.createList();
        return redirect(routes.Catalogs.relationships());
    }
}

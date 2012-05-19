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

public class Groups extends Controller {

    static Form<Group> groupForm = form(Group.class);

    // GROUP
    public static Result groups() {
        return ok(views.html.group.render(Group.all(), groupForm));
    }

    public static Result addGroup() {
        Form<Group> filledForm = groupForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.group.render(Group.all(), filledForm));
        } else {
            Group.add(filledForm.get());
            return redirect(routes.Groups.groups());
        }
    }

    public static Result deleteGroup(Long id) {
        Group.delete(id);
        return redirect(routes.Groups.groups());
    }
}

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

public class GPMsAddedByAdmin extends Controller {

    static Form<AddedByAdmin> gpmForm = form(AddedByAdmin.class);

    public static Result gpmsAddedByAdmin (Long groupId) {
        Group gr = Group.findById(groupId);
        return ok(views.html.addedbyadmin.render(gr.id, gr.name, AddedByAdmin.findByGroupId(groupId), gpmForm));
    }

    public static Result addAddedByAdmin(Long groupId) {
        Group gr = Group.findById(groupId);
        Form<AddedByAdmin> filledForm = gpmForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.addedbyadmin.render(gr.id, gr.name, AddedByAdmin.findByGroupId(groupId), filledForm));
        } else {
            AddedByAdmin gg = filledForm.get();
            AddedByAdmin.add(new AddedByAdmin(gg.gpm.idGpm, groupId, gg.position, gg.dateOfAddition, gg.dateOfRemoval, gg.comment));
            return redirect(routes.GPMsAddedByAdmin.gpmsAddedByAdmin(groupId));
        }
    }

    public static Result deleteAddedByAdmin(Long groupId, Long id) {
        AddedByAdmin.delete(id);
        return redirect(routes.GPMsAddedByAdmin.gpmsAddedByAdmin(groupId));
    }
}

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

public class BlackListController extends Controller {

    static Form<BlackList> blackListForm = form(BlackList.class);

    // BLACK LIST
    public static Result blackList () {
        return ok(views.html.blacklist.render(BlackList.all(), blackListForm));
    }

    public static Result addBlackList() {
        Form<BlackList> filledForm = blackListForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.blacklist.render(BlackList.all(), filledForm));
        } else {
            BlackList.add(filledForm.get());
            return redirect(routes.BlackListController.blackList());
        }
    }

    public static Result deleteBlackList(Long id) {
        BlackList.delete(id);
        return redirect(routes.BlackListController.blackList());
    }
}

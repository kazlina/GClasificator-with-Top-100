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
    static Form<GroupWord> groupWordForm = form(GroupWord.class);

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

    // GROUP WORDS
        public static Result groupWords(Long groupId) {
        return ok(views.html.groupword.render(Group.findById(groupId), Group.findById(groupId).name, GroupWord.findByGroup(groupId), groupWordForm));
    }

    public static Result addGroupWord(Long groupId) {
        Form<GroupWord> filledForm = groupWordForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.groupword.render(Group.findById(groupId), Group.findById(groupId).name, GroupWord.findByGroup(groupId), filledForm));
        } else {
            GroupWord.add(filledForm.get());
            return redirect(routes.Groups.groupWords(groupId));
        }
    }

    public static Result deleteGroupWord(Long groupId,Long groupWordId) {
        GroupWord.delete(groupWordId);
        return redirect(routes.Groups.groupWords(groupId));
    }
}

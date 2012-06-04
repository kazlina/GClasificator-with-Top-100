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
    //you can view all groups
    public static Result groups() {
        return ok(views.html.groups.render(Group.all(), groupForm));
    }

    //you can add new group
    public static Result addGroup() {
        Form<Group> filledForm = groupForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.groups.render(Group.all(), filledForm));
        } else {
            Group.add(filledForm.get());
            return redirect(routes.Groups.groups());
        }
    }

    //you can change group
    public static Result changeGroup(Long id) {
        Form<Group> filledForm = groupForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.groups.render(Group.all(), filledForm));
        } else {
            Group.updateGroup(id, filledForm.get());
            return redirect(routes.Groups.groups());
        }
    }
    
    //you can delete group
    public static Result deleteGroup(Long id) {
        Group.delete(id);
        return redirect(routes.Groups.groups());
    }

    //you can view information about group
    public static Result viewGroup(Long groupId) {
        Form<Group> filledForm = form(Group.class).fill(Group.findById(groupId));//groupForm.bindFromRequest();
        return ok(views.html.group.render(Group.findById(groupId),filledForm));
    }


    // GROUP WORDS
    //you can see all group's words
        public static Result groupWords(Long groupId) {
        return ok(views.html.groupword.render(Group.findById(groupId), Group.findById(groupId).name, GroupWord.findByGroup(groupId), groupWordForm));
    }

    //you can add group's word
    public static Result addGroupWord(Long groupId) {
        Form<GroupWord> filledForm = groupWordForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.groupword.render(Group.findById(groupId), Group.findById(groupId).name, GroupWord.findByGroup(groupId), filledForm));
        } else {
            GroupWord.add(filledForm.get());
            return redirect(routes.Groups.groupWords(groupId));
        }
    }

    //you can delete group's word
    public static Result deleteGroupWord(Long groupId,Long groupWordId) {
        GroupWord.delete(groupWordId);
        return redirect(routes.Groups.groupWords(groupId));
    }
}

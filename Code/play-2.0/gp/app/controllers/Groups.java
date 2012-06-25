package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.*;
import play.db.jpa.*;

import views.html.*;

import models.*;
import play.libs.*;

import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.*;

import java.io.File;
import java.net.*;

import java.io.IOException;


public class Groups extends Controller {

    // GROUP
    //you can view all groups
    public static Result groups() {
        return ok(views.html.groups.render(Group.all(), form(Group.class)));
    }

    //you can add new group
    public static Result addGroup() {
        Form<Group> filledForm = form(Group.class).bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.groups.render(Group.all(), filledForm));
        } else {		
		Group gr = filledForm.get();
	
		MultipartFormData body = request().body().asMultipartFormData();		
		if (body != null) {
	   		FilePart activeImageFile = body.getFile("activeImageFile");
			gr.activeImage = activeImageFile.getFilename();
			String FileTitle1 = activeImageFile.getFilename();
			String contentType1 = activeImageFile.getContentType();
	      		System.out.println("Name: " + FileTitle1 + ", contentType: " + contentType1);
	      		File file1 = activeImageFile.getFile();
	     		file1.renameTo(new File("public/images/" + FileTitle1));     	
		
			FilePart passiveImageFile = body.getFile("passiveImageFile");
			gr.passiveImage = passiveImageFile.getFilename();
			String FileTitle2 = passiveImageFile.getFilename();
	      		String contentType2 = passiveImageFile.getContentType();
	      		System.out.println("Name: " + FileTitle2 + ", contentType: " + contentType2);
	      		File file2 = passiveImageFile.getFile();
	     		file2.renameTo(new File("public/images/" + FileTitle2));
						    
		    	//return redirect(routes.Groups.groups());
				}
		 	else
			       { 
				System.out.println("Body is NULL!");
				}
		Group.add(gr);
       		 }
		return redirect(routes.Groups.groups());
   	 }


    //you can change group
    public static Result changeGroup(Long id) {
        Form<Group> filledForm = form(Group.class).bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.group.render(id, filledForm));
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
        return ok(views.html.group.render(groupId, form(Group.class).fill(Group.findById(groupId))));
    }


    // GROUP WORDS
    //you can see all group's words
        public static Result groupWords(Long groupId) {
        return ok(views.html.groupword.render(Group.findById(groupId), Group.findById(groupId).name, GroupWord.findByGroup(groupId), form(GroupWord.class)));
    }

    //you can add group's word
    public static Result addGroupWord(Long groupId) {
    	Form<GroupWord> filledForm = form(GroupWord.class).bindFromRequest();
        if(filledForm.hasErrors()) 
        	return badRequest(views.html.groupword.render(Group.findById(groupId), Group.findById(groupId).name, GroupWord.findByGroup(groupId), filledForm));
         
        GroupWord gw = filledForm.get();
        GroupWord.add(new GroupWord(groupId, gw.word.word, gw.postWeight, gw.profileWeight));
        return redirect(routes.Groups.groupWords(groupId));
    }

    //you can delete group's word
    public static Result deleteGroupWord(Long groupId,Long groupWordId) {
        GroupWord.delete(groupWordId);
        return redirect(routes.Groups.groupWords(groupId));
    }
    
    // GROUP LINKS
    //you can see all group's words
    public static Result groupLinks(Long groupId) {
        Group gr = Group.findById(groupId);
    	return ok(views.html.grouplink.render(gr, gr.name, GroupLink.findByGroup(groupId), form(GroupLink.class)));
    }

    //you can add group's word
    public static Result addGroupLink(Long groupId) {
    	Group gr = Group.findById(groupId);
    	Form<GroupLink> filledForm = form(GroupLink.class).bindFromRequest();
        if(filledForm.hasErrors()) 
        	return badRequest(views.html.grouplink.render(gr, gr.name, GroupLink.findByGroup(groupId), filledForm));
         
        GroupLink gl = filledForm.get();
        GroupLink.add(new GroupLink(groupId, gl.link.link, gl.postWeight, gl.profileWeight));
        return redirect(routes.Groups.groupLinks(groupId));
    }

    //you can delete group's word
    public static Result deleteGroupLink(Long groupId,Long groupWordId) {
        GroupLink.delete(groupWordId);
        return redirect(routes.Groups.groupLinks(groupId));
    }
}

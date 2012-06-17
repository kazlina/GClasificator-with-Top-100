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

public class Admin extends Controller {

	//MAIN PAGE
	public static Result main() {
        return ok(views.html.admin.render(
        		Group.size(), 
        		Word.size(), 
        		Link.size(), 
        		GPM.size(),
        		Profile.size(), 
        		Post.size()));
    }
}

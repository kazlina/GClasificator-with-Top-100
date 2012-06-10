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

public class Application extends Controller {

	public static Result ind() throws IOException {
	    TempProfile temp = GAPI.getProfile("100915540970866628562");
	    DataExtraction.newGPM("114536133164105123829");
       	return ok(temp.displayName);
		}

    public static Result index() {
    	return ok(views.html.index.render(Group.all()));
    }
    
    public static Result viewGroup(Long idGroup) {
    	List <Profile> gpms = new ArrayList<Profile>();
        for(GPM gpm: GPM.all()) {
        	gpms.add(Profile.lastProfileByGpmId(gpm.id));
        }
    	return ok(views.html.usergroup.render(gpms, Group.all(), Group.findById(idGroup).name));
    }
}

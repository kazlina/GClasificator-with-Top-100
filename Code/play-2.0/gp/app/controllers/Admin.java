package controllers;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import play.*;
import play.mvc.*;
import play.data.*;
import play.data.validation.Constraints;
import play.db.jpa.*;
import play.libs.*;
import views.html.*;
import models.*;


public class Admin extends Controller {
	
	public static boolean isBaseUpdaterThreadRun;
	
	private static class BackgroundProcess implements Runnable {
        public void run() {
            while (isBaseUpdaterThreadRun) {
                try {
                    UpdateControl.Start();
                    TimeUnit.SECONDS.sleep(10);
                }
                catch (InterruptedException ex) {}
            }
        }
    }
	
	// update's parameters.
	public static Parameters updateParameters = new Parameters();
	
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
	
	public static Result updateControl() {
		return ok(views.html.updateControl.render(
        		GPM.size(),
        		Profile.size(), 
        		Post.size(),
        		isBaseUpdaterThreadRun,
        		form(Parameters.class).fill(updateParameters)));
    }
	
	public static Result startUpdate() {
		if (!isBaseUpdaterThreadRun) {
            Thread worker = new Thread(new BackgroundProcess());
            worker.setDaemon(true);
            isBaseUpdaterThreadRun = true;
            worker.start();

            System.out.println(" ---=== UPDATE STARTED ===---");
        }
		else {
			isBaseUpdaterThreadRun = false;
			
			System.out.println(" ---=== UPDATE STOPPED ===---");
		}
		
        return redirect(routes.Admin.updateControl());
    }
	
	public static Result setParameters() {
		Form<Parameters> filledForm = form(Parameters.class).bindFromRequest();
        if(filledForm.hasErrors())
            return badRequest(views.html.updateControl.render(
            		GPM.size(),
            		Profile.size(), 
            		Post.size(),
            		isBaseUpdaterThreadRun,
            		filledForm));
		
        Parameters params = filledForm.get();
        int sumParameters = params.percentGetNew + params.percentUpdatePosts + params.percentUpdateProfiles;
        if (sumParameters == 0) {
        	updateParameters.percentUpdatePosts = updateParameters.percentUpdateProfiles = 33;
        	updateParameters.percentGetNew = 34;
        }
        else if (sumParameters == 100)
        	updateParameters = params;
        else {
        	updateParameters.percentUpdatePosts = params.percentUpdatePosts * 100 / sumParameters;
        	updateParameters.percentUpdateProfiles = params.percentUpdateProfiles * 100 / sumParameters;
        	updateParameters.percentGetNew = 100 - (updateParameters.percentUpdatePosts + updateParameters.percentUpdateProfiles);
        }
        
        return redirect(routes.Admin.updateControl());
    }
}

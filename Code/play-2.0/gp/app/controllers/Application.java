package controllers;
import java.util.*;
import java.lang.InterruptedException;
import java.util.concurrent.TimeUnit;

import play.data.Form;
import play.mvc.*;
import models.*;

import java.io.IOException;

public class Application extends Controller {

    private static class BackgroundProcess implements Runnable {
        public void run() {
            while (true) {
                try {
                    UpdateControl.Start();
                    TimeUnit.SECONDS.sleep(10);
                }
                catch (InterruptedException ex) {}
            }
        }
    }
    
    private static boolean threadRun;
    
    public static Result index() {
        if (!threadRun) {
            Thread worker = new Thread(new BackgroundProcess());
            worker.setDaemon(true);
            threadRun = true;
            worker.start();
        }
        
        return ok(views.html.index.render(Group.all()));
    }
    
    private static class CacheUpdater implements Runnable {
        public void run() {
            while (true) {
            	//��� ������������� � ���
            	������ ���� �������� - �� ������� �� ������. ������ ������- ��������� ������� �������������
                /*try {
                    
                    TimeUnit.SECONDS.sleep(5);
                }
                catch (InterruptedException ex) {}*/
            }
        }
    }
    private static boolean isCacheUpdaterThreadRun;
    public static void cacheUpdate() {
        if (!isCacheUpdaterThreadRun) {
			    //running of cache updating
			    Thread cacheUpdaterThread = new Thread(new CacheUpdater());
			    cacheUpdaterThread.setDaemon(true);
			    isCacheUpdaterThreadRun = true;
			    cacheUpdaterThread.start();
        }
    }
    
    public static Result viewGroup(Long idGroup) {
        List <GpmForOutput> gpms = Classifier.getGpmForGroup(idGroup);
        return ok(views.html.usergroup.render(
        		gpms, 
        		Group.all(), 
        		Group.findById(idGroup),
        		form(GPM.class)));
    }
    
    public static Result indexGpm(Long id) {
    	Form<GPM> filledForm = form(GPM.class).bindFromRequest();
        if(filledForm.hasErrors()) {
        	List <GpmForOutput> gpms = Classifier.getGpmForGroup(id);
            return badRequest(views.html.usergroup.render(
            		gpms, 
            		Group.all(), 
            		Group.findById(id),
            		filledForm));
        } 
        
    	NewGPM.add(filledForm.get().idGpm);
    	
    	return redirect(routes.Application.viewGroup(id));
    }
    
    public static Result ind() {
        return ok("all good");
    }
}

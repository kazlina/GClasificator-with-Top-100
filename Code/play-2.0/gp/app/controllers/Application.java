package controllers;

import java.io.*;
import java.util.*;
import java.lang.InterruptedException;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import play.data.Form;
import play.mvc.*;
import models.*;

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
            System.out.println(" ---=== UPDATE STARTED ===---");
        }
        
        return ok(views.html.index.render(Group.all()));
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

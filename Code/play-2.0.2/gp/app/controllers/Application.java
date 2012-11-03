package controllers;

/*import com.avaje.ebean.SqlRow;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;*/
import java.util.*;
import play.data.Form;
import play.mvc.*;
import models.*;

public class Application extends Controller {

    public static Result index() {
        return ok(views.html.index.render(Group.all()));
    }
    
    public static Result viewGroup(Long idGroup) {
        List <CacheClassifier> gpms = CacheClassifier.findByGroup(idGroup);
        return ok(views.html.usergroup.render(
        		gpms, 
        		Group.all(), 
        		Group.findById(idGroup),
        		form(GPM.class)));
    }
    
    public static Result indexGpm(Long id) {
    	Form<GPM> filledForm = form(GPM.class).bindFromRequest();
        if(filledForm.hasErrors()) {
        	List <CacheClassifier> gpms = CacheClassifier.findByGroup(id);
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
    	return ok("It's work!");
    }
    
    public static Result showTop() {
    	List <GpmForOutput> gpms = Top100.getTopGpms();
    	return ok(views.html.topGpms.render(
        		gpms, 
        		Group.all()));
    }
}

package controllers;

import java.util.*;

import play.mvc.*;
import play.data.*;

import views.html.*;

import models.*;
import play.libs.*;

import java.io.IOException;

public class GPMs extends Controller {

    static Form<GPM> gpmForm = form(GPM.class);

    public static Result gpms() {
        return ok(views.html.gpms.render(GPM.all(), gpmForm));
    }

    public static Result newGPM() {
        Form<GPM> filledForm = gpmForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.gpms.render(GPM.all(), filledForm));
        } else {
            GPM gg = filledForm.get();
            GPM.add(gg.idGpm);
            return redirect(routes.GPMs.gpms());
        }
    }

    public static Result deleteGPM(Long id) {
        GPM.delete(id);
        return redirect(routes.GPMs.gpms());
    }

    public static Result extractData(String idGpm) throws IOException {
        GPM gpm = GPM.findByIdGpm(idGpm);
        DataExtraction.updateProfile(gpm);
        DataExtraction.updateActivity(gpm,100);
        return redirect(routes.GPMs.gpms());
    }
}

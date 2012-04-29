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
        return ok(temp.displayName);
		}
		
    static Form<Word> wordForm = form(Word.class);  
    static Form<Synonym> synonymForm = form(Synonym.class);  
  
    public static Result index() {
        return redirect(routes.Application.words());
    }
  
    public static Result words() {
        return ok(views.html.index.render(Word.all(), wordForm));
    }
  
    public static Result newWord() {
        Form<Word> filledForm = wordForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.index.render(Word.all(), filledForm));
        } else {
            Word.create(filledForm.get());
            return redirect(routes.Application.words());  
        }
    }

    public static Result synonyms(Long wordId) {
        return ok(views.html.synonym.render(Word.findById(wordId), Word.findById(wordId).word, Synonym.all(wordId), synonymForm));
    }

    public static Result addSynonym(Long wordId) {
        Form<Synonym> filledForm = synonymForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.synonym.render(Word.findById(wordId), Word.findById(wordId).word, Synonym.all(wordId), filledForm));
        } else {
            Synonym.create(wordId, filledForm.get());
            return redirect(routes.Application.synonyms(wordId));  
        }
    }

    public static Result deleteSynonym(Long wordId, Long synonymId) {
        Synonym.delete(synonymId);
        return redirect(routes.Application.synonyms(wordId));
    }
  
    public static Result deleteWord(Long id) {
        Word.delete(id);
        return redirect(routes.Application.words());
    }
}

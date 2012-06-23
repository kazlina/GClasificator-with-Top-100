package controllers;

import java.util.*;

import play.mvc.*;
import play.data.*;

import views.html.*;

import models.*;
import play.libs.*;

import java.io.IOException;

public class Words extends Controller {

    public static Result ind() throws IOException {
	    TempProfile temp = GAPI.getProfile("100915540970866628562");
	    DataExtraction data = new DataExtraction();
	    data.newGPM("114536133164105123829");
        return ok(temp.displayName);
		}

    static Form<Word> wordForm = form(Word.class);
    static Form<Synonym> synonymForm = form(Synonym.class);


    // WORD
    public static Result words() {
        return ok(views.html.words.render(Word.all(), wordForm));
    }

    public static Result newWord() {
        Form<Word> filledForm = wordForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.words.render(Word.all(), filledForm));
        } else {
            Word.add(filledForm.get());
            return redirect(routes.Words.words());
        }
    }

    public static Result deleteWord(Long id) {
        Word.delete(id);
        return redirect(routes.Words.words());
    }

    // SYNONYM
    public static Result synonyms(Long wordId) {
        return ok(views.html.synonyms.render(Word.findById(wordId), Word.findById(wordId).word, Synonym.findByWordId(wordId), synonymForm));
    }

    public static Result addSynonym(Long wordId) {
        Form<Synonym> filledForm = synonymForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.synonyms.render(Word.findById(wordId), Word.findById(wordId).word, Synonym.findByWordId(wordId), filledForm));
        } else {
        	Synonym.add(wordId, filledForm.get());
            return redirect(routes.Words.synonyms(wordId));
        }
    }

    public static Result deleteSynonym(Long wordId, Long synonymId) {
        Synonym.delete(synonymId);
        return redirect(routes.Words.synonyms(wordId));
    }

	//you can change word
    public static Result changeWord(Long id) {
        Form<Word> filledForm = form(Word.class).bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.word.render(id, filledForm));
        } else {
            Word.updateWord(id, filledForm.get());
            return redirect(routes.Words.words());
        }
    }
	
    //you can view information about word
    public static Result viewWord(Long wordId) {
        return ok(views.html.word.render(wordId, form(Word.class).fill(Word.findById(wordId))));
    }
	
	//you can change synonym
    public static Result changeSynonym(Long id, Long wordId) {
        Form<Synonym> filledForm = form(Synonym.class).bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.synonym.render(id, wordId, filledForm));
        } else {
            Synonym.updateSynonym(id, filledForm.get());
            return redirect(routes.Words.synonyms(wordId));
        }
    }
	
    //you can view information about synonym
    public static Result viewSynonym(Long synonymId, Long wordId) {
        return ok(views.html.synonym.render(synonymId, wordId, form(Synonym.class).fill(Synonym.findById(synonymId))));
    }
}

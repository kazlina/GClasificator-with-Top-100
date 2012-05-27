package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "Synonym", uniqueConstraints ={
		@UniqueConstraint(columnNames = {
				"word", "synonym" 
				}) 
		})
public class Synonym extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
	
    @ManyToOne
	//@Constraints.Required
	@JoinColumn(name = "word", nullable = false)
	public Word word;	
	
	@Column(name = "synonym", length = 30, nullable = false)
	@Constraints.Required
	public String synonym;
	
	public String toString(){
		return this.synonym;
	}

	public Synonym(String synonym){
		this.synonym = synonym;
	}

	private static Model.Finder<Long, Synonym> find = new Model.Finder<Long, Synonym>(Long.class, Synonym.class);

    public static Synonym findById(Long Id) {
    	return find.where().eq("id", Id).findUnique();
	}
    
    public static List<Synonym> findBySynonim(String element) {
    	return find.where().eq("synonym", element).findList();
	}
    
    public static List<Synonym> findByWordId(Long wordId) {
    	return Word.findById(wordId).synonyms;
	}
    
    public static void add(Long wordId, Synonym synonym) {
    	Ebean.beginTransaction();
    	
    	try {
	    	Word word = Word.findById(wordId);
	    	if (word == null) {
	    		Ebean.endTransaction();
	    		return;
	    	}
	    		
	    	
	    	Synonym findSynonym = find.where().eq("word", word).eq("synonym", synonym.synonym).findUnique();
	    	if (findSynonym != null) {
	    		Ebean.endTransaction();
	    		return;
	    	}
	    	
	    	synonym.word = word;
	        synonym.save();
	        
	        Ebean.commitTransaction();
    	}
    	finally {
			Ebean.endTransaction();
		}
    }

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}

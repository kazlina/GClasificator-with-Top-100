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

    @JoinColumn(name = "word", nullable = false)
	@ManyToOne
	public Word word;

    @Constraints.MaxLength(30)
	@Constraints.Required
	@Constraints.Pattern(value = "[a-zA-Zа-яА-Я]+", message = "Incorrect word")
	@Column(name = "synonym", length = 30, nullable = false)
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
	    	if (word != null) {
		    	Synonym findSynonym = find.where().eq("word", word).eq("synonym", synonym.synonym).findUnique();
		    	if (findSynonym == null) {
		    		synonym.word = word;
		    		synonym.save();
		    	}
	    	}
	        Ebean.commitTransaction();
    	}
    	finally {
			Ebean.endTransaction();
		}
    }

	public static void delete(Long id) {
		find.ref(id).delete();
    }

	public static void updateSynonym(Long idSynonym, Synonym synonym) {
	System.out.println(synonym.synonym);
	Synonym findSynonym = Synonym.findById(idSynonym);
	if (findSynonym == null)
		return;

	findSynonym.synonym = synonym.synonym;
	findSynonym.update();
	}

}

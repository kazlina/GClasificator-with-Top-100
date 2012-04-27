package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;

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
	@Constraints.Required
	@JoinColumn(name = "word", nullable = false)
	public Word word;	
	
	@Column(name = "synonym", length = 30, nullable = false)
	@Constraints.Required
	public String synonym;
	
/*
	public String toString(){
		return this.synonym;
	}

	public Synonym(String synonym){
		this.synonym = synonym;
	}
*/
	public static Model.Finder<Long, Synonym> find = new Model.Finder<Long, Synonym>(Long.class, Synonym.class);

    public static List<Synonym> all(Long wordId) {
        return Word.find.ref(wordId).synonyms;
    }

    public static void create(Long wordId, Synonym synonym) {
        synonym.word = Word.find.ref(wordId);
        synonym.save();
    }

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}

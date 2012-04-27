package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;


@Entity
@Table(name = "Word")
public class Word extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	
	@Column(name = "word", length = 30, nullable = false, unique = true)
	@Constraints.Required
	public String word;

	@OneToMany(mappedBy = "word")
	public List<GroupWord> groupWords;

	@OneToMany(mappedBy = "word")
	public List<PostWord> postWords;

	@OneToMany(mappedBy = "word")
	public List<ProfileWord> profileWords;

	@OneToMany(mappedBy = "word")
	public List<Synonym> synonyms;
/*
	public String toString(){
	return this.word;
	}

	public Word(String word){
		this.word = word;
	}
*/
	public static Model.Finder<Long, Word> find = new Model.Finder<Long, Word>(Long.class, Word.class);

	public static List<Word> all() {
		return find.all();
	}

	public static Word wordById(Long Id) {
		return find.ref(Id);
	}

	public static void create(Word element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}

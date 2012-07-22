package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "Word")
public class Word extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Constraints.MaxLength(30)
	@Constraints.Required
	@Constraints.Pattern(value = "[a-zA-Zа-яА-Я]+", message = "Incorrect word")
	@Column(name = "word", length = 30, nullable = false, unique = true)
	public String word;

	@OneToMany(mappedBy = "word")
	public List<GroupWord> groupWords;

	@OneToMany(mappedBy = "word")
	public List<PostWord> postWords;

	@OneToMany(mappedBy = "word")
	public List<ProfileWord> profileWords;

	@OneToMany(mappedBy = "word")
	public List<Synonym> synonyms;

	public String toString(){
		return this.word;
	}

	public Word(String word){
		this.word = word;
	}

	private static Model.Finder<Long, Word> find = new Model.Finder<Long, Word>(Long.class, Word.class);

	public static List<Word> all() {
		return find.all();
	}

	public static List<String> allInString() {
		List <String> wordsInString = new ArrayList<String>();
		for (Word word: all())
			wordsInString.add(word.word);
		return wordsInString;
	}
	
	public static Integer size() {
		return find.findRowCount();
	}
	
	public static Word findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}

	public static Word findByWord(String element) {
		return find.where().eq("word", element).findUnique();
	}

	public static void add(Word element) {
		Word findWord = Word.findByWord(element.word);
		if (findWord == null)
			element.save();
	}

	public static void delete(Long id) {
		Word findWord = findById(id);
		if (findWord == null)
			return;
		
		// delete groups for word
		for (GroupWord gw: findWord.groupWords)
			GroupWord.delete(gw.id);
		
		// delete posts for word
		for (PostWord pw: findWord.postWords)
			PostWord.delete(pw.id);
				
		// delete profiles for word
		for (ProfileWord pw: findWord.profileWords)
			ProfileWord.delete(pw.id);
		
		// delete synonyms for word
		for (Synonym syn: findWord.synonyms)
			Synonym.delete(syn.id);
		
		findWord.delete();
    }

	public static void updateWord(Long idWord, Word word) {
		System.out.println(word.word);
		Word findWord = Word.findById(idWord);
		if (findWord == null)
			return;
	
		findWord.word = word.word;
		findWord.update();
	}

}

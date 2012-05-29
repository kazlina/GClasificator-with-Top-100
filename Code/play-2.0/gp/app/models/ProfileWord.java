package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "ProfileWord", uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"profile", "word"
		})
	})
public class ProfileWord extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Constraints.Required
	@JoinColumn(name = "profile", nullable = false)
    @ManyToOne
    public Profile profile;

    @Constraints.Required
	@JoinColumn(name = "word", nullable = false)
    @ManyToOne
    public Word word;

    @Constraints.Min(1)
    @Constraints.Required
	@Column(name = "amount", nullable = false)
    public Integer amount;

    private ProfileWord(Profile profile, Word word, int amount){
        this.profile = profile;
        this.word = word;
        this.amount = amount;
        }

    private static Model.Finder<Long, ProfileWord> find = new Model.Finder<Long, ProfileWord>(Long.class, ProfileWord.class);

    public static ProfileWord findById(Long Id) {
    	return find.where().eq("id", Id).findUnique();
	}
	
	public static List<ProfileWord> findByProfile(Long id) {
		return Profile.findById(id).words;
	}
	
	public static List<ProfileWord> findByWord(Long id) {
		return Word.findById(id).profileWords;
	}

	public static void add(Profile profile, String word, int amount) {
		Ebean.beginTransaction();
		
		try {
			Word findWord = Word.findByWord(word);
			if (findWord != null) {
				ProfileWord findProfileWord = find.where().eq("profile", profile).eq("word", findWord).findUnique();
				if (findProfileWord == null) {
					ProfileWord element = new ProfileWord(profile, findWord, amount);
					element.save();
				}
			}
			
			List<Synonym> synonyms = Synonym.findBySynonim(word); 
			for (int i = 0; i < synonyms.size(); i ++) {
				Word synonymWord = synonyms.get(i).word;
				ProfileWord findProfileWord = find.where().eq("profile", profile).eq("word", synonymWord).findUnique();
				if (findProfileWord == null) {
					ProfileWord element = new ProfileWord(profile, synonymWord, amount);
					element.save();
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
}

package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;

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

    @Constraints.Required
	@Column(name = "amount", nullable = false)
    public Integer amount;

/*
    public ProfileWord(Profile profile, Word word, int amount){
        this.profile = profile;
        this.word = word;
        this.amount = amount;
        }
*/
	public static Finder<Long, ProfileWord> find = new Finder<Long, ProfileWord>(Long.class, ProfileWord.class);

	public static List<ProfileWord> all() {
		return find.all();
	}

	public static ProfileWord profileWordById(Long Id) {
		return find.ref(Id);
	}

	public static void create(ProfileWord element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}

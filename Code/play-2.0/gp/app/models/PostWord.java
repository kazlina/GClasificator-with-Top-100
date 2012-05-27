package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "PostWord", uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"post", "word"
		})
	})
public class PostWord extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Constraints.Required
	@JoinColumn(name = "post", nullable = false)
    @ManyToOne
    public Post post;

    @Constraints.Required
	@JoinColumn(name = "word", nullable = false)
    @ManyToOne
    public Word word;

    @Constraints.Required
	@Column(name = "amount", nullable = false)
    public int amount;

    private PostWord(Post post, Word word, int amount){
        this.post = post;
        this.word = word;
        this.amount = amount;
        }

    private static Model.Finder<Long, PostWord> find = new Model.Finder<Long, PostWord>(Long.class, PostWord.class);

    public static PostWord findById(Long Id) {
    	return find.where().eq("id", Id).findUnique();
	}
	
	public static List<PostWord> findByPost(Long id) {
		return Post.findById(id).words;
	}
	
	public static List<PostWord> findByWord(Long id) {
		return Word.findById(id).postWords;
	}

	public static void add(Post post, String word, int amount) {
		Ebean.beginTransaction();
		
		Word findWord = Word.findByWord(word);
		if (findWord != null) {
			PostWord findPostWord = find.where().eq("post", post).eq("word", findWord).findUnique();
			if (findPostWord == null) {
				PostWord element = new PostWord(post, findWord, amount);
				element.save();
			}
		}
		
		List<Synonym> synonyms = Synonym.findBySynonim(word); 
		for (int i = 0; i < synonyms.size(); i ++) {
			Word synonymWord = synonyms.get(i).word;
			PostWord findPostWord = find.where().eq("post", post).eq("word", synonymWord).findUnique();
			if (findPostWord == null) {
				PostWord element = new PostWord(post, synonymWord, amount);
				element.save();
			}
		}
		
		Ebean.commitTransaction();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}

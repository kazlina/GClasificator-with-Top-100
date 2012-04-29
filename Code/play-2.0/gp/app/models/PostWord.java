package models;

import java.util.*;

import javax.persistence.*;
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

    public PostWord(Post post, Word word, int amount){
        this.post = post;
        this.word = word;
        this.amount = amount;
        }

    private static Model.Finder<Long, PostWord> find = new Model.Finder<Long, PostWord>(Long.class, PostWord.class);

    public static PostWord findById(Long Id) {
		return find.ref(Id);
	}
	
	public static List<PostWord> findByPost(Long id) {
		return Post.findById(id).words;
	}
	
	public static List<PostWord> findByWord(Long id) {
		return Word.findById(id).postWords;
	}

	public static void create(PostWord element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}

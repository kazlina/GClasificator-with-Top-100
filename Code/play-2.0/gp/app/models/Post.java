package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;

@Entity
@Table(name = "Post")
public class Post extends Model {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.Required
    @Column(name = "postId", length = 30, nullable = false, unique = true)
    public String postId;
    
    @Constraints.Required
    @JoinColumn(name = "gpm", nullable = false)
    @ManyToOne
    public GPM gpm;

    @Constraints.Required
    @Column(name = "dateCreate", nullable = false)
    public Date date;

	@Constraints.Required
	@JoinColumn(name = "kindContent", nullable = false)
	@ManyToOne
	public Content kindContent;

	@Column(name = "nComment")
    public Integer nComment;

    @Column(name = "nPlusOne")
    public Integer nPlusOne;

    @Column(name = "nResharers")
    public Integer nResharers;

    @Constraints.Required
	@Column(name = "isRepost", nullable = false)
    public boolean isRepost;

    @OneToMany(mappedBy = "post")
    public List<PostLink> links;

    @OneToMany(mappedBy = "post")
    public List<PostWord> words;
/*
    public Post(GPM gpm, Date publishedData, String kindContent, int nComments, int nPlusOne, int nResharers, boolean isRepost) {
        this.gpm = gpm;
        this.date = publishedData;
        this.kindContent = kindContent;
        this.nComment = nComments;
        this.nPlusOne = nPlusOne;
        this.nResharers = nResharers;
        this.isRepost = isRepost;
        }
 */
	public static Model.Finder<Long, Post> find = new Model.Finder<Long, Post>(Long.class, Post.class);

	public static List<Post> all() {
		return find.all();
	}

	public static Post postById(Long Id) {
		return find.ref(Id);
	}

	public static void create(Post element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}

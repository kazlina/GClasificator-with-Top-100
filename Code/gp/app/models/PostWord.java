package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Post_words")
@Entity
public class PostWord extends GenericModel {

	@Id
	//@JoinColumn(name="Id_GPM")
    @ManyToOne
    /*@JoinColumns({
    	@JoinColumn(name="Id_GPM", referencedColumnName="Id"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    }) */
    public Post post;
    
    @Id
	@Required
	@JoinColumn(name="Id_Word")
    @ManyToOne
    public Word word;
    
    @Required
	@Min(1)
	@Column(name="Amount", nullable=false)
    public int amount;
    
    public PostWord(){};
    
    public PostWord(Post posts, Word word, int amount){
        this.post = posts;
        this.word = word;
        this.amount = amount;
        }
}

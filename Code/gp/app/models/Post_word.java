package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Post_word")
@Entity
public class Post_word extends GenericModel {

	@Id
	//@JoinColumn(name="Id_GPM")
    @ManyToOne
    /*@JoinColumns({
    	@JoinColumn(name="Id_GPM", referencedColumnName="Id"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    }) */
    public Posts post;
    
    @Id
	@Required
	@JoinColumn(name="Id_Word")
    @ManyToOne
    public Word_dictionary Id_Word;
    
    @Required
	@Min(1)
	@Column(name="Count", nullable=false)
    public int Count;
    
    public Post_word(Posts Id, Word_dictionary Id_Word, int Count){
        this.post = Id;
        this.Id_Word = Id_Word;
        this.Count = Count;
        }
}

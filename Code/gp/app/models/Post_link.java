package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Post_link")
@Entity
public class Post_link extends GenericModel {

	//@Id
	//@EmbeddedId
	//public Key key;
	/*@JoinColumn(name="Id_GPM")
    public GPM Id_GPM;
    
    //@Id
    @Column(name="date")
    public Date date;
    */
    @Id
    //@JoinColumn(name="post")
    @ManyToOne
    /*@JoinColumns({
    	@JoinColumn(name="Id_GPM", referencedColumnName="Id"),
    	@JoinColumn(name="date", referencedColumnName="date")
    }) */
    public Posts post;
    
    @Id
	@Required
	@JoinColumn(name="Id_Link")
    @ManyToOne
    public Link_dictionary Id_Link;
    
    @Required
	@Min(1)
	@Column(name="Count", nullable=false)
    public int Count;
    
    public Post_link(/*GPM Id, Posts post,*/ Link_dictionary Id_Link, int Count){
        //this.Id_GPM = Id;
        //this.date = new Date();
        //this.post = post;
        this.Id_Link = Id_Link;
        this.Count = Count;
        }

}

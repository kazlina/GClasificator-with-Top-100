package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Post_links")
@Entity
public class PostLink extends GenericModel {

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
    public Post post;
    
    @Id
	@Required
	@JoinColumn(name="Id_Link")
    @ManyToOne
    public Link link;
    
    @Required
	@Min(1)
	@Column(name="Amount", nullable=false)
    public int amount;
    
    public PostLink(){};
    
    public PostLink(/*GPM Id, Posts post,*/ Link link, int amount){
        //this.Id_GPM = Id;
        //this.date = new Date();
        //this.post = post;
        this.link = link;
        this.amount = amount;
        }

}

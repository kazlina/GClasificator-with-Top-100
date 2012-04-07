package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Posts")
@Entity
public class Posts extends GenericModel {

	/*//@Id
	@JoinColumn(name="Id")
    @ManyToOne
    public GPM Id;
    
    //@Id
    @Column(name="date")
	public Date date;
	*/
	
	@EmbeddedId
	public Key key;
	
	@Required
	@MaxSize(value=10)
	@Column(name="Type", length=10, nullable=false)
	public String Type;
	
	@Column(name="nComment")
    public int nComment;
    
    @Column(name="nPlus_one")
    public int nPlus_one;
    
    @Column(name="nReshare")
    public int nReshare;
    
    @Required
	@Column(name="isRepost", nullable=false)
    public boolean isRepost;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="post")
    /*@JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id"),
    	@JoinColumn(name="date", referencedColumnName="date")
    })*/
    public List<Post_link> followedLinks; 
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="post")
    /*@JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id_GPM"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    })*/
    public List<Post_word> followedWords; 
   
    public Posts(GPM Id_GPM, String Type, int nComments, int nPlus_one, int nReshare, boolean isRepost) {
        this.key.Id = Id_GPM;
        this.key.date = new Date();
        this.Type = Type;
        this.nComment = nComments;
        this.nPlus_one = nPlus_one;
        this.nReshare = nReshare;
        this.isRepost = isRepost;
        }
}

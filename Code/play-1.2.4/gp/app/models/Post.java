package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Posts")
@Entity
@IdClass(Key.class)
public class Post extends GenericModel {

    @Id
    @Required
    @JoinColumn(name="Id_GPM")
    @ManyToOne
    public GPM gpm;
    
    @Id
    @Column(name="Published_Data")
    public Date date;
	
	
	@Required
	@MaxSize(value=10)
	@Column(name="Kind_Content", length=10, nullable=false)
	public String kindContent;
	
	@Column(name="nComment")
    public int nComment;
    
    @Column(name="nPlus_one")
    public int nPlusOne;
    
    @Column(name="nReshare")
    public int nResharers;
    
    @Required
	@Column(name="isRepost", nullable=false)
    public boolean isRepost;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="post")
    /*@JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id"),
    	@JoinColumn(name="date", referencedColumnName="date")
    })*/
    public List<PostLink> links; 
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="post")
    /*@JoinColumns({
    	@JoinColumn(name="Id", referencedColumnName="Id_GPM"),
    	@JoinColumn(name="Date", referencedColumnName="Date")
    })*/
    public List<PostWord> words; 
    
    public Post(){};
   
    public Post(GPM gpm, Date publishedData, String kindContent, int nComments, int nPlusOne, int nResharers, boolean isRepost) {
        this.gpm = gpm;
        this.date = publishedData;
        this.kindContent = kindContent;
        this.nComment = nComments;
        this.nPlusOne = nPlusOne;
        this.nResharers = nResharers;
        this.isRepost = isRepost;
        } 
    }

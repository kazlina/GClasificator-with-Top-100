package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class Statistics extends Model {	//GenericModel {

	//@Id
	//@JoinColumn(name="Id")
    @ManyToOne
    public GPM Id_GPM;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "Id_GPM") 
    public List<Links> followedLinks; 
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "Id_GPM") 
    public List<Keywords> followedWords; 
   
    //@Id
	public Date date;
	
    public int Comments;
    public int Posts;
    public int Plus_one;
    public int Circled_by;
    public int Shared;
    public int Comments_repost;
    public int Repost;
    public int Plus_repost;
    public int Shared_repost;

    public Statistics(GPM Id_GPM, int Comments, int Posts, int Plus_one, int Circled_by, int Shared, int Repost,int Plus_repost,int Shared_repost) {
        this.Id_GPM = Id_GPM;
        this.date = new Date();
        this.Comments = Comments;
        this.Posts = Posts;
        this.Plus_one = Plus_one;
        this.Circled_by = Circled_by;
        this.Shared = Shared;
        this.Comments_repost = Comments_repost;
        this.Repost = Repost;
        this.Plus_repost = Plus_repost;
        this.Shared_repost = Shared_repost;
        }

}

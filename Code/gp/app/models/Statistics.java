package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Statistics extends Model {

    @ManyToOne
    public GPM Id;
    
    //public int Id;

    @ManyToMany(cascade=CascadeType.ALL) 
    public Set<Links_Def> followedLinks = new HashSet<Links_Def>(); 
    
    @ManyToMany(cascade=CascadeType.ALL) 
    public Set<Keywords_Def> followedWords = new HashSet<Keywords_Def>(); 
    
    public Date date;
    public int Comments;
    public int Posts;
    public int Likes;
    public int Circled_by;
    public int Shared;
    public int Comments_repost;
    public int Repost;
    public int Likes_repost;
    public int Shared_repost;

    public Statistics(GPM Id, int Comments, int Posts, int Likes, int Circled_by, int Shared, int Repost,int Likes_repost,int Shared_repost) {
        this.Id = Id;
        this.date = new Date();
        this.Comments = Comments;
        this.Posts = Posts;
        this.Likes = Likes;
        this.Circled_by = Circled_by;
        this.Shared = Shared;
        this.Comments_repost = Comments_repost;
        this.Repost = Repost;
        this.Likes_repost=Likes_repost;
        this.Shared_repost=Shared_repost;
        }

}

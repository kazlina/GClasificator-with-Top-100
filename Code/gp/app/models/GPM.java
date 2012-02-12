package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class GPM extends Model {
 
    public String id_gplus;
    public String Name;
    public String Tagline;
    public String Photo_link;
    public Date Last_update;
    public int GR;
    public int numberofposts;
    public int sum_plus;
    public int sum_comment;
    public int sum_reshared;
 
    
    public GPM(String id_gplus, String Name, String Tagline, String Photo_link,int GR, int numberofposts,int sum_plus,int sum_comment, int sum_reshared) {
        this.id_gplus = id_gplus;
        this.Name = Name;
        this.Tagline = Tagline;
        this.Photo_link = Photo_link;
        this.Last_update = new Date();
        this.GR = GR;
        this.numberofposts = numberofposts;
        this.sum_plus = sum_plus;
        this.sum_comment = sum_comment;
        this.sum_reshared = sum_reshared;
    }
 
}

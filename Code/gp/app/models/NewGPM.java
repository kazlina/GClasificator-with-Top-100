package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class NewGPM extends Model {
 
    public String id_google;
    public Date date;
    public int num;
 
    
    public NewGPM(String id_gplus,int num) {
        this.id_google = id_google;
        this.date = new Date();
        this.num = num;
    }
 
}

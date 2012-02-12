package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Page extends Model {
 
    public String id_google;
    
    public Page(String id_google) {
        this.id_google = id_google;
    }
 
}

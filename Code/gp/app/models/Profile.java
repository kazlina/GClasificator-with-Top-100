package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Profile extends Model {

    @ManyToOne
    public GPM Id;
    
    //public int Id;
    public Date date;
    public String Name;
    public String Photo_link;
    public Boolean Gender;
    public String Aim;
    public String Tagline;
    public String Status;

    public Profile(GPM Id, String Name, String Photo_link, Boolean Gender, String Aim, String Tagline, String Status) {
        this.Id = Id;
        this.date = new Date();
        this.Name = Name;
        this.Photo_link = Photo_link;
        this.Gender = Gender;
        this.Aim = Aim;
        this.Tagline = Tagline;
        this.Status = Status;
        }

}

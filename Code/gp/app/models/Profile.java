package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class Profile extends GenericModel {

	@Id
	@JoinColumn(name="Id")
	@ManyToOne
    public GPM Id;
    
    @Id
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

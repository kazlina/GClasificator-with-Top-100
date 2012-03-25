package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class Look_Id extends GenericModel {

    @Id
	@Match(value="^\\d{21}$", message="Incorrect identifer")
    @Column(length=21)
    public String Id;
    
    public Date date;

    public Look_Id(String Id, Date date) {
        this.Id = Id;
        this.date = date;
        }

}

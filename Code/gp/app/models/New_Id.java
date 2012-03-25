package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class New_Id extends GenericModel {

	@Id
	@Match(value="^\\d{21}$", message="Incorrect identifer")
    @Column(length=21)
    public String Id;
    
    public int Severity;
    
    public New_Id(String Id, int Severity) {
        this.Id = Id;
        this.Severity = Severity;
        }

}

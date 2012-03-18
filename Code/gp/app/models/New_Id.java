package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class New_Id extends Model {

    public int Id;
    public int Severity;

    public New_Id(int Id, int Severity) {
        this.Id = Id;
        this.Severity = Severity;
        }

}

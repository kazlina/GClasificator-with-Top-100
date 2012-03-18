package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Look_Id extends Model {

    public int Id;
    public Date date;

    public Look_Id(int Id, Date date) {
        this.Id = Id;
        this.date = date;
        }

}

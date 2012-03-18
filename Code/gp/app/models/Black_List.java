package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Black_List extends Model {

    public Date date;

    @OneToOne
    public GPM GPM;


    public Black_List(GPM GPM) {
        this.GPM = GPM;
        this.date = new Date();
        }

}

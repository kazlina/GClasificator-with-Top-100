package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Group extends Model {

    //public int Id;
    public String Name;
    public String Picture;


    public Group(String Name, String Picture) {
        this.Name = Name;
        this.Picture = Picture;
        }

}

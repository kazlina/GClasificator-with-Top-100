package models;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;
import play.db.jpa.*;
import play.data.validation.*;

@Embeddable
public class Key implements Serializable {

	@Required
	public GPM gpm;
	
	@Required
        public Date date;
}

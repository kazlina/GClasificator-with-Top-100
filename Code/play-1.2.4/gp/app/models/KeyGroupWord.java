package models;

import java.util.*;
import javax.persistence.*;

import java.io.Serializable;
import play.db.jpa.*;
import play.data.validation.*;

@Embeddable
public class KeyGroupWord implements Serializable {

	@Required
	public Group group;
	
	@Required
    public Word word;

 }

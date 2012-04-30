import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class RelationshipTest {
    
	private Relationship relation;
	private String text;
	
    private Relationship create(String value) {
    	relation = new Relationship(value);
    	Relationship.create(relation);
        return relation;
    }

    @Test
    public void CreateAndDeleteRelation() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   relation = create(text = "good relation");
               assertThat(relation.status).isEqualTo(text);
               
               Relationship findRelation = Relationship.findByStatus(text);
               assertThat(findRelation.status == text);

               Relationship.delete(findRelation.id);
               Relationship relationDeleted = Relationship.findByStatus(text);
               assertThat(relationDeleted == null);
           }
        });
    }

    @Test
    public void FindNotExistedRelation() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   relation = Relationship.findByStatus("very beutiful status");
               assertThat(relation == null);
           }
        });
    }
 
    @Test
    public void RelationToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  relation = create(text = "good relation");
              
              String relationText = relation.toString();
              assertThat(relationText == text);

              Relationship.delete(relation.id);
          }
       });
    }
   
    @Test
    public void GetAllRelations() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              create("First relation");
              create("Second relation");
              create("Third relation");
              
              List<Relationship> allRelation = Relationship.all();
              assertThat(allRelation.size() == 3);
              
              for (int i = 0; i < allRelation.size(); i ++)
            	  Relationship.delete(allRelation.get(i).id);
              
              allRelation = Relationship.all();
              assertThat(allRelation.size() == 0);
          }
       });
    }
    
    @Test
    public void FindRelationById() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   relation = create("good relation");
               
               Relationship findRelation = Relationship.findById(relation.id);
               assertThat(findRelation != null);

               Relationship.delete(findRelation.id);
           }
        });
    }
}
 
import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class RelationshipTest {
    
	@Test
    public void CreateAndDeleteRelation() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String text = "good relation";
        	   Relationship.add(text);
        	   Relationship findRelation = Relationship.findByStatus(text);
               assertThat(findRelation).isNotNull();

               Relationship.delete(findRelation.id);
               Relationship relationDeleted = Relationship.findByStatus(text);
               assertThat(relationDeleted).isNull();
           }
        });
    }

    @Test
    public void FindNotExistedRelation() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   Relationship relation = Relationship.findByStatus("very beutiful status");
               assertThat(relation).isNull();
           }
        });
    }
 
    @Test
    public void RelationToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String text = "good relation";
        	  Relationship.add(text);
        	  Relationship relation = Relationship.findByStatus(text);
              assertThat(relation).isNotNull();
              
              String relationText = relation.toString();
              assertThat(relationText).isEqualTo(text);

              Relationship.delete(relation.id);
          }
       });
    }
   
    @Test
    public void GetAllRelations() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  Relationship.add("First relation");
        	  Relationship.add("Second relation");
        	  Relationship.add("Third relation");
              
              List<Relationship> allRelation = Relationship.all();
              assertThat(allRelation.size()).isEqualTo(3);
              
              for (int i = 0; i < allRelation.size(); i ++)
            	  Relationship.delete(allRelation.get(i).id);
              
              allRelation = Relationship.all();
              assertThat(allRelation.size()).isEqualTo(0);
          }
       });
    }
    
    @Test
    public void FindRelationById() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String text = "good relation";
        	   Relationship.add(text);
        	   Relationship relation = Relationship.findByStatus(text);
               assertThat(relation).isNotNull();
               
               Relationship findRelation = Relationship.findById(relation.id);
               assertThat(findRelation ).isNotNull();

               Relationship.delete(findRelation.id);
           }
        });
    }
}
 
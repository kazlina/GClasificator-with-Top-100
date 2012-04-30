import org.junit.*;

import java.util.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class GroupTest {
    
	private Group group;
	private String name;
	
    private Group create(String value) {
    	group = new Group(value, "", "", "", 0, 0, 0, 0, 0);
    	Group.create(group);
        return group;
    }
    
    @Test
    public void CreateAndDeleteGroup() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   group = create(name = "mathematics");
               assertThat(group.name).isEqualTo(name);
               
               Group findGroup = Group.findByName(name);
               assertThat(findGroup.name == name);

               Group.delete(findGroup.id);
               Group groupDeleted = Group.findByName(name);
               assertThat(groupDeleted == null);
           }
        });
    }

    @Test
    public void FindNotExistedGroup() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   Group findGroup = Group.findByName("phisics");
               assertThat(findGroup == null);
           }
        });
    }
 
    @Test
    public void GroupToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              group = create(name = "photos");
              
              String textGroup = group.toString();
              assertThat(textGroup == name);
              
              Group.delete(group.id);
          }
       });
    }
   
    @Test
    public void GetAllGroups() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              create("mathematics");
              create("phisics");
              create("lords");
              
              List<Group> allGroup = Group.all();
              assertThat(allGroup.size() == 3);
              
              for (int i = 0; i < allGroup.size(); i ++)
            	  Group.delete(allGroup.get(i).id);
              
              allGroup = Group.all();
              assertThat(allGroup.size() == 0);
          }
       });
    }
   
    @Test
    public void FindGroupById() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              create(name = "lords");
              
              group = Group.findByName(name);
              assertThat(group != null);
              
              Group.delete(group.id);
              
              group = Group.findById(group.id);
              assertThat(group == null);
          }
       });
    }
}
 
import org.junit.*;

import java.util.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class GroupTest {
    
	private Group create(String value) {
		Group group = new Group(value, "", "", "", 0, 0, 0, 0, 0);
    	Group.add(group);
        return group;
    }
    
    @Test
    public void CreateAndDeleteGroup() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String name = "mathematics";
        	   Group group = create(name);
               assertThat(group.name).isEqualTo(name);
               
               Group findGroup = Group.findByName(name);
               assertThat(findGroup.name).isEqualTo(name);

               Group.delete(findGroup.id);
               Group groupDeleted = Group.findByName(name);
               assertThat(groupDeleted).isNull();
           }
        });
    }

    @Test
    public void FindNotExistedGroup() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   Group findGroup = Group.findByName("phisics");
               assertThat(findGroup).isNull();
           }
        });
    }
 
    @Test
    public void GroupToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String name = "mathematics";
        	  Group group = create(name);
              assertThat(group.name).isEqualTo(name);
              
              String textGroup = group.toString();
              assertThat(textGroup).isEqualTo(name);
              
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
              assertThat(allGroup.size()).isEqualTo(3);
              
              for (int i = 0; i < allGroup.size(); i ++)
            	  Group.delete(allGroup.get(i).id);
              
              allGroup = Group.all();
              assertThat(allGroup.size()).isEqualTo(0);
          }
       });
    }
   
    @Test
    public void FindGroupById() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String name = "mathematics";
        	  Group group = create(name);
              assertThat(group.name).isEqualTo(name);
              
              group = Group.findByName(name);
              assertThat(group).isNotNull();
              
              Group.delete(group.id);
              
              group = Group.findById(group.id);
              assertThat(group).isNull();
          }
       });
    }
}
 
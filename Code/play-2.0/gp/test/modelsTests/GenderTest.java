import org.junit.*;

import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class GenderTest {
    
	@Test
    public void CreateAndDeleteGender() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String text = "man";
        	   Gender.add(text);
        	   
        	   Gender findGender = Gender.findByValue(text);
               assertThat(findGender).isNotNull();

               Gender.delete(findGender.id);
               Gender genderDeleted = Gender.findByValue(text);
               assertThat(genderDeleted).isNull();
           }
        });
    }

    @Test
    public void FindNotExistedGender() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   Gender gender = Gender.findByValue("woman");
               assertThat(gender).isNull();
           }
        });
    }
 
    @Test
    public void GenderToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String text = "man";
        	  Gender.add(text);
       	   
        	  Gender gender = Gender.findByValue(text);
              assertThat(gender).isNotNull();
              
              String genderText = gender.toString();
              assertThat(genderText).isEqualTo(text);

              Gender.delete(gender.id);
          }
       });
    }
   
    @Test
    public void GetAllGenders() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  Gender.add("man");
        	  Gender.add("woman");
        	  Gender.add("none");
              
              List<Gender> allGender = Gender.all();
              assertThat(allGender.size()).isEqualTo(3);
              
              for (int i = 0; i < allGender.size(); i ++)
            	  Gender.delete(allGender.get(i).id);
              
              allGender = Gender.all();
              assertThat(allGender.size()).isEqualTo(0);
          }
       });
    }
    
    @Test
    public void FindGenderById() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String text = "man";
        	   Gender.add(text);
        	   
        	   Gender gender = Gender.findByValue(text);
               assertThat(gender).isNotNull();
               
        	   Gender findGender = Gender.findById(gender.id);
               assertThat(findGender).isNotNull();

               Gender.delete(findGender.id);
           }
        });
    }
}
 
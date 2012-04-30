import org.junit.*;

import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class GenderTest {
    
	private Gender gender;
	private String text;
	
    private Gender create(String value) {
    	gender = new Gender(value);
    	Gender.create(gender);
        return gender;
    }

    @Test
    public void CreateAndDeleteGender() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   gender = create(text = "man");
               assertThat(gender.value).isEqualTo(text);
               
               Gender findGender = Gender.findByValue(text);
               assertThat(findGender.value == text);

               Gender.delete(findGender.id);
               Gender genderDeleted = Gender.findByValue(text);
               assertThat(genderDeleted == null);
           }
        });
    }

    @Test
    public void FindNotExistedGender() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   gender = Gender.findByValue("woman");
               assertThat(gender == null);
           }
        });
    }
 
    @Test
    public void GenderToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  gender = create(text = "man");
              
              String genderText = gender.toString();
              assertThat(genderText == text);

              Gender.delete(gender.id);
          }
       });
    }
   
    @Test
    public void GetAllGenders() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              create("man");
              create("woman");
              create("none");
              
              List<Gender> allGender = Gender.all();
              assertThat(allGender.size() == 3);
              
              for (int i = 0; i < allGender.size(); i ++)
            	  Gender.delete(allGender.get(i).id);
              
              allGender = Gender.all();
              assertThat(allGender.size() == 0);
          }
       });
    }
    
    @Test
    public void FindGenderById() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   gender = create("man");
               
        	   Gender findGender = Gender.findById(gender.id);
               assertThat(findGender != null);

               Gender.delete(findGender.id);
           }
        });
    }
}
 
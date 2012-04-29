import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class NewGPMTest {
    
	private NewGPM GPM;
	private String idGpm;
	
    private NewGPM create(String id, int nMentiens) {
        GPM = new NewGPM(id, nMentiens);
        NewGPM.create(GPM);
        return GPM;
    }

    @Test
    public void CreateAndDeleteNewGPM() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   GPM = create(idGpm = "100915540970866628562", 1);
               assertThat(GPM.idGpm).isEqualTo(idGpm);
               
               NewGPM findGPM = NewGPM.findByIdGPM(idGpm);
               assertThat(findGPM.idGpm == idGpm);

               NewGPM.delete(findGPM.id);
               
               NewGPM gpmDeleted = NewGPM.findByIdGPM(idGpm);
               assertThat(findGPM == null);
           }
        });
    }

    @Test
    public void FindNotExistedNewGPM() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   GPM = NewGPM.findByIdGPM("100915540970866628562");
               assertThat(GPM == null);
           }
        });
    }
 
    @Test
    public void NewGPMtoString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  GPM = create(idGpm = "100915540970866628562", 1);
              
              String textGPM = GPM.toString();
              assertThat(textGPM == idGpm);

              NewGPM.delete(GPM.id);
         }
	   });
    }
   
    @Test
    public void GetAllNewGPM() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              create("100915540970866628562", 1);
              create("100915540970868628562", 3);
              create("100915540970867628562", 2);
              
              List<NewGPM> allGPM = NewGPM.all();
              assertThat(allGPM.size() == 3);
              assertThat(allGPM.get(0).nMentiens == 3);
              assertThat(allGPM.get(1).nMentiens == 2);
              assertThat(allGPM.get(2).nMentiens == 1);
              
              for (int i = 0; i < allGPM.size(); i ++)
            	  NewGPM.delete(allGPM.get(i).id);
              
              allGPM = NewGPM.all();
              assertThat(allGPM.size() == 0);
          }
       });
    }
   
    @Test
    public void FindNewGPMById() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   GPM = create(idGpm = "100915540970866628562", 1);
               
               NewGPM findGPM = NewGPM.findById(GPM.id);
               assertThat(findGPM != null);

               NewGPM.delete(findGPM.id);
           }
        });
    }
}
 
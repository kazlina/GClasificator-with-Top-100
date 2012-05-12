import org.junit.*;

import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class ContentTest {

	@Test
    public void CreateAndDeleteContent() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String text = "link";
        	   Content.add(text);
               Content content = Content.findByKind(text);
               assertThat(content != null);

               Content.delete(content.id);
               
               Content contentDeleted = Content.findByKind(text);
               assertThat(contentDeleted == null);
           }
        });
    }

    @Test
    public void FindNotExistedContent() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   Content content = Content.findByKind("video");
               assertThat(content == null);
           }
        });
    }
 
    @Test
    public void ContentToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String text = "photo";
        	  Content.add(text);
              Content content = Content.findByKind(text);
              assertThat(content != null);
              
              String contentText = content.toString();
              assertThat(contentText == text);

              Content.delete(content.id);
          }
       });
    }
   
    @Test
    public void GetAllContents() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  Content.add("link");
        	  Content.add("text");
        	  Content.add("audio");
              
              List<Content> allContent = Content.all();
              assertThat(allContent.size() == 3);
              
              for (int i = 0; i < allContent.size(); i ++)
            	  Content.delete(allContent.get(i).id);
              
              allContent = Content.all();
              assertThat(allContent.size() == 0);
          }
       });
    }
    
    @Test
    public void FindContentById() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String text = "photo";
        	   Content.add(text);
               Content content = Content.findByKind(text);
               assertThat(content != null);
               
        	   Content findContent = Content.findById(content.id);
               assertThat(findContent != null);

               Content.delete(findContent.id);
           }
        });
    }
}
 
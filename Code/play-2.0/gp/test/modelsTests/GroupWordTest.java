import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class GroupWordTest {
    
	private GroupWord groupWord;
	private Word word;
	private Group group;
	private String textWord;
	private String nameGroup;
	
	private GroupWord createGroupWord(Group group, Word word) {
		groupWord = new GroupWord(group, word, 0, 0);
		GroupWord.create(groupWord);
        return groupWord;
    }
	
	private Word createWord(String value) {
        word = new Word(value);
        Word.create(word);
        return word;
    }
	
	private Group createGroup(String value) {
    	group = new Group(value, "", "", "", 0, 0, 0, 0, 0);
    	Group.create(group);
        return group;
    }
    
    @Test
    public void CreateAndDeleteGroupWord() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
               createWord(textWord = "Maximus");
               word = Word.findByWord(textWord);
               assertThat(word.word != null);
               
               createGroup(nameGroup = "lords");
               group = Group.findByName(nameGroup);
               assertThat(group.name != null);

               groupWord = createGroupWord(group, word);
               assertThat(groupWord.group).isEqualTo(group);
               
               GroupWord findGroupWord = GroupWord.findById(groupWord.id);
               assertThat(findGroupWord.word == word);
               
               GroupWord.delete(groupWord.id);
               GroupWord groupWordDeleted = GroupWord.findById(groupWord.id);
               assertThat(groupWordDeleted == null);
               
               Word.delete(word.id);
               Group.delete(group.id);
           }
        });
    }

   @Test
   public void GetWordsForGroup() {
   	running(fakeApplication(), new Runnable() {
          public void run() {
              createGroup(nameGroup = "flowers");
              group = Group.findByName(nameGroup);
              assertThat(group != null);

              word = createWord("rose");
              createGroupWord(group, word);
              word = createWord("tulip");
              createGroupWord(group, word);
              word = createWord("dandelion");
              createGroupWord(group, word);
              
              List<GroupWord> groupWords = GroupWord.findByGroup(group.id);
              assertThat(groupWords.size() == 3);
              
              for (int i = 0; i < groupWords.size(); i ++)
            	  GroupWord.delete(groupWords.get(i).id);
              
              groupWords = GroupWord.findByGroup(group.id);
              assertThat(groupWords.size() == 0);
              
              Group.delete(group.id);
              Word.delete(Word.findByWord("rose").id);
              Word.delete(Word.findByWord("tulip").id);
              Word.delete(Word.findByWord("dandelion").id);
          }
       });
   }
   
   @Test
   public void GetGroupsForWord() {
   	running(fakeApplication(), new Runnable() {
          public void run() {
              createWord(textWord = "image");
              word = Word.findByWord(textWord);
              assertThat(word != null);

              group = createGroup("photographers");
              createGroupWord(group, word);
              group = createGroup("artists");
              createGroupWord(group, word);
              group = createGroup("googlers");
              createGroupWord(group, word);
              
              List<GroupWord> groupWords = GroupWord.findByWord(word.id);
              assertThat(groupWords.size() == 3);
              
              for (int i = 0; i < groupWords.size(); i ++)
            	  GroupWord.delete(groupWords.get(i).id);
              
              groupWords = GroupWord.findByWord(word.id);
              assertThat(groupWords.size() == 0);
              
              Group.delete(Group.findByName("photographers").id);
              Group.delete(Group.findByName("artists").id);
              Group.delete(Group.findByName("googlers").id);
              Word.delete(word.id);
          }
       });
   }
}
 
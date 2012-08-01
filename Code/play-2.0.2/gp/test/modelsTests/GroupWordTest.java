import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class GroupWordTest {
    
	private GroupWord createGroupWord(Group group, Word word) {
		GroupWord groupWord = new GroupWord(group, word, 0, 0);
		GroupWord.add(groupWord);
        return groupWord;
    }
	
	private Word createWord(String value) {
        Word word = new Word(value);
        Word.add(word);
        return word;
    }
	
	private Group createGroup(String value) {
    	Group group = new Group(value, "", "", "", 0, 0, 0, 0, 0);
    	Group.add(group);
        return group;
    }
    
    @Test
    public void CreateAndDeleteGroupWord() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
               String textWord = "Maximus";
        	   createWord(textWord);
        	   Word word = Word.findByWord(textWord);
               assertThat(word).isNotNull();
               
               String nameGroup = "lords";
               createGroup(nameGroup);
               Group group = Group.findByName(nameGroup);
               assertThat(group).isNotNull();

               GroupWord groupWord = createGroupWord(group, word);
               assertThat(groupWord.group).isEqualTo(group);
               
               GroupWord findGroupWord = GroupWord.findById(groupWord.id);
               assertThat(findGroupWord.word).isEqualTo(word);
               
               GroupWord.delete(groupWord.id);
               GroupWord groupWordDeleted = GroupWord.findById(groupWord.id);
               assertThat(groupWordDeleted).isNull();
               
               Word.delete(word.id);
               Group.delete(group.id);
           }
        });
    }

   @Test
   public void GetWordsForGroup() {
   	running(fakeApplication(), new Runnable() {
          public void run() {
              String nameGroup = "flowers";
        	  createGroup(nameGroup);
        	  Group group = Group.findByName(nameGroup);
              assertThat(group).isNotNull();

              Word word = createWord("rose");
              createGroupWord(group, word);
              word = createWord("tulip");
              createGroupWord(group, word);
              word = createWord("dandelion");
              createGroupWord(group, word);
              
              List<GroupWord> groupWords = GroupWord.findByGroup(group.id);
              assertThat(groupWords.size()).isEqualTo(3);
              
              for (int i = 0; i < groupWords.size(); i ++)
            	  GroupWord.delete(groupWords.get(i).id);
              
              groupWords = GroupWord.findByGroup(group.id);
              assertThat(groupWords.size()).isEqualTo(0);
              
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
              String textWord = "image";
        	  createWord(textWord);
              Word word = Word.findByWord(textWord);
              assertThat(word).isNotNull();

              Group group = createGroup("photographers");
              createGroupWord(group, word);
              group = createGroup("artists");
              createGroupWord(group, word);
              group = createGroup("googlers");
              createGroupWord(group, word);
              
              List<GroupWord> groupWords = GroupWord.findByWord(word.id);
              assertThat(groupWords.size()).isEqualTo(3);
              
              for (int i = 0; i < groupWords.size(); i ++)
            	  GroupWord.delete(groupWords.get(i).id);
              
              groupWords = GroupWord.findByWord(word.id);
              assertThat(groupWords.size()).isEqualTo(0);
              
              Group.delete(Group.findByName("photographers").id);
              Group.delete(Group.findByName("artists").id);
              Group.delete(Group.findByName("googlers").id);
              Word.delete(word.id);
          }
       });
   }
}
 
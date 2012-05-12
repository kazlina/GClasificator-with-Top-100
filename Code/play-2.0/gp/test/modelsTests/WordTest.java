import org.junit.*;

import java.util.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class WordTest {
    
	private Word createWord(String value) {
		Word word = new Word(value);
        Word.add(word);
        return word;
    }
    
    private Synonym createSynonym(Long wordId, String value) {
    	Synonym synonym = new Synonym(value);
    	Synonym.add(wordId, synonym);
        return synonym;
    }
    
    private GroupWord createGroupWord(Group group, Word word) {
    	GroupWord groupWord = new GroupWord(group, word, 0, 0);
		GroupWord.add(groupWord);
        return groupWord;
    }
	
	private Group createGroup(String value) {
		Group group = new Group(value, "", "", "", 0, 0, 0, 0, 0);
    	Group.add(group);
        return group;
    }

    @Test
    public void CreateAndDeleteWord() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
               String text = "Peter";
               createWord(text);
               Word findWord = Word.findByWord(text);
               assertThat(findWord.word == text);

               Word.delete(findWord.id);
               Word wordDeleted = Word.findByWord(text);
               assertThat(wordDeleted == null);
           }
        });
    }

    @Test
    public void FindNotExistedWord() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   Word word = Word.findByWord("Yuri");
               assertThat(word == null);
           }
        });
    }
 
    @Test
    public void WordToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String text = "Peter";
              createWord(text);
              Word word = Word.findByWord(text);
              assertThat(word != null);
              
              String textWord = word.toString();
              assertThat(textWord == text);
              
              Word.delete(word.id);
          }
       });
    }
   
    @Test
    public void GetAllWords() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  createWord("Julia");
        	  createWord("Nasty");
        	  createWord("Kate");
              
              List<Word> allWord = Word.all();
              assertThat(allWord.size() == 3);
              
              for (int i = 0; i < allWord.size(); i ++)
            	  Word.delete(allWord.get(i).id);
              
              allWord = Word.all();
              assertThat(allWord.size() == 0);
          }
       });
    }
   
    @Test
    public void FindByWord() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String text = "Maximus";
        	  createWord(text);
              
        	  Word word = Word.findByWord(text);
              assertThat(word != null);
              
              Word.delete(word.id);
              
              word = Word.findByWord(text);
              assertThat(word == null);
          }
       });
    }
 
    @Test
    public void GetSynonymsForWord() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String text = "Mari";
        	   createWord(text);
        	   Word word = Word.findByWord(text);
               assertThat(word.word != null);

               createSynonym(word.id, "Marinad");
               createSynonym(word.id, "Student");
               createSynonym(word.id, "Murder");
               
               assertThat(word.synonyms.size() == 3);
               
               for (int i = 0; i < word.synonyms.size(); i ++)
             	  Synonym.delete(word.synonyms.get(i).id);
               
               assertThat(word.synonyms.size() == 0);
               
               Word.delete(word.id);
           }
        });
    }
 	
	@Test
	   public void GetGroupsForWord() {
	   	running(fakeApplication(), new Runnable() {
	          public void run() {
	        	  String text = "Mari";
	        	  createWord(text);
	              Word word = Word.findByWord(text);
	              assertThat(word != null);

	              //assertThat(word.groupWords.size() == 0);
	              
	              Group group = createGroup("Women");
	              createGroupWord(group, word);
	              group = createGroup("Students");
	              createGroupWord(group, word);
	              group = createGroup("Murders");
	              createGroupWord(group, word);
	              
	              assertThat(word.groupWords.size() == 3);
	              
	              for (int i = 0; i < word.groupWords.size(); i ++)
	            	  GroupWord.delete(word.groupWords.get(i).id);
	              
	              assertThat(word.groupWords.size() == 0);
	              
	              Group.delete(Group.findByName("Women").id);
	              Group.delete(Group.findByName("Students").id);
	              Group.delete(Group.findByName("Murders").id);
	              Word.delete(word.id);
	          }
	       });
	   }
	    
    @Test
    public void GetGroupsForSomeWord() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   createWord("Mari");
        	   createWord("Maximus");
               
        	   Word word = Word.findByWord("Mari");
               assertThat(word != null);
               //assertThat(word.groupWords.size() == 0);
               
               Group group = createGroup("Women");
               createGroupWord(group, word);
               group = createGroup("Murders");
               createGroupWord(group, word);
               group = createGroup("Students");
               createGroupWord(group, word);
               
               assertThat(word.groupWords.size() == 3);
               assertThat(word.groupWords.get(0).group.name == "Students"
            		   || word.groupWords.get(1).group.name == "Students"
            		   || word.groupWords.get(2).group.name == "Students");
               
               word = Word.findByWord("Maximus");
               assertThat(word != null);
               //assertThat(word.groupWords.size() == 0);
               
               createGroupWord(group, word);
               group = createGroup("Lords");
               createGroupWord(group, word);
               
               assertThat(word.groupWords.size() == 2);
               assertThat(word.groupWords.get(0).group.name == "Students"
            		   || word.groupWords.get(1).group.name == "Students");
               
               for (int i = 0; i < word.groupWords.size(); i ++)
            	   GroupWord.delete(word.groupWords.get(i).id);
               
               assertThat(word.groupWords.size() == 0);
               
               Word.delete(word.id);
               
               word = Word.findByWord("Mari");
               for (int i = 0; i < word.groupWords.size(); i ++)
            	   GroupWord.delete(word.groupWords.get(i).id);
               
               assertThat(word.groupWords.size() == 0);
               Word.delete(word.id);
               Group.delete(Group.findByName("Women").id);
               Group.delete(Group.findByName("Students").id);
               Group.delete(Group.findByName("Murders").id);
               Group.delete(Group.findByName("Lords").id);
           }
        });
    }
}
 
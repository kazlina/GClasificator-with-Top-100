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
               assertThat(findWord).isNotNull();

               Word.delete(findWord.id);
               Word wordDeleted = Word.findByWord(text);
               assertThat(wordDeleted).isNull();
           }
        });
    }

    @Test
    public void FindNotExistedWord() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   Word word = Word.findByWord("Yuri");
               assertThat(word).isNull();
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
              assertThat(word).isNotNull();
              
              String textWord = word.toString();
              assertThat(textWord).isEqualTo(text);
              
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
              assertThat(allWord.size()).isEqualTo(3);
              
              for (int i = 0; i < allWord.size(); i ++)
            	  Word.delete(allWord.get(i).id);
              
              allWord = Word.all();
              assertThat(allWord.size()).isEqualTo(0);
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
              assertThat(word).isNotNull();
              
              Word.delete(word.id);
              
              word = Word.findByWord(text);
              assertThat(word).isNull();
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
               assertThat(word.word).isNotNull();

               createSynonym(word.id, "Marinad");
               createSynonym(word.id, "Student");
               createSynonym(word.id, "Murder");
               
               assertThat(word.synonyms.size()).isEqualTo(3);
               
               for (int i = 0; i < word.synonyms.size(); i ++)
             	  Synonym.delete(word.synonyms.get(i).id);
               
               word = Word.findByWord(text);
               assertThat(word.synonyms.size()).isEqualTo(0);
               
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
	              assertThat(word).isNotNull();
	              assertThat(word.groupWords.size() == 0);
	              
	              Group group = createGroup("Women");
	              createGroupWord(group, word);
	              group = createGroup("Students");
	              createGroupWord(group, word);
	              group = createGroup("Murders");
	              createGroupWord(group, word);
	              
	              word = Word.findByWord(text);
	              assertThat(word.groupWords.size()).isEqualTo(3);
	              
	              for (int i = 0; i < word.groupWords.size(); i ++)
	            	  GroupWord.delete(word.groupWords.get(i).id);
	              
	              word = Word.findByWord(text);
	              assertThat(word.groupWords.size()).isEqualTo(0);
	              
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
               assertThat(word).isNotNull();
               assertThat(word.groupWords.size() == 0);
               
               Group group = createGroup("Women");
               createGroupWord(group, word);
               group = createGroup("Murders");
               createGroupWord(group, word);
               group = createGroup("Students");
               createGroupWord(group, word);
               
        	   word = Word.findByWord("Mari");
               assertThat(word.groupWords.size()).isEqualTo(3);
               assertThat(word.groupWords.get(0).group.toString().contentEquals("Students")
            		   || word.groupWords.get(1).group.toString().contentEquals("Students")
            		   || word.groupWords.get(2).group.toString().contentEquals("Students")).isTrue();
               
               word = Word.findByWord("Maximus");
               assertThat(word).isNotNull();
               assertThat(word.groupWords.size()).isEqualTo(0);
               
               createGroupWord(group, word);
               group = createGroup("Lords");
               createGroupWord(group, word);
               
               word = Word.findByWord("Maximus");
               assertThat(word.groupWords.size()).isEqualTo(2);
               assertThat(word.groupWords.get(0).group.toString().contentEquals("Students")
            		   || word.groupWords.get(1).group.toString().contentEquals("Students")).isTrue();
               
               for (int i = 0; i < word.groupWords.size(); i ++)
            	   GroupWord.delete(word.groupWords.get(i).id);
               
               word = Word.findByWord("Maximus");
               assertThat(word.groupWords.size()).isEqualTo(0);
               
               Word.delete(word.id);
               
               word = Word.findByWord("Mari");
               for (int i = 0; i < word.groupWords.size(); i ++)
            	   GroupWord.delete(word.groupWords.get(i).id);
               
               word = Word.findByWord("Mari");
               assertThat(word.groupWords.size()).isEqualTo(0);
               
               Word.delete(word.id);
               
               Group.delete(Group.findByName("Women").id);
               Group.delete(Group.findByName("Students").id);
               Group.delete(Group.findByName("Murders").id);
               Group.delete(Group.findByName("Lords").id);
           }
        });
    }
}
 
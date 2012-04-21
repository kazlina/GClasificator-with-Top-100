package models;

import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;

public class DataExtraction {

    public int newGPM(String id) {
        updateActivity(id,100);
        // i should add a validator!
        updateProfile(id);
        return 0;
    }

    public int updateProfile(String id){
        TempProfile profile = new TempProfile();
        Parser temp = new Parser();

        //get 'profile' from GooglePlus
        profile = temp.getProgile(id); // i should add a validator!

        //add profile to DB

        Profile man = Profile(id,profile.displayName,profile.image,profile.gender,AIM???profile.tagline,profile.relationshipStatus,FOLLOWERS???).save();
        // i should add a validator!


        //words extraction
        ArrayList<HistogramForWords> profileHistogram = new ArrayList<HistogramForWords>();
        String text = null;
        text = profile.aboutMe + " " + profile.tagline;
        profileHistogram = wordPreprocessor(text);
        //for each word from profile
        for (HistogramForWords pH: profileHistogram) {
                //for pH.word search it (word) in WordDictionary

                List <WordDictionary> word1 = WordDictionary.find("byWord",pH.word).fetch();
                List <WordSynonyms> word2 = WordSynonyms.find("byWord",pH.word).fetch();

                //there is the word (pH.word) in dictionary (WordDictionary or WordSynonyms)
                if (!(word1.isEmpty()) || !(word2.isEmpty())) {
                    //add word from profile to table 'ProfileWord'
                    new ProfileWord(man, Word_dictionary Id_Word????, pH.countWord).save();
                    // i should add a validator!
                }

            }

        //links extraction (there is no, Artem has not written this feature yet)

        return 0;
    }

    public int updateActivity(String id, int countOfPosts) {

        List <Post> activity = new ArrayList <Post>();
        GAPI temp = new GAPI();

        //get 'activity' from GooglePlus
        activity = temp.getActivity(id,countOfPosts); // i should add a validator

        //for each 'post' from 'activity'
        for (Post post: activity) {
            //add posts to DB (class Posts)

            Posts postToDB = new Posts (id,"i do not know",post.total_replies,post.total_plusoners,post.total_resharers,post.kind_post).save();
            // WHERE is there 'kind_content' in this table?
            // i should add a validator!


            //words extraction
            //build word histogram
            ArrayList<HistogramForWords> postHistogram = new ArrayList<HistogramForWords>();
            postHistogram = wordPreprocessor(post.content);
            //for each word from post
            for (HistogramForWords pH: postHistogram) {
                //for pH.word search it (word) in WordDictionary

                List <WordDictionary> word1 = WordDictionary.find("byWord",pH.word).fetch();
                List <WordSynonyms> word2 = WordSynonyms.find("byWord",pH.word).fetch();

                //there is the word (pH.word) in dictionary (WordDictionary or WordSynonyms)
                if (!(word1.isEmpty()) || !(word2.isEmpty())) {
                    //add word from post to table 'PostWord'
                    new PostWord(postToDB, Word_dictionary Id_Word, pH.countWord).save();
                    // i should add a validator!
                }

            }

            //links extraction (there is no, Artem has not written this feature yet)

            //ids extraction
            if (!(post.kindPost)) {

                List <NewId> newId = NewId.find("byId").fetch();
                if (newId.isEmpty()) {
                    new NewId(post.actorId,1).save();
                    // i should add a validator!
                }
                else {
                    //add +1 to nMentiens for actorId ??? How?
                }

            }
        }
        return 0;
    }

    ArrayList<HistogramForWords> wordPreprocessor(String a) {
        int i = 0, check = 0;
        String [] temp = null;
        ArrayList<String> mas = new ArrayList<String>();
        ArrayList<HistogramForWords> hist = new ArrayList<HistogramForWords>();
        temp = getWordsFromSentence(a);
        if(temp != null) {
            for(i = 0; i < temp.length; i++) {
                mas.add(temp[i]);
            }
        }
        HistogramForWords elem = new HistogramForWords();
        elem.countWord++;
        elem.word = mas.get(0);
        hist.add(elem);
        mas.remove(0);
        for (String m: mas){
            for (HistogramForWords h: hist){
                if (h.word == null ? m == null : h.word.equals(m)) {
                    h.countWord++;
                    check = 1;
                }
            }
            if (check == 0) {
                HistogramForWords elem1 = new HistogramForWords();
                elem1.countWord++;
                elem1.word = m;
                hist.add(elem1);
            }
            check = 0;
        }
        return hist;
    }

    String[] getWordsFromSentence(String a) {
        //conversion string to lowercase
        a = a.toLowerCase();
        String[] temp = null;
        if (a != null && !"".equals(a))
        {
            temp = a.split("[ ,.!?;:#()]+");
        }
        return temp;
    }
}

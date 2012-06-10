package models;

//import HistogramForLinks;
//import Link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.net.URL;

public class DataExtraction {

    public static int newGPM(String id) {
	GPM gpm = GPM.add(id);
	if(gpm != null){
	//updateActivity(gpm,100);
        // i should add a validator!
    updateProfile(gpm);
    }
    return 0;
    }

    public static int updateProfile(GPM gpm){
        TempProfile profile = new TempProfile();

        //get 'profile' from GooglePlus
        try {
			profile = GAPI.getProfile(gpm.idGpm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // i should add a validator!

        //add profile to DB
        Gender gender =  Gender.findByValue(profile.gender);
        Relationship relationshipStatus = Relationship.findByStatus(profile.relationshipStatus);
        Profile man = new Profile(gpm,profile.displayName,profile.image, gender,
					profile.tagline, relationshipStatus, profile.nfollowers);
        Profile.add(man);
        // i should add a validator!

        //words extraction
        ArrayList<HistogramForWords> profileHistogram = new ArrayList<HistogramForWords>();
        String text = null;
        text = profile.aboutMe + " " + profile.tagline;
        profileHistogram = wordPreprocessor(text);
        if (profileHistogram != null) {
            //for each word from profile
            for (HistogramForWords pH: profileHistogram) {
                //for pH.word search it (word) in WordDictionary
                //add word from profile to table 'ProfileWord'
                ProfileWord.add(man, pH.word, pH.countWord);
                // i should add a validator!
            }
        }

        //links extraction (there is no, Artem has not written this feature yet)
        String linksFromProfile = "";
        //collect of links from profile "urls"
        for (String str: profile.urls) {
        	linksFromProfile += str + " ";
        }
        //combine of all links from profile
        text = text + " " + linksFromProfile;
        //begin of adding histogram info about profile links into table "ProfileLink"
        ArrayList<HistogramForLinks> profileLinksHistogram = linkPreprocessor (text, 3);
        if (profileLinksHistogram != null) {
        	for (HistogramForLinks linksHistogram: profileLinksHistogram) {
                //adding link from profile to table 'ProfileLink'
    			ProfileLink.add(man, linksHistogram.link.toString(), linksHistogram.count);
        	}
        }
        return 0;
    }

    public static int updateActivity(GPM gpm, int countOfPosts) {

        List <TempPost> activity = new ArrayList <TempPost>();
        GAPI temp = new GAPI();

        //get 'activity' from GooglePlus
        try {
			activity = temp.getActivity(gpm.idGpm,countOfPosts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // i should add a validator

        //for each 'post' from 'activity'
        for (TempPost post: activity) {
            //add posts to DB (class Post)
        	post.print();
        	Content kindContent =  Content.findByKind(post.kindContent);
            Post postToDB = new Post (gpm, post.postId, post.publishedData, kindContent, post.nComments,
				post.nPlusOne, post.nResharers,post.isRepost);
            Post.add(postToDB);
            // i should add a validator!

            //words extraction
            //build word histogram
            ArrayList<HistogramForWords> postHistogram = new ArrayList<HistogramForWords>();
            String text = null;
            text = post.content;
            if (post.isRepost && post.annotation != null) {
                text = text + " " + post.annotation;
            }
            postHistogram = wordPreprocessor(text);
            if (postHistogram != null){
                //for each word from post
                for (HistogramForWords pH: postHistogram) {
                    //for pH.word search it (word) in WordDictionary
                    //add word from post to table 'PostWord'
                    PostWord.add(postToDB, pH.word, pH.countWord);
                    // i should add a validator!
                }
            }

            
            //links extraction
            //combine of all links from post
            text = text + " " + post.url;
            //build links histogram
            ArrayList<HistogramForLinks> profileLinksHistogram = linkPreprocessor (text, 3);
            if (profileLinksHistogram != null) {
            	for (HistogramForLinks linksHistogram: profileLinksHistogram) {
                    //adding link from profile to table 'ProfileLink'
        			PostLink.add(postToDB, linksHistogram.link.toString(), linksHistogram.count);
            	}
            }

            //ids extraction
            if ((post.isRepost)) {
                NewGPM newGPM = null;
                newGPM.add(post.actorId);
            }
        }
        return 0;
    }
    static ArrayList<HistogramForWords> wordPreprocessor(String a) {
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

        if (!(mas.isEmpty())) {
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
        } else {
            hist = null;
        }
        return hist;
    }

    static String[] getWordsFromSentence(String a) {
        //conversion string to lowercase
        a = a.toLowerCase();
        String[] temp = null;
        if (a != null && !"".equals(a))
        {
            temp = a.split("[ ,.!?;:#()]+");
        }
        return temp;
    }
    public static ArrayList<HistogramForLinks> linkPreprocessor (String inputString, int maxLength) {
    	String [] slicedStringMass = null;
		
		//Dividing input string into words
		slicedStringMass = separateTokens(inputString);

		String urlRegexp = "(http|https|HTTP|HTTPS)://.*";
		Pattern urlPattern = Pattern.compile(urlRegexp);
		
		//adding links into list
		List <URL> sourcelinksList = new ArrayList<URL>();
		//add string from sliced mass into list only if they match html-tamplate
		
		if (slicedStringMass != null) {
			for(int i = 0; i < slicedStringMass.length; i++) {
				if (urlPattern.matcher(slicedStringMass[i]).matches()) {
					try {
						URL addingLink = new URL(slicedStringMass [i]);
						sourcelinksList.add(addingLink);
					}
					catch (Exception linkError) {
						//it's mean, that regular expression in pattern is invalid
					}
				}
			}
		}
		
		if (sourcelinksList.size() == 0) {
			ArrayList<HistogramForLinks> nullValue = null;
			return nullValue;
		}

		//LinkComparator comparator = new LinkComparator("");
		//java.util.Collections.sort(sourcelinksList, comparator);
		
		List <Link> dictionaryLinks = Link.all();
		//create and initialized first element histogram mass
		ArrayList <HistogramForLinks> histogramList = new ArrayList<HistogramForLinks>();
		//try {
		for (URL currentLink: sourcelinksList) {
		    for (Link linkFromDictionary: dictionaryLinks) {
		    	try {
		    		if (dimainCollation(
		    	
		        		linkFromDictionary.link.substring(
		        				getSecondSlashPosition(linkFromDictionary.link) + 1, getThirdSlashPosition(linkFromDictionary.link)
	        					)
	        			,currentLink.getHost())	&& 
	        			linksDistance(currentLink, linkFromDictionary.link) <= maxLength) {
			    		//search in histogram such link
			    		int indexOfLink;
			    		boolean linkInHistogram = false;
			    		for (indexOfLink = 0; 
			    				indexOfLink < histogramList.size() && !linkInHistogram; 
			    				indexOfLink++) {
			    			linkInHistogram = histogramList.get(indexOfLink).link.toString().equals(linkFromDictionary.link);
			    		}
	        			if (linkInHistogram) {
	        				histogramList.get(indexOfLink - 1).count++;
	        			}
		        		else {
		        			histogramList.add(new HistogramForLinks(linkFromDictionary.link));
		        			
		        		}
	        			break;
		    		}
		    	}
		    	catch (Exception ex) {
					//there is cannot be exception, because it should be early
				}
		    }
		}
		/*}
		catch (Exception ex) {
			//there is cannot be exception, because it should be early
		}*/
		return histogramList;
	}
    
	private static String [] separateTokens (String inputString) {
		String [] outputString = null;
		if (inputString != null && !inputString.equals("")){
			outputString = inputString.split("\\s|\\s=\\s|>|=\"|\\s=\"|=\\s\"|\\s=");
		}
		return outputString;
	}

	private  static int linksDistance (URL link1, String link2) {
		String bigLinkPath, smallLinkPath;
		/*if (!link1.getHost().toString().equals(link2.getHost().toString())) {
			return -1;
		}*/
		
		if (link1.toString().length() >= link2.length()) {
			bigLinkPath = link1.getPath();
			smallLinkPath = link2.substring(getThirdSlashPosition(link2), link2.length());
		}
		else {
			bigLinkPath = link2.substring(getThirdSlashPosition(link2), link2.length());
			smallLinkPath = link1.getPath();
		}
		//search of domain end in first and second link
		
		if (bigLinkPath.length() == 0) {
			bigLinkPath = "/";
		}
		if (smallLinkPath.length() == 0) {
			smallLinkPath = "/";
		}
		
		int distance = 0, linksDifferencesPosition = 0;
		//search position in which strings are different
		for (; bigLinkPath.charAt(linksDifferencesPosition) == smallLinkPath.charAt(linksDifferencesPosition) &&
				linksDifferencesPosition < (smallLinkPath.length() - 1); linksDifferencesPosition++);
		
		//search of destination between strings
		for (int curPosition = linksDifferencesPosition; curPosition < bigLinkPath.length(); curPosition++) {
			if (bigLinkPath.charAt(curPosition) == '/' && ((bigLinkPath.length() - curPosition) != 1)){
				distance++;
			}
		}
		return distance;
	}
	//collate string and find occurrence "?" only in domain
	private  static boolean dimainCollation (String dictionaryLinkDomain, String newLinkDomain) {
		int indexOfQuestionMark = dictionaryLinkDomain.indexOf('?');
		if (dotsNumber(dictionaryLinkDomain) != dotsNumber(newLinkDomain)) {
			return false;
		}
		//if (dictionaryLinkDomain.charAt(dictionaryLinkDomain.length()) != '?')
		if (indexOfQuestionMark == -1) {
			return dictionaryLinkDomain.equals(newLinkDomain);
		}
		if (indexOfQuestionMark < newLinkDomain.length()) {
			String leftPart = null, rightPart = null;
			if (indexOfQuestionMark != 0 && 
					indexOfQuestionMark != (dictionaryLinkDomain.length() - 1)) { // ru.?.com
				leftPart = dictionaryLinkDomain.substring(0, indexOfQuestionMark);
				rightPart = dictionaryLinkDomain.substring(indexOfQuestionMark + 1, dictionaryLinkDomain.length());
				if (newLinkDomain.startsWith (leftPart) && 
						newLinkDomain.endsWith (rightPart)) {
					return true;
				}
			}
			else { // ?.google.com
				if (indexOfQuestionMark == 0 &&
						newLinkDomain.endsWith (dictionaryLinkDomain.substring (2, dictionaryLinkDomain.length() ) )) {
					return true;
				}
				 // ru.google.?
				if (indexOfQuestionMark == (dictionaryLinkDomain.length() - 1) &&
						newLinkDomain.startsWith (dictionaryLinkDomain.substring (0, indexOfQuestionMark) )) {
					return true;
				}
			}
				
			if (//checking before Question Mark
					dictionaryLinkDomain.substring(0, indexOfQuestionMark).equals(newLinkDomain.substring(0, indexOfQuestionMark)) &&
					dictionaryLinkDomain.substring(indexOfQuestionMark, dictionaryLinkDomain.length()).
						equals(newLinkDomain.substring(indexOfQuestionMark, newLinkDomain.length()))) {
				return true;
			}
		}
		return false;
	}
	public static int getSecondSlashPosition (String link) {
		int currentPosition, count = 0; 
		for (currentPosition = 0; count != 2; currentPosition++) {
			if (link.charAt(currentPosition) == '/') {
				count++;
			}
		}
		return currentPosition - 1;
	}
	public static int getThirdSlashPosition (String link) {
		int currentPosition, count = 0; 
		for (currentPosition = 0; count != 3 && currentPosition != link.length(); currentPosition++) {
			if (link.charAt(currentPosition) == '/') {
				count++;
			}
		}
		return currentPosition - 1;
	}
	public static int dotsNumber (String link) {
		int numberOfDots = 0;
		for (int currentPosition = 0; currentPosition < link.length(); currentPosition++) {
			if (link.charAt(currentPosition) == '.') {
				numberOfDots++;
			}
		}
		return numberOfDots;
	}
}

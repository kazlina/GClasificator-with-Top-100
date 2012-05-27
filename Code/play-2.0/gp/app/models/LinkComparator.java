package models;

import java.net.URL;

public class LinkComparator implements java.util.Comparator<URL> {

    private int referenceLength;

    public LinkComparator (String reference) {
        super();
        this.referenceLength = reference.length();
    }

    public int compare(URL s1, URL s2) {
        int dist1 = Math.abs(s1.toString().length() - referenceLength);
        int dist2 = Math.abs(s2.toString().length() - referenceLength);

        return dist1 - dist2;
    }
}
package models;

import java.net.URL;

public class HistogramForLinks {
	public URL link;
	public int count = 0;
	public HistogramForLinks (String inputLink) throws Exception {
		link = new URL(inputLink);
	}
}

package org.shako.textsummary.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Token {
	
	private String data;
	private int frequency;
	private Map<Integer, Integer> appearInSentence;
	private int contribution;
	
	public Token(String data) {
		this.data = data;
		this.frequency = 0;
		this.appearInSentence = new HashMap<Integer, Integer>();
	}

	public String getData() {
		return data;
	}
	
	public void addMore(Integer sentence) {
		Integer num = 0;
		if(isInSentence(sentence)) {
			num = this.appearInSentence.get(sentence) + 1;
		}
		this.appearInSentence.put(sentence, num);
		this.frequency += 1;
	}
	
	public int getFrequency() {
		return frequency;
	}

	public boolean isInSentence(Integer sentence) {
		return this.appearInSentence.containsKey(sentence);
	}
	
	public Integer getNumInSentence(Integer sentence) {
		if(isInSentence(sentence)) {
			return this.appearInSentence.get(sentence);
		} else {
			return 0;
		}
	}
	
	public Set<Integer> getSentences() {
		return this.appearInSentence.keySet();
	}
	
	@Override
 	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		
		if(!(o instanceof Token))
		{
			return false;
		}
		else
		{
			Token other = (Token)o;
			return this.data.equals(other.data);
		}
	}
	
	@Override
	public int hashCode()
	{
		return data.hashCode();
	}
	
	@Override
	public String toString()
	{
		return data;		
	}
}

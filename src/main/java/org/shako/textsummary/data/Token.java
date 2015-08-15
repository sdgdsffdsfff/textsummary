package org.shako.textsummary.data;

import java.util.HashMap;

public class Token {

	private String data;
	private double totalNumber;
	private HashMap<Integer, Integer> appearInSentence;

	public Token(String data) {
		this.data = data;
		this.totalNumber = 0;
		this.appearInSentence = new HashMap<Integer, Integer>();
	}

	public String getValue() {
		return data;
	}

	public void addToSentence(Integer sentence) {
		this.appearInSentence.put(sentence, isInSentence(sentence) + 1);
		this.totalNumber += 1;
	}

	public double getTotalNumber() {
		return totalNumber;
	}

	public Integer isInSentence(Integer sentence) {
		return this.appearInSentence.get(sentence);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof Token)) {
			return false;
		} else {
			Token other = (Token) o;
			return this.data.equals(other.data);
		}
	}

	@Override
	public int hashCode() {
		return data.hashCode();
	}

	@Override
	public String toString() {
		return data;
	}

	public Integer[] getSentences() {
		return appearInSentence.keySet().toArray(new Integer[0]);
	}
}

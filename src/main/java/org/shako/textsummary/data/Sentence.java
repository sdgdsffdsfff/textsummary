package org.shako.textsummary.data;

import java.util.ArrayList;
import java.util.List;

class Sentence {
	
	/**
	 * sentence structure
	 */
	private int category;
	private int position;
	private int length;
	/**
	 * sentence occur in which section
	 */
	private int section;
	/**
	 * id in article
	 */
	private int identity;
	
	/**
	 * 
	 * @param identity
	 */
	public Sentence(int identity) {
		init(identity);
	}
	
	private void init(int identity) {
		this.length = 0;
		this.identity = identity;
		this.tokens = new ArrayList<String>();
	}

	private List<String> tokens;
	/**
	 * 
	 * @param identity
	 * @param category
	 * @param section
	 * @param position
	 */
	public Sentence(int identity, int category, int section, int position) {
		this.identity = identity;
		this.category = category;
		this.length = 0;
		this.position = position;
		this.section = section;
	}
	
	public int addToken(String data) {
		if(!isContainToken(data)) {
			this.tokens.add(data);
		}
		length = length + 1;
		return length;
	}
	
	public boolean isContainToken(String data) {
		return this.tokens.contains(data);
	}
	
}

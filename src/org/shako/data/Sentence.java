package org.shako.data;

import java.util.List;

class Sentence {
	
	private int category;
	private int position;
	private int length;
	private List<Token> tokens;
	/**
	 * 暂时无法处理
	 */
	private List<Token> specialTokens;
	
	private int score;	
	private int scoreStructure;
	private int scoreTopic;
	
	private int section;
}

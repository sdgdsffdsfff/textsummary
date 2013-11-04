package org.shako.textsummary.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Article {
	
	private List<Sentence> sentences;
	private int sentenceID;
	private HashMap<String, Token> tokens;
	
	public Article() {
		sentenceID = 0;
		this.sentences = new ArrayList<Sentence>();
		this.tokens = new HashMap<String, Token>();
	}
	
	/**
	 * 读取一个已经预处理过的句子
	 * @param data
	 * @return
	 */
	public int readNewSentence(List<String> data) {
		sentenceID = sentenceID + 1;
		Sentence sen = new Sentence(sentenceID);
		for(String sdata : data) {
			if(tokens.containsKey(sdata)) {
				tokens.get(sdata).addMore(sentenceID);			
			} else {
				Token token = new Token(sdata);
				token.addMore(sentenceID);
				tokens.put(sdata, token);
			}
			sen.addToken(sdata);
		}
		sentences.add(sen);
		return sentenceID;
	}
	
	public List<Token> getTokenByFrequency(double n) {
		int _TTokens = (int) (n * getTokenCounts());
		ArrayList<Token> output = new ArrayList<Token>(_TTokens);
		//目的是将tokens按照其频率分成2堆，其中大堆的数量超过阀值即可。
		String[] _sortByFreq = sortByFreq(this.tokens.values(), _TTokens);
		for(String key : _sortByFreq) {
			Token t = tokens.get(key);
			output.add(t);
		}
		return output;
	}
	
	private String[] sortByFreq(Collection<Token> values, int n) {
		int[] freq = new int[values.size()];
		String[] data = new String[values.size()];
		int i = -1;
		Iterator<Token> iter = values.iterator();
		while(iter.hasNext()) {
			i = i + 1;
			Token t = iter.next();
			freq[i] = t.getFrequency();
			data[i] = t.getData();
		}
		//quick sort 
		int left = 0, right = i, partial = i - n + 1, initial = partial - 1;
				
		while(left < right) {	
			int j;
			int p ;
			String q;
			int tempFreq = freq[initial];
			String tempData = data[initial];
			for(j = right; j >= left; j--) {
				if(freq[j] < tempFreq) {		
					p = freq[j];
					q = data[j];
					freq[j] = tempFreq;
					data[j] = tempData;
					tempFreq = p;
					tempData = q;
					freq[initial] = p;
					data[initial] = q;
					right = j - 1;
					break;
				} 
			}
			if(j < left) {
				freq[initial] = freq[left];
				data[initial] = data[left];
				freq[left] = tempFreq;
				data[left] = tempData;
				left++;
				if(left == partial) {
					right = left;
				}
				continue;
			}
			for(j = left; j <= right; j++) {
				if(freq[j] > tempFreq) {
					p = freq[j];
					q = data[j];
					freq[j] = tempFreq;
					data[j] = tempData;
					tempFreq = p;
					tempData = q; 
					freq[initial] = p;
					data[initial] = q;
					left = j + 1;
					break;
				}
			}
			if(j > right) {
				freq[initial] = freq[right];
				data[initial] = data[right];
				freq[right] = tempFreq;
				data[right] = tempData;
				right--;
				if(right == partial) {
					left = right;
				}
				continue;
			}
			if(left == right) {
				if(left < partial) {
					right = i;
				} else if(left > partial) {
					left = 0;
				}
			}
		}
		
		String[] _output = new String[n]; 
		for(int j = i, k = 0; j >= partial && k < n; j--, k++){
			_output[k] = data[j];
		}
		return _output;
	}

	public List<Token> getTokenByFrequency() {
		double def = 0.1;
		return getTokenByFrequency(def);
	}
	
	public int getTokenCounts() {
		return tokens.size();
	}

	public HashMap<String, Token> getTokens() {
		return tokens;
	}

}

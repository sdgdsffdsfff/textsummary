package org.shako.textsummary.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Article {

	// private Map<Integer, Sentence> sentences;
	private Integer sentenceID;
	private HashMap<String, Token> tokens;

	public Article() {
		sentenceID = 0;
		// this.sentences = new HashMap<Integer, Sentence>();
		this.tokens = new HashMap<String, Token>();
	}

	/**
	 * 读取一个已经预处理过的句子
	 * 
	 * @param data
	 * @return
	 */
	public int readNewSentence(List<String> data) {
		sentenceID = sentenceID + 1;
		// Sentence sen = new Sentence(sentenceID);
		for (String sdata : data) {
			if (!tokens.containsKey(sdata)) {
				Token token = new Token(sdata);
				tokens.put(sdata, token);
			}
			Token t = tokens.get(sdata);
			t.addToSentence(sentenceID);
			// sen.addToken(t);
		}
		// sentences.put(sentenceID, sen);
		return sentenceID;
	}

	/**
	 * 按百分比选取出现频率最高的词
	 * 
	 * @param ratio
	 * @return
	 */
	public List<Token> getTokenByFrequency(double ratio) {
		int _TTokens = (int) (ratio * sumTokens());
		List<Token> output = new ArrayList<Token>(_TTokens);
		// 目的是将tokens按照其频率分成2堆，其中大堆的数量超过阀值即可。
		String[] _sortByFreq = sortByFreq(this.tokens.values(), _TTokens);
		for (String key : _sortByFreq) {
			Token t = tokens.get(key);
			output.add(t);
		}
		return output;
	}

	private String[] sortByFreq(Collection<Token> values, int partial) {
		FreqToken[] freqTokens = new FreqToken[values.size()];
		int size = -1;
		Iterator<Token> iter = values.iterator();
		while (iter.hasNext()) {
			size = size + 1;
			Token t = iter.next();
			freqTokens[size] = new FreqToken(t.getValue(),
					(int) t.getTotalNumber());
		}
		// quick sort
		int left = 0, right = size;

		while (left < right) {
			int j;
			FreqToken temp = freqTokens[partial];
			// 从左向右，检查
			for (j = left; j <= right; j++) {
				if (freqTokens[j].compare(temp) < 0) {
					freqTokens[j].exchange(temp);
					left = j + 1;
					break;
				}
			}
			if (j > right) {
				freqTokens[right].exchange(temp);
				right--;
				if (right == partial) {
					left = right;
				}
				continue;
			}
			// 从右向左检查
			for (j = right; j >= left; j--) {
				if (freqTokens[j].compare(temp) > 0) {
					freqTokens[j].exchange(temp);
					right = j - 1;
					break;
				}
			}
			if (j < left) {
				freqTokens[left].exchange(temp);
				left++;
				if (left == partial) {
					right = left;
				}
				continue;
			}
			// 在中间相遇，检查
			if (left == right) {
				if (left < partial) {
					right = size;
				} else if (left > partial) {
					left = 0;
				}
			}
		}

		String[] _output = new String[partial];
		for (int k = 0; k < partial; k++) {
			_output[k] = freqTokens[k].getData();
		}
		return _output;
	}

	public List<Token> getTokenByFrequency() {
		double def = 0.1;
		return getTokenByFrequency(def);
	}

	public int sumTokens() {
		return tokens.size();
	}

	public Collection<Token> getAllTokens() {
		return tokens.values();
	}

	private class FreqToken {

		private int freq;
		private String data;

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public int getFreq() {
			return freq;
		}

		public void setFreq(int freq) {
			this.freq = freq;
		}

		public FreqToken(String data, int freq) {
			this.freq = freq;
			this.data = data;
		}

		public void exchange(FreqToken other) {
			String data = other.getData();
			int freq = other.getFreq();
			other.setData(this.getData());
			other.setFreq(this.getFreq());
			this.setData(data);
			this.setFreq(freq);
		}

		/**
		 * > 0 : more than < 0 : less than = 0 : equal
		 * 
		 * @param other
		 * @return
		 */
		public int compare(FreqToken other) {
			return this.getFreq() - other.getFreq();
		}

	}
}

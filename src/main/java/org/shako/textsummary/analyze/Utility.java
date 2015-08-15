package org.shako.textsummary.analyze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.shako.textsummary.data.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class Utility {

	private static Logger log = LoggerFactory.getLogger(Utility.class);

	public static Cluster[] cluster(Double[] relates, WrapToken[] freqTokens) {
		log.debug("cluster begin at " + new Date().getTime());
		List<Cluster> clus = new ArrayList<Cluster>();
		List<WrapToken> preList = Arrays.asList(freqTokens);
		double level = relates[freqTokens.length - 1];
		do {
			Cluster c = new Cluster();
			WrapToken t = preList.get(0);
			List<WrapToken> result = c.process(t, level);
			preList.removeAll(result);
			if (c.size() > 1)
				clus.add(c);
		} while (!preList.isEmpty());
		log.debug("cluster end at " + new Date().getTime());
		return clus.toArray(new Cluster[0]);
	}

	// 冒泡排序找出最高的n个值，最小的即为阀值，返回出去
	public static void popup(Double[] doubles, int n) {
		for (int j = 0; j < n; j++) {
			int i = doubles.length - 1;
			int k;
			for (; i > 0; i--) {
				k = i - 1;
				if (doubles[i] > doubles[k]) {
					double temp = doubles[i];
					doubles[i] = doubles[k];
					doubles[k] = temp;
				}
			}
		}
	}

	public static double calcRelate(WrapToken first, WrapToken second) {
		Token fdata = first.getData();
		Token sdata = second.getData();
		Integer[] sens = fdata.getSentences();
		double frel = 0;
		double srel = 0;
		for (Integer index : sens) {
			if (sdata.isInSentence(index) > 0) {
				frel += fdata.isInSentence(index);
				srel += sdata.isInSentence(index);
			}
		}
		double relate = (frel / fdata.getTotalNumber())
				+ (srel / sdata.getTotalNumber());
		first.addRelation(second, relate);
		second.addRelation(first, relate);
		return relate;
	}

	public static WrapToken[] wrapTokens(Collection<Token> tokens) {
		log.debug("wrap the tokens from the article in the operation.");
		int size = tokens.size();
		WrapToken[] wraped = new WrapToken[size];
		Iterator<Token> iter = tokens.iterator();
		int i = 0;
		while (iter.hasNext()) {
			wraped[i] = new WrapToken(iter.next());
			i++;
		}
		log.debug("wrapping the tokens is done");
		return wraped;
	}

	public static WrapToken[] minusWrapTokens(Collection<WrapToken> parent,
			Collection<WrapToken> child) {
		List<WrapToken> ret = new ArrayList<WrapToken>();
		for (WrapToken token : parent) {
			if (!child.contains(token)) {
				ret.add(token);
			}
		}
		log.debug("parent has " + parent.size() + " tokens.");
		log.debug("child has " + child.size() + " tokens.");
		log.debug("return " + ret.size() + " tokens.");
		return ret.toArray(new WrapToken[0]);
	}

	public static double calcContribution(Cluster cluster, List<WrapToken> tokens) {
		double score = 0;
		for (WrapToken c : cluster.getTokens()) {
			for (WrapToken t : tokens) {
				if (!cluster.contains(t))
					score += c.getRelation(t);
			}
		}
		return score;
	}

	public static int calcContribution(WrapToken token, Cluster cluster) {
		int score = 0;
		for (WrapToken w : cluster.getTokens()) {
			if (token.getRelateToken().containsKey(w)) {
				score += token.getRelateToken().get(w);
			}
		}
		return score;
	}
}

package org.shako.textsummary.analyze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.shako.textsummary.context.SummaryConstant;
import org.shako.textsummary.data.Article;
import org.shako.textsummary.data.Token;


class Graph {
	
	private static Logger log = Logger.getLogger(Graph.class);
	
	public Graph() {
		// TODO Auto-generated constructor stub
	}
	
	public void autoProcess(Article article){
		WrapToken[] total = Utility.wrapTokens(article.getTokens().values(), article.getTokens().size()); 
		List<Token> tokens = article.getTokenByFrequency();
		WrapToken[] freq = Utility.wrapTokens(tokens, tokens.size());	
		WrapToken[] remain = Utility.minusTokens(Arrays.asList(total), Arrays.asList(freq));
		Cluster[] clusters = initCluster(freq);
		calcContribution(clusters, remain, freq);
		WrapToken[] candiLinks = findLinkTokens(remain, freq, clusters);
		
	}
	
	private Cluster[] initCluster(WrapToken[] freqTokens) {
		int size = freqTokens.length;
		int k = size * (size - 1) / 2;
		Double[] relates = new Double[k];
		k = 0;
		for(int i = 0; i < size; i++) {			
			for(int j = i + 1; j < size; j++) {
				relates[k] = (double) Utility.calcRelate(freqTokens[i], freqTokens[j]);
				k++;
			}
		}
		//size - 1条边是size个点组成连通图的最小机会
		int n = size - 1;
		int level = (int) Utility.calcLevel(relates, n);
		return Utility.cluster(level, freqTokens);
	}	
		
	private void calcContribution(Cluster[] clusters, WrapToken[] remain, WrapToken[] freq){
		//计算cluster的贡献值
		for(Cluster c : clusters) {
			int score = Utility.calcContribution(c, Arrays.asList(freq));
			for(WrapToken t : c.getTokens()) {
				for(WrapToken rt : remain){
					score += Utility.calcRelate(t, rt);
				}
			}
			c.setContributionToArticle(score);
		}
	}
	
	private WrapToken[] findLinkTokens(WrapToken[] remain, WrapToken[] freq, Cluster[] clusters){
		if(isConnectGraph(clusters)) {
			log.debug("this is a connectted graph, maybe no links exist.");
			return new WrapToken[0];
		}
		Map<WrapToken, Double> tokens = new HashMap<WrapToken, Double>();
		List<Double> scores = new ArrayList<Double>();
		for(WrapToken w : remain) {
			double score = 1;
			for(Cluster c : clusters){
				score = score * (1 - (double) Utility.calcContribution(w, c) / (double) c.getContributionToArticle());
			}
			tokens.put(w, 1 - score);
			scores.add(score);
		}
		for(WrapToken w : freq) {
			double score = 1;
			for(Cluster c : clusters){
				score = score * (1 - (double) Utility.calcContribution(w, c) / (double) c.getContributionToArticle());
			}
			tokens.put(w, 1 - score);
			scores.add(score);
		}
		int n = (int) (tokens.size() * SummaryConstant.LINK_RATIO);
		double level = Utility.calcLevel(scores.toArray(new Double[0]), n);
		ArrayList<WrapToken> cand = new ArrayList<WrapToken>();
		for(Entry<WrapToken, Double> entry : tokens.entrySet()) {
			if(entry.getValue() >= level){
				cand.add(entry.getKey());
			}
		}
		return cand.toArray(new WrapToken[0]);
	}
		
	private boolean isConnectGraph(Cluster[] clusters){
		if(clusters.length == 1) {
			return true;
		}
		return false;
	}
}

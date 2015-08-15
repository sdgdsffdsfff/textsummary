package org.shako.textsummary.analyze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.shako.textsummary.data.Article;
import org.shako.textsummary.data.Token;


class Graph {
	
	private static Logger log = Logger.getLogger(Graph.class);
	private WrapToken[] total;
	private WrapToken[] freq;
	private WrapToken[] remain;

	public void initEnv(Article article){
		total = Utility.wrapTokens(article.getAllTokens()); 
		freq = Utility.wrapTokens(article.getTokenByFrequency());	
		remain = Utility.minusWrapTokens(Arrays.asList(total), Arrays.asList(freq));
	}
	
	public List<Token> process(){
		Cluster[] clusters = initCluster(freq);
		calcContribution(clusters, remain, freq);
		/*genContriTokens(clusters);*/
		/*WrapToken[] candiLinks = findLinkTokens(remain, freq, clusters);*/
		List<Token> tokens = new ArrayList<Token>();
		Iterator<WrapToken> iter;
		for(Cluster c : clusters){
			iter = c.getTokens().iterator();
			if(iter.hasNext())
				tokens.add(iter.next().getData());
		}
		return tokens;
	}
	
	/*private void genContriTokens(Cluster[] clusters) {
		for(Cluster c : clusters){
			for(WrapToken t : c.getTokens()){
				t.setContrToArticle(c.getContributionToArticle());
			}
		}
		
	}*/

	private Cluster[] initCluster(WrapToken[] freqTokens) {
		int size = freqTokens.length;
		int k = size * (size - 1) / 2;
		Double[] relates = new Double[k];
		k = 0;
		for(int i = 0; i < size; i++) {			
			for(int j = i + 1; j < size; j++) {
				relates[k++] = Utility.calcRelate(freqTokens[i], freqTokens[j]);
			}
		}
		//size - 1条边是size个点组成连通图的最小机会
		int n = size - 1;
		Utility.popup(relates, n);
		return Utility.cluster(relates, freqTokens);
	}	
		
	private void calcContribution(Cluster[] clusters, WrapToken[] remain, WrapToken[] freq){
		if(this.isConnectGraph(clusters)){
			clusters[0].setContributionToArticle(1);
			return;
		}
		//计算cluster的贡献值
		for(Cluster c : clusters) {
			double score = Utility.calcContribution(c, Arrays.asList(freq));
			for(WrapToken t : c.getTokens()) {
				for(WrapToken rt : remain){
					score += Utility.calcRelate(t, rt);
				}
			}
			c.setContributionToArticle(score);
		}
	}
	
	/*private WrapToken[] findLinkTokens(WrapToken[] remain, WrapToken[] freq, Cluster[] clusters){
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
		Utility.popup(scores.toArray(new Double[0]), n);
		ArrayList<WrapToken> cand = new ArrayList<WrapToken>();
		for(Entry<WrapToken, Double> entry : tokens.entrySet()) {
			if(entry.getValue() >= level){
				cand.add(entry.getKey());
			}
		}
		return cand.toArray(new WrapToken[0]);
	}*/
		
	private boolean isConnectGraph(Cluster[] clusters){
		if(clusters.length == 1) {
			return true;
		}
		return false;
	}
}

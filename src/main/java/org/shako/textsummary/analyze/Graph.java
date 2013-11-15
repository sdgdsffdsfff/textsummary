package org.shako.textsummary.analyze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.shako.textsummary.data.Article;
import org.shako.textsummary.data.Token;


class Graph {
	
	private static Logger log = Logger.getLogger(Graph.class);
	private Cluster[] clusters;
	private LinkToken[] links;
	
	public Graph() {
		// TODO Auto-generated constructor stub
	}
	
	public void autoProcess(Article article){
		initGraph(article.getTokenByFrequency());
		findLinks(article);
	}
	
	private void initGraph(List<Token> tokens) {
		log.debug("begin init graph");
		int size = tokens.size();
		WrapToken[] freqTokens = Utility.wrapTokens(tokens, size);
		int k = size * (size - 1) / 2;
		int[] relates = new int[k];
		k = 0;
		for(int i = 0; i < size; i++) {			
			for(int j = i + 1; j < size; j++) {
				relates[k] = Utility.calcRelate(freqTokens[i], freqTokens[j]);
				k++;
			}
		}
		//size - 1条边是size个点组成连通图的最小机会
		int n = size - 1;
		int level = Utility.calcRelLevel(relates, n);
		clusters = Utility.cluster(level, freqTokens);
		log.debug("graph init done");
	}	
		
	private LinkToken[] findLinks(Article article){
		if(isConnectGraph()) {
			links = new LinkToken[0];
			log.debug("this is a connectted graph, maybe no links exist.");
			return links;
		}
		WrapToken[] total = Utility.wrapTokens(article.getTokens().values(), article.getTokens().size());
		
		List<WrapToken> consume = new ArrayList<WrapToken>();
		for(Cluster c : clusters){
			consume.addAll(c.getTokens());
		}
		WrapToken[] remain = Utility.minusTokens(Arrays.asList(total), consume);
		
		//计算cluster的贡献值
		for(Cluster c : clusters) {
			int score = Utility.calcContribution(c, consume);
			for(WrapToken t : c.getTokens()) {
				for(WrapToken rt : remain){
					score += Utility.calcRelate(t, rt);
				}
			}
			c.setContributionToArticle(score);
		}
		
		//寻找连接点
		for(WrapToken token : remain) {
			
		}
		return links;
	}
		
	private boolean isConnectGraph(){
		if(clusters.length == 1) {
			return true;
		}
		return false;
	}
}

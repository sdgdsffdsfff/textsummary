package org.shako.textsummary.analyze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Cluster {
	private List<WrapToken> tokens;
	private double contribution;
	private static Logger log = LoggerFactory.getLogger(Cluster.class);
	
	public Cluster() {
		this.tokens = new ArrayList<WrapToken>();
	}
	
	public double getContributionToArticle() {
		return contribution;
	}
	
	public void setContributionToArticle(double contribution) {
		this.contribution = contribution;
	}

	public List<WrapToken> getTokens() {
		return tokens;
	}

	public boolean push(WrapToken token){
		return this.tokens.add(token);
	}
	
	public boolean pop(WrapToken token) {
		return this.tokens.remove(token);
	}

	public boolean contains(WrapToken token) {
		return tokens.contains(token);
	}
	
	public List<WrapToken> process(WrapToken seed, double level){
		if(seed == null) {
			log.info("add a null object to the graph, be careful!");
		} else {
			LinkedList<WrapToken> before = new LinkedList<WrapToken>();
			ArrayList<WrapToken> after = new ArrayList<WrapToken>();
			before.push(seed);
			after.add(seed);
			while(!before.isEmpty()){
				WrapToken t = before.pop();
				push(t);
				for(Entry<WrapToken, Double> sub : t.getRelateToken().entrySet()){
					WrapToken subToken = sub.getKey();
					if(sub.getValue() >= level && !after.contains(subToken)){
						before.push(subToken);
						after.add(subToken);
					}
				}
			}
		}
		return tokens;
	}
	
	public int size(){
		return tokens.size();
	}
	
}

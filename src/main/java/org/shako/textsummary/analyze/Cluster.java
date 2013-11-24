package org.shako.textsummary.analyze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

class Cluster {
	private List<WrapToken> tokens;
	private int contribution;
	private static Logger log = Logger.getLogger(Cluster.class);
	
	public Cluster() {
		this.tokens = new ArrayList<WrapToken>();
	}
	
	public int getContributionToArticle() {
		return contribution;
	}
	
	public void setContributionToArticle(int contribution) {
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
	
	public List<WrapToken> process(WrapToken seed, int level){
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
				for(Entry<WrapToken, Integer> sub : t.getRelateToken().entrySet()){
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

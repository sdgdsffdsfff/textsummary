package org.shako.textsummary.analyze;

import java.util.HashMap;
import java.util.Map;

import org.shako.textsummary.data.Token;

class WrapToken {
	private Token data;
	private Map<WrapToken, Double> relations;
	private double contrToArticle;
	
	public WrapToken(Token data) {
		this.data = data;
		relations = new HashMap<WrapToken, Double>();					
	}
	
	public Map<WrapToken, Double> getRelateToken() {
		return relations;
	}

	public void addRelation(WrapToken token, double coef) {
		relations.put(token, coef);
	}
	
	public Token getData() {
		return data;
	}
	
	public double getRelation(WrapToken token) {
		return relations.get(token);
	}
	
	public double getContrToArticle() {
		return contrToArticle;
	}

	public void setContrToArticle(double contrToArticle) {
		this.contrToArticle = contrToArticle;
	}

	@Override
 	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		
		if(!(o instanceof WrapToken))
		{
			return false;
		}
		else
		{
			WrapToken other = (WrapToken)o;
			return this.data.equals(other.data);
		}
	}
	
	@Override
	public int hashCode()
	{
		return data.hashCode();
	}
	
	@Override
	public String toString()
	{
		return data.toString();		
	}
}

package org.shako.textsummary.analyze;

import java.util.HashMap;
import java.util.Map;

import org.shako.textsummary.data.Token;

class WrapToken {
	private Token data;
	private Map<WrapToken, Integer> relateCoefficients;
	
	public WrapToken(Token data) {
		this.data = data;
		relateCoefficients = new HashMap<WrapToken, Integer>();					
	}
	
	public Map<WrapToken, Integer> getRelateToken() {
		return relateCoefficients;
	}

	public void setCoefficient(WrapToken token, int coef) {
		relateCoefficients.put(token, coef);
	}
	
	public Token getData() {
		return data;
	}
	
	public int getCoefficient(WrapToken token) {
		return relateCoefficients.get(token);
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

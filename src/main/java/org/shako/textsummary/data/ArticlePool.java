package org.shako.textsummary.data;

import java.util.ArrayList;
import java.util.List;

class ArticlePool {

	private List<Article> articles;
	
	public ArticlePool() {
		articles = new ArrayList<Article>();
	}
	
	public int put(Article article) {
		articles.add(article);
		return getSize();
	}
	
	public int getSize() {
		return articles.size();
	}
}

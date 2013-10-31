package org.shako.data;

import java.util.ArrayList;
import java.util.List;

class ArticlePool {

	private List<Article> articles;
	private int size;
	
	public ArticlePool() {
		articles = new ArrayList<Article>();
	}
	
	public int put(Article article) {
		articles.add(article);
		size++;
		return size;
	}
}

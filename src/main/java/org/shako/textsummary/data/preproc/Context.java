package org.shako.textsummary.data.preproc;

import org.shako.textsummary.data.Article;

public class Context {

	private Article article;

	public Context() {
		article = new Article();
	}

	public Article getArticle() {
		return article;
	}

}

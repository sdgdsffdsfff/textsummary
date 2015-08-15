package org.shako.textsummary.analyze;

import java.util.List;

import org.apache.log4j.Logger;
import org.shako.textsummary.data.Article;
import org.shako.textsummary.data.Token;

public class Analyzer {

	private Article article;
	private Graph graph;

	private static Logger log = Logger.getLogger(Analyzer.class);

	public void initEnv(Article article) {
		this.article = article;
		graph = new Graph();
		graph.initEnv(article);
	}

	public List<Token> process() {
		return graph.process();
	}
}

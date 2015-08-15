package org.shako.textsummary.analyze;

import java.util.List;

import org.shako.textsummary.data.Article;
import org.shako.textsummary.data.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Analyzer {

	private Article article;
	private Graph graph;

	private static Logger log = LoggerFactory.getLogger(Analyzer.class);

	public void initEnv(Article article) {
		this.article = article;
		graph = new Graph();
		graph.initEnv(article);
	}

	public List<Token> process() {
		return graph.process();
	}
}

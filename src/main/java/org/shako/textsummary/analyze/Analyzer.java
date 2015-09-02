package org.shako.textsummary.analyze;

import java.util.List;

import org.shako.textsummary.data.Article;
import org.shako.textsummary.data.Token;

public class Analyzer {

	private Graph graph;

	public void initEnv(Article article) {
		graph = new Graph();
		graph.initEnv(article);
	}

	public List<Token> process() {
		return graph.process();
	}
}

package org.shako.textsummary;

import java.util.Arrays;
import java.util.List;

import org.shako.textsummary.data.Article;
import org.shako.textsummary.data.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class ArticleTest {

	static Logger log = LoggerFactory.getLogger(ArticleTest.class);

	private String sentence = "A Dutch artist and designer has come up with a device he hopes will suck pollutants from Beijing`s smog-cloaked skies, creating columns of clean air for the city`s surgical-mask wearing residents.";

	@Test
	public void test_getTokenByFrequency() {
		sentence = sentence.toLowerCase();
		String[] tokens = sentence.split("[^a-zA-Z]+");
		List<String> data = Arrays.asList(tokens);
		Article article = new Article();
		article.readNewSentence(data);
		List<Token> _freqToken = article.getTokenByFrequency();
		log.debug("/******** ArticleTest test_getTokenByFrequency **************/");
		for (Token t : _freqToken) {
			log.debug(t.toString() + ":" + t.getTotalNumber());
		}
	}

	@Test
	public void test_readNewSentence() {
		sentence = sentence.toLowerCase();
		String[] tokens = sentence.split("[^a-zA-Z]+");
		List<String> data = Arrays.asList(tokens);
		Article article = new Article();
		article.readNewSentence(data);
		log.debug("/******** ArticleTest test_readNewSentence **************/");
		for (Token t : article.getAllTokens()) {
			log.debug(t.toString() + ":" + t.getTotalNumber());
		}
	}
}

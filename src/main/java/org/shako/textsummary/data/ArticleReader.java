package org.shako.textsummary.data;

public class ArticleReader {
	
	private ArticlePool pool;
	
	private ArticleReader() {
		pool = new ArticlePool();
	}
	
	public static ArticleReader getReader() {
		return _ArticleReader._articleReader;
	}
	/**
	 * singleton 
	 * @author shako
	 *
	 */
	private static class _ArticleReader {
		public static ArticleReader _articleReader = new ArticleReader();
	}
	
	
}

package org.shako.data;

public class ArticleReader {
	
	private ArticlePool pool;
	private ArticleReader() {
		pool = new ArticlePool();
	}
	
	public ArticleReader getReader() {
		return _ArticleReader._articleReader;
	}
	
	private static class _ArticleReader {
		public static ArticleReader _articleReader = new ArticleReader();
	}
	
	/******************* methods **************/
	
	public void read(String filePath) {
		
		Article article = new Article();
	}
}

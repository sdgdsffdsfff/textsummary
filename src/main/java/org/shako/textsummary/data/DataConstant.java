package org.shako.textsummary.data;

class DataConstant {
	
	/*********** sentence category *************/
	/**
	 * 以句号结尾，陈述观点的语句;
	 * 包含问号、感叹号、引号的语句，通常带有修饰语气，描述成分强烈
	 */
	public static int SENTENCE_CATE_DECLARE = 1;
	
	public static int SENTENCE_CATE_NONDECLARE = 2;
	
	public static int SENTENCE_CATE_OTHER = 3;
	
	/************ token source *****************/
	/**
	 * 通过词组出现在文章中的位置，赋予不同主题影响力，得出其真实的贡献加权值
	 */
	public static int TOKEN_FORM_DEFAULT = 11;
	public static int TOKEN_FROM_TITLE = 12;
	public static int TOKEN_FROM_OTHER = 13;
	
}

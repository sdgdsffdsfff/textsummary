package org.shako.textsummary.demo;

import org.shako.textsummary.data.preproc.DefaultArticleReader;
import org.shako.textsummary.data.preproc.IReader;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IReader arti = new DefaultArticleReader("src/main/resource/demo");
		arti.read();
	}

}

package org.shako.textsummary.data.preproc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

public class DefaultArticleReader implements IReader {

	private static Logger log = Logger.getLogger(DefaultArticleReader.class);
	
	private String filePath;
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public DefaultArticleReader(String filePath) {
		this.filePath = filePath;
	}

	public DefaultArticleReader() {
		// TODO Auto-generated constructor stub
	}

	public Context read() {
		Context ctx = new Context();
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String s;
			int i = 1;
			while(( s = in.readLine()) != null){
				i++;
				// TODO read data from s;
				// using tokenizer to generate sentence
			}
			if(in != null)
				in.close();
			
		} catch (FileNotFoundException e) {
			log.error("file not found!");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return ctx;
	}

}

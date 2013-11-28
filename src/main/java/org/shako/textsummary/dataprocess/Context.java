package org.shako.textsummary.dataprocess;

import java.util.ArrayList;
import java.util.List;

public class Context {

	private List<Paragraph> paras;
	
	public void addParagraph(Paragraph para) {
		if(paras == null) {
			paras = new ArrayList<Paragraph>();
		}
		paras.add(para);
	}

}

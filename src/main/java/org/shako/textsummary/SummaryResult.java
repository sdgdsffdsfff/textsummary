package org.shako.textsummary;

import java.util.ArrayList;
import java.util.List;

public interface SummaryResult {

	public List<String> getLines();

	public void addLine(String content);

	public static final SummaryResult NoSummaryResult = new SummaryResult() {
		@Override
		public List<String> getLines() {
			return new ArrayList<>();
		}

		@Override
		public void addLine(String content) {
			// NOP
		}
	};
}
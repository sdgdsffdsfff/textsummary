package org.shako.textsummary;

import java.util.Arrays;
import java.util.List;

public interface Strategy {

	void preProc(int lineNumber, String content);

	SummaryResult proc();

	// get lines between one to three(at most 3 lines)
	public Strategy Default = new Strategy() {

		private int Max_Lines = 3;
		private SummaryResult result = new SummaryResult() {

			private String[] lines = new String[3];
			private int index = 0;

			public void addLine(String line) {
				lines[index++] = line;
			}

			@Override
			public List<String> getLines() {
				return Arrays.asList(lines);
			}

		};

		@Override
		public void preProc(int lineNumber, String content) {
			if (lineNumber < Max_Lines) {
				result.addLine(content);
			}
		}

		@Override
		public SummaryResult proc() {
			return result;
		}

	};
}

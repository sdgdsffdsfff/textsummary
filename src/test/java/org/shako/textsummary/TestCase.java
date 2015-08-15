package org.shako.textsummary;

import org.testng.annotations.Test;

public class TestCase {

	@Test
	public void extract() {
		String a = "test";
		String b = "abcd";
		String temp;
		temp = a;
		a = b;
		b = temp;
		System.out.print(b + a);
	}
}

package com.denis.learning.management;

import java.util.Arrays;

//In Java 6, the LargeString class generates 360,984 bytes, but in Java 7, it goes up to a whopping 20,441,536 bytes.
public class LargeString {
	public static void main(String[] args) {
		char[] largeText = new char[10 * 1000 * 1000];
		Arrays.fill(largeText, 'A');

		String superString = new String(largeText);

		long bytes = ThreadMemory.threadAllocatedBytes();

		String[] subStrings = new String[largeText.length / 1000];
		for (int i = 0; i < subStrings.length; i++) {
			subStrings[i] = superString.substring(i * 1000, i * 1000 + 1000);
		}

		bytes = ThreadMemory.threadAllocatedBytes() - bytes;
		System.out.printf("%,d%n", bytes);

	}
}

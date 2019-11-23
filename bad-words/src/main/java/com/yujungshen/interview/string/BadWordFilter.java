package com.yujungshen.interview.string;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Problem:
 * 
 * Filter out bad words in String and replace with "****", note that the spacing and case should be preserver for
 * good words.
 * We will use Google's profanity word list to filter on strings. Google list here:
 * https://raw.githubusercontent.com/RobertJGabriel/Google-profanity-words/master/list.txt
 * 
 * @author yujungs700
 *
 */
public class BadWordFilter {

	private final String GOOGLE_PROFANITY_WORD_LIST_URL = "https://raw.githubusercontent.com/RobertJGabriel/Google-profanity-words/master/list.txt";
	private List<String> badWordList;
	
	public BadWordFilter() {
		try {
			List<String> badWords = getGoogleProfanityWords();
			this.badWordList = badWords;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BadWordFilter(List<String> badWordList) {
		this.badWordList = badWordList;
	}
	
	public String filterBadWords(String input) {
		if (badWordList == null || badWordList.size() == 0) {
			return input;
		}
		// first find all the words by spliting strings on whitespaces
		String lowercaseInput = input.toLowerCase();
		String[] words = lowercaseInput.split("\\s+");
		StringBuilder outputBuilder = new StringBuilder();
		int totalLengh = input.length();
		int lastCharIndex = totalLengh - 1;
		boolean foundBadWords = false;
		for (String word : words) {
			// check if the word
			if (badWordList.contains(word)) {
				foundBadWords = true;
				// we need to replace this
				int wordStart = lowercaseInput.indexOf(word);
				if (wordStart > 0) {
					outputBuilder.append(input.substring(0, wordStart));
				}
				outputBuilder.append("****");
				int wordEnd = wordStart + word.length();
				if (wordEnd < lastCharIndex) {
					outputBuilder.append(input.substring(wordEnd + 1, totalLengh));
				}
			}
		}
		if (!foundBadWords) {
			return input;
		}
		
		return outputBuilder.toString();
	}

	private List<String> getGoogleProfanityWords() throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet getReq = new HttpGet(GOOGLE_PROFANITY_WORD_LIST_URL);
		CloseableHttpResponse resp = httpClient.execute(getReq);
		try {
			if (resp.getStatusLine().getStatusCode() == 200) {
				String wordList = EntityUtils.toString(resp.getEntity());
				if (wordList != null) {
					String[] words = wordList.toLowerCase().split("\n");
					ArrayList<String> badWordList = new ArrayList<>();
					for (String word : words) {
						badWordList.add(word);
					}
					return badWordList;
				}
			} else {
				System.err.println("Have problem getting profanity word list");
				System.err.println(resp.getStatusLine());
			}
		} finally {
			resp.close();
			httpClient.close();
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		BadWordFilter filter = new BadWordFilter();
		System.out.println(filter.filterBadWords("xxballs xsdf bass balls xxx"));
	}
}

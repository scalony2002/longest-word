package com.pf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TextScanner {
    public static final String DEFAULT_SENTENCE_SEPARATORS = ".?!";

    private final Pattern wordPattern;
    private final Pattern sentencePattern;
    private final Pattern nonWordPattern;

    public static void main(String[] args) {
        var scanner = new TextScanner(DEFAULT_SENTENCE_SEPARATORS);
        List<ScanResult> result = scanner.findLongestWordInSentence(System.in);

        result.forEach(scanResult -> System.out.println(scanResult.getLongestWord() + "\t" +scanResult.getSentenceLength()));
    }

    public TextScanner(String sentenceSeparators) {
        this.wordPattern = Pattern.compile("^\\w+");
        this.sentencePattern = Pattern.compile("^[{sentenceSep}]+".replace("{sentenceSep}", sentenceSeparators));
        this.nonWordPattern = Pattern.compile("^\\W+");
    }

    List<ScanResult> findLongestWordInSentence(InputStream in) {
        var scan = new Scanner(in);
        var response = new ArrayList<ScanResult>();

        ScanResult.Builder currentSentence = ScanResult.newBuilder();
        while (scan.hasNext()) {
            String fragment = scan.next();

            while (fragment.length() > 0) {
                var wordMatcher = wordPattern.matcher(fragment);
                var sentenceMatcher = sentencePattern.matcher(fragment);
                var nonWordMatcher = nonWordPattern.matcher(fragment);
                if (wordMatcher.find()) {
                    var word = wordMatcher.group();
                    currentSentence.processWord(word);
                    fragment = fragment.substring(word.length());
                } else if (sentenceMatcher.find()) {
                    var sep = sentenceMatcher.group();
                    fragment = fragment.substring(sep.length());
                    response.add(currentSentence.build());
                    currentSentence = ScanResult.newBuilder();
                } else if (nonWordMatcher.find()) {
                    var nonword = nonWordMatcher.group();
                    fragment = fragment.substring(nonword.length());
                }
            }
        }

        var lastSentence =  currentSentence.build();
        if (lastSentence.getSentenceLength() > 0) {
            response.add(lastSentence);
        }

        return response;
    }
}

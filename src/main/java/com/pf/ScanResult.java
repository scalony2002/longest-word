package com.pf;

public class ScanResult {
    private String longestWord;
    private String shortestWord;
    private int sentenceLength;

    private ScanResult(Builder builder) {
        longestWord = builder.longestWord;
        shortestWord = builder.shortestWord;
        sentenceLength = builder.sentenceLength;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getLongestWord() {
        return longestWord;
    }

    public String getShortestWord() {
        return shortestWord;
    }

    public int getSentenceLength() {
        return sentenceLength;
    }


    public static final class Builder {
        private String longestWord;
        private String shortestWord;
        private int sentenceLength;

        private Builder() {
        }

        public Builder setLongestWord(String val) {
            longestWord = val;
            return this;
        }

        public Builder setShortestWord(String val) {
            shortestWord = val;
            return this;
        }

        public Builder setSentenceLength(int val) {
            sentenceLength = val;
            return this;
        }

        public Builder processWord(String newWord) {
            sentenceLength += 1;
            if (longestWord == null || longestWord.length() < newWord.length()) {
                longestWord = newWord;
            }
            if (shortestWord == null || shortestWord.length() > newWord.length()) {
                shortestWord = newWord;
            }
            return this;
        }

        public ScanResult build() {
            return new ScanResult(this);
        }
    }
}

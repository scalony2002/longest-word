package com.pf;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

class TextScannerTest {
    private TextScanner sut = new TextScanner(".?!");

    @Test
    void shouldReturnEmptyListForEmptyInput() {
        List<ScanResult> result = sut.findLongestWordInSentence(toInputStream(""));

        assertThat(result, hasSize(0));
    }

    @Test
    void shouldReturnLongestWordInASentence() {
        List<ScanResult> result = sut.findLongestWordInSentence(toInputStream("The cow jumped over the moon."));

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getSentenceLength(), is(6));
        assertThat(result.get(0).getLongestWord(), is("jumped"));
    }

    @Test
    void shouldNotTreatSymbolsAsWords() {
        List<ScanResult> result = sut.findLongestWordInSentence(toInputStream("The cow-cow-cow jumped, over the moon."));

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getSentenceLength(), is(8));
        assertThat(result.get(0).getLongestWord(), is("jumped"));
    }

    @Test
    void shouldHandleMoreThanOneSentence() {
        List<ScanResult> result = sut.findLongestWordInSentence(toInputStream("The cow jumped, over the moon. Hip, hip! Hurray!"));

        assertThat(result, hasSize(3));
        assertThat(result.get(0).getSentenceLength(), is(6));
        assertThat(result.get(0).getLongestWord(), is("jumped"));
        assertThat(result.get(1).getSentenceLength(), is(2));
        assertThat(result.get(1).getLongestWord(), is("Hip"));
        assertThat(result.get(2).getSentenceLength(), is(1));
        assertThat(result.get(2).getLongestWord(), is("Hurray"));
    }

    @Test
    void shouldWorkWithUnterminatedSentences() {
        List<ScanResult> result = sut.findLongestWordInSentence(toInputStream("The cow jumped, over the moon"));

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getSentenceLength(), is(6));
        assertThat(result.get(0).getLongestWord(), is("jumped"));
    }

    @Test
    void shouldFindShortestWordInASentence() {
        List<ScanResult> result = sut.findLongestWordInSentence(toInputStream("The cow jumped, over the moon. Oh yes? OK, I'll do it"));

        assertThat(result, hasSize(3));
        assertThat(result.get(0).getShortestWord(), is("The"));
        assertThat(result.get(1).getShortestWord(), is("Oh"));
        assertThat(result.get(2).getShortestWord(), is("I"));
    }

    private InputStream toInputStream(String content) {
        return new ByteArrayInputStream(content.getBytes());
    }
}
package org.recap;

import kr.bydelta.koala.hnn.SentenceSplitter;
import org.recap.sentence.Extractor;
import org.recap.sentence.Splitter;

import java.util.List;
import java.util.Map;

public class Summarizer {
    private String text;

    public Summarizer(String text) {
        this.text = text;
    }

    public String summarize() {
        List<String> sentences = Splitter.split(text);
        Map<String, List<String>> wordsEachSentence = Extractor.extract(sentences);
        return "";
    }
}

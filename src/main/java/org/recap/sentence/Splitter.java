package org.recap.sentence;

import kr.bydelta.koala.hnn.SentenceSplitter;

import java.util.List;

public class Splitter {
    public static List<String> split(String text) {
        SentenceSplitter sentenceSplitter = new SentenceSplitter();
        return sentenceSplitter.sentences(text);
    }
}

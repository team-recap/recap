package org.recap;

import org.recap.sentence.Extractor;
import org.recap.sentence.Short;
import org.recap.sentence.Splitter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Summarizer {
    private final String text;

    public Summarizer(String text) {
        this.text = text;
    }

    public List<String> summarize(Graph.SimilarityMethods similarityMethods) {
        List<String> sentences = Splitter.split(text); // 문장 분리

        //System.out.println(sentences.size());

        //List<String> withOutMAG_Sentences = Short.sentenceShorter(sentences); // MAG(부사제거)

        Map<String, List<String>> wordsWithSentences = Extractor.extract(sentences); // 부사제거 명사추출 한번에

        // 그래프 생성
        Graph graph = new Graph(wordsWithSentences, similarityMethods);
        return graph.calculatePageRank().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }
}

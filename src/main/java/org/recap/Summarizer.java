package org.recap;

import org.recap.sentence.Extractor;
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

        Map<String, List<String>> wordsWithSentences = Extractor.extract(sentences); // 명사 추출

        // 그래프 생성 및 빌드
        Graph graph = new Graph(wordsWithSentences);
        graph.build(similarityMethods);

        return graph.calculatePageRank().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }
}

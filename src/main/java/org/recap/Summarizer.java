package org.recap;

import org.recap.sentence.Extractor;
import org.recap.sentence.Splitter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Summarizer {
    public List<String> summarize(String text, Graph.SimilarityMethods similarityMethods) {
        List<String> sentences = Splitter.split(text); // 문장 분리

        int sentencesSize = sentences.size();
        int recapSize = Math.round((1/20f)*sentencesSize+2); // 몇줄로 줄일 건지
        //함수식 반올림으로 : y = (1/20)x + 2  //Desmos 그래프 복붙하면 : y=\frac{1}{20}x+2
        //limit가 없으면 안들고 오기 때문에 값이 더 커도 문제없음. //값을 딱 맞춰야 되면 올림으로 : y=x^{0.3}+\frac{1}{50}x-0.2

        Map<String, List<String>> wordsWithSentences = Extractor.extract(sentences); // 명사추출, 문장 내 부사제거

        // 그래프 생성
        Graph graph = new Graph(wordsWithSentences, similarityMethods); // 그래프 및 가중치 생성
        return graph.calculatePageRank(recapSize).stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }
}

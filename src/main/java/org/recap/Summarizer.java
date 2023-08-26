package org.recap;

import org.recap.sentence.Extractor;
import org.recap.sentence.Short;
import org.recap.sentence.Splitter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Summarizer {
    public List<String> summarize(String text, Graph.SimilarityMethods similarityMethods) {
        List<String> sentences = Splitter.split(text); // 문장 분리

        //System.out.println(sentences.size());

        int sentenceSize = sentences.size();

        if(sentenceSize<3){  //총문장 갯수가 3보다 작으면
            return sentences;
        }
        else{
            //List<String> withOutMAG_Sentences = Short.sentenceShorter(sentences); // MAG(부사제거)

            Map<String, List<String>> wordsWithSentences = Extractor.extract(sentences); // 부사제거 명사추출 한번에

            // 그래프 생성
            Graph graph = new Graph(wordsWithSentences, similarityMethods);
            return graph.calculatePageRank(Math.round(sentenceSize/2f)).stream().map(Map.Entry::getKey).collect(Collectors.toList());
        }
    }
}

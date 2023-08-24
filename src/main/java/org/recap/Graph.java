package org.recap;

import org.jgrapht.alg.interfaces.VertexScoringAlgorithm;
import org.jgrapht.alg.scoring.PageRank;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.recap.sentence.Similarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Graph {

    private final SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final Map<String, List<String>> sentencesWithWords;

    public enum SimilarityMethods { COSINE_SIMILARITY, JACCARD_SIMILARITY }

    public Graph(Map<String, List<String>> sentencesWithWords, SimilarityMethods similarityMethods) {
        this.sentencesWithWords = sentencesWithWords;

        sentencesWithWords.forEach((sentence, words) -> graph.addVertex(sentence)); // 각 문장으로 그래프 정점 생성

        for (Map.Entry<String, List<String>> entrySource : sentencesWithWords.entrySet()) {
            for (Map.Entry<String, List<String>> entryTarget : sentencesWithWords.entrySet()) {
                if (!Objects.equals(entrySource.getKey(), entryTarget.getKey())) { // 서로 같은 문장이 아닐 때만 유사도 검사 실시
                    float similarity = 0f;
                    if (similarityMethods == SimilarityMethods.COSINE_SIMILARITY)
                        similarity = Similarity.calculateCosineSimilarity(entrySource, entryTarget); // 코사인 유사도로 계산
                    else if (similarityMethods == SimilarityMethods.JACCARD_SIMILARITY)
                        similarity = Similarity.calculateCosineSimilarity(entrySource, entryTarget); // 자카드 유사도로 계산

                    if (similarity > 0 && graph.getEdge(entrySource.getKey(), entryTarget.getKey()) == null) {
                        DefaultWeightedEdge edge = graph.addEdge(entrySource.getKey(), entryTarget.getKey());
                        graph.setEdgeWeight(edge, similarity);
                    }
                }
            }
        }
    }

    public List<Map.Entry<String, Double>> calculatePageRank() {
        VertexScoringAlgorithm<String, Double> pageRank = new PageRank<>(graph);
        List<String> sentences = new ArrayList<>(sentencesWithWords.keySet());
        return pageRank.getScores().entrySet().stream()
                .sorted((elem1, elem2) -> elem1.getValue() < elem2.getValue() ? 1 : -1)
                .limit(3)
                .collect(Collectors.toList())
                .stream()
                .sorted((source, target) -> sentences.indexOf(source.getKey()) > sentences.indexOf(target.getKey()) ? 1 : -1)
                .collect(Collectors.toList());
    }
}

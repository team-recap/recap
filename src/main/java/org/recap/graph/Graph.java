package org.recap.graph;

import org.recap.sentence.Similarity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Graph {

    public enum SimilarityMethods { COSINE_SIMILARITY, JACCARD_SIMILARITY }

    public static WeightedGraph makeWeightedGraph(Map<String, List<String>> sentencesWithWords, SimilarityMethods similarityMethods){
        WeightedGraph graph = new WeightedGraph();  //가중치가 있는 그래프 생성

        //그래프 만들기
        for (Map.Entry<String, List<String>> entrySource : sentencesWithWords.entrySet()) {
            graph.addNode(entrySource.getKey());  //그래프에 노드 추가

            for (Map.Entry<String, List<String>> entryTarget : sentencesWithWords.entrySet()) {
                if (!Objects.equals(entrySource.getKey(), entryTarget.getKey())) {
                    float similarity = 0f;
                    if (similarityMethods == SimilarityMethods.COSINE_SIMILARITY)
                        similarity = Similarity.calculateCosineSimilarity(entrySource, entryTarget); // 코사인 유사도로 계산
                    else if (similarityMethods == SimilarityMethods.JACCARD_SIMILARITY)
                        similarity = Similarity.calculateCosineSimilarity(entrySource, entryTarget); // 자카드 유사도로 계산

                    graph.addEdge(entrySource.getKey(), entryTarget.getKey(), similarity);  //그래프에 엣지, 가중치 추가
                }
            }
        }

        return graph;
    }
}

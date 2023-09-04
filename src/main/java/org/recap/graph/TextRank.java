package org.recap.graph;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextRank {
    public static List<Map.Entry<String, Double>> calculateTextRank(WeightedGraph graph, int recapSize) {
        double tolerance = 0.0001;  //이전 스코어의 최대값과 현재 스코어의 최대값을 (변화량) 비교하여 tolerance이하이면 알고리즘 종료
        double dampingFactor = 0.85d; //다른 노드로 이동할 확률(이탈률) 일반적으로 0.85를 많이 사용
        int maxNumIterations = 100;  //최대로 돌아가는 값

        Map<String, Double> scores = new LinkedHashMap<>();  //문장별 점수를 저장
        Map<String, Double> weightSums = new HashMap<>();  //문장별 가중치의 합을 저장

        for(String sentence : graph.getNodes()){  //초기값 설정 및 가중치 합계 계산
            scores.put(sentence, 1.0 / graph.getNodes().size());  //각 문장별 점수의 초기값 설정
            weightSums.put(sentence, 0.0);  //초기값 설정

            for(Edge edge : graph.getEdges(sentence)){
                weightSums.put(sentence, weightSums.get(sentence) + edge.getWeight());  //가중치 합계 계산
            }
        }

        double scoresChangeValue = tolerance;  //이전 점수와 현재 점수의 변화량의 최대값을 저장할 변수

        // TextRank 알고리즘 반복 실행
        while (maxNumIterations > 0 && scoresChangeValue >= tolerance){  //최대로 돌아가거나 변화량의 최대값이 공차 이하면 종료
            Map<String, Double> newScores = new HashMap<>();

            scoresChangeValue = 0d; //초기화

            //평균 텔레포트 확률 계산
            double avgTelePer = 0d;  //초기값
            //모든 노드에 대한 반복
            for(String sentence : graph.getNodes()){
                if(weightSums.get(sentence) > 0) {  //가중치합이 0보다 크면 (가중치가 0보다 큰 연결된 노드가 1개 이상 이면)
                    avgTelePer += (1d - dampingFactor) * scores.get(sentence);
                }
                else{  //연결된 노드가 0개 이면
                    avgTelePer += scores.get(sentence);  //연결된 노드가 없기 때문에 이동할데가 없음
                }
            }
            avgTelePer /= graph.getNodes().size();  //총 노드 갯수로 나누어서 평균을 구함

            //점수 계산
            //모든 노드에 대한 반복
            for (String sentence : graph.getNodes()) {
                double newScore = avgTelePer;  //점수를 평균 텔레포트 확률로 초기화

                //현재 노드와 연결된 엣지에 대한 반복
                for (Edge edge : graph.getEdges(sentence)) {
                    if(edge.getWeight() > 0) {  //가중치가 0 이상일때
                        //스코어 계산
                        newScore += dampingFactor * scores.get(edge.getTargetNode()) * edge.getWeight() / weightSums.get(edge.getTargetNode());
                    }
                }
                scoresChangeValue = Math.max(scoresChangeValue, Math.abs(scores.get(sentence) - newScore)); //스코어 변화량 업데이트
                newScores.put(sentence, newScore);
            }
            scores = newScores;  //스코어 업데이트
            maxNumIterations--;  //카운트 감소
        }

        return scores.entrySet().stream()
                .sorted((elem1, elem2) -> elem1.getValue() < elem2.getValue() ? 1 : -1)  //점수 기준으로 정렬
                .limit(recapSize)  //상위 recapSize만큼 선택
                .collect(Collectors.toList())
                .stream()
                .sorted((source, target) -> graph.getNodes().indexOf(source.getKey()) > graph.getNodes().indexOf(target.getKey()) ? 1 : -1)  //그래프의 키를 기준으로 정렬
                .collect(Collectors.toList());
    }
}

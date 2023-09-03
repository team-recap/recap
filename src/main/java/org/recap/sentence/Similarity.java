package org.recap.sentence;

import java.util.*;

public class Similarity {

    // 두 리스트의 단어 합집합 추출
    private static List<String> union(List<String> source, List<String> target) {
        Set<String> set = new HashSet<>();
        set.addAll(source);
        set.addAll(target);
        return new ArrayList<>(set);
    }

    // 두 리스트의 단어 교집합 추출
    private static List<String> intersection(List<String> source, List<String> target) {
        Set<String> sourceSet = new HashSet<>(source);
        Set<String> targetSet = new HashSet<>(target);
        sourceSet.retainAll(targetSet); // sourceSet에 targetSet의 원소만 남김
        return new ArrayList<>(sourceSet);
    }


    // 코사인 유사도 계산
    public static float calculateCosineSimilarity(Map.Entry<String, List<String>> entrySource, Map.Entry<String, List<String>> entryTarget) {
        List<String> intersection = intersection(entrySource.getValue(), entryTarget.getValue());

        // 분자: 교집합의 크기
        float numerator = intersection.size();

        // 분모: 두 리스트의 크기의 제곱근 곱
        float denominator = (float) (Math.sqrt(entrySource.getValue().size()) * Math.sqrt(entryTarget.getValue().size()));

        // 분모가 0인 경우
        if (denominator == 0f)
            return 0f; // 분모가 0이면 코사인 유사도는 0

        return numerator / denominator;
    }

    // 자카드 유사도 계산
    public static float calculateJaccardSimilarity(Map.Entry<String, List<String>> entrySource, Map.Entry<String, List<String>> entryTarget) {
        List<String> intersection = intersection(entrySource.getValue(), entryTarget.getValue());
        List<String> union = union(entrySource.getValue(), entryTarget.getValue());
        return (float) intersection.size() / (float) union.size();
    }
}

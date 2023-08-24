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
        List<String> list = new ArrayList<>();
        for (String word : source)
            if (target.contains(word))
                list.add(word);
        return list;
    }


    // 코사인 유사도 계산
    public static float calculateCosineSimilarity(Map.Entry<String, List<String>> entrySource, Map.Entry<String, List<String>> entryTarget) {
        List<String> intersection = intersection(entrySource.getValue(), entryTarget.getValue());
        return (float) intersection.size() / (float) (Math.sqrt(entrySource.getValue().size()) * Math.sqrt(entryTarget.getValue().size()));
    }

    // 자카드 유사도 계산
    public static float calculateJaccardSimilarity(Map.Entry<String, List<String>> entrySource, Map.Entry<String, List<String>> entryTarget) {
        List<String> intersection = intersection(entrySource.getValue(), entryTarget.getValue());
        List<String> union = union(entrySource.getValue(), entryTarget.getValue());
        return (float) intersection.size() / (float) union.size();
    }
}

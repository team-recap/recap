package org.recap.sentence;

import kr.bydelta.koala.data.Morpheme;
import kr.bydelta.koala.data.Sentence;
import kr.bydelta.koala.data.Word;
import kr.bydelta.koala.hnn.Parser;
import kr.bydelta.koala.hnn.Tagger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Extractor {
    public static Map<String, List<String>> extract(List<String> sentences) {
        final Tagger tagger = new Tagger();
        final Parser parser = new Parser();
        final Map<String, List<String>> wordsWithSentences = new LinkedHashMap<>(); // 키 - 문장, 값 - 추출된 단어들

        // 문장별
        for (String sentence : sentences) {
            Sentence analyzedSentence = tagger.tagSentence(sentence);
            // analyzedSentence = parser.analyze(analyzedSentence); // 사용시 형태소를 잘 끊어주는 반면 속도가 느려짐

            List<String> collectedWords = new ArrayList<>();

            // 문장 내 단어별
            for (Word word : analyzedSentence.getNouns()) {
                // 단어 내 형태소별 (ex. '리캡은' -> '리캡' + '은')
                for (Morpheme morpheme : word) {
                    // 모든 명사 종류 추출
                    if (morpheme.getTag().toString().matches("NN[GP]")) { // 일반 명사나 고유 명사를 추출함
                        if (morpheme.getSurface().length() > 1) // 글자가 하나인 명사는 제외
                            collectedWords.add(morpheme.getSurface());
                    }
                }
            }

            wordsWithSentences.put(sentence, collectedWords);
        }

        return wordsWithSentences;
    }
}

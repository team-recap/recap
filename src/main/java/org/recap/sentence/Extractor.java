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
        //final Parser parser = new Parser();
        final Map<String, List<String>> wordsWithSentences = new LinkedHashMap<>(); // 키 - 문장, 값 - 추출된 단어들

        // 문장별
        for (String sentence : sentences) {
            Sentence analyzedSentence = tagger.tagSentence(sentence);
            // analyzedSentence = parser.analyze(analyzedSentence); // 사용시 형태소를 잘 끊어주는 반면 속도가 느려짐

            StringBuilder withOutMAGSentence = new StringBuilder();

            int num=0;

            // 문장 내 단어별
            for (Word word : analyzedSentence) {
                //System.out.println(analyzedSentence.getSize());
                num++;

                // 단어 내 형태소별 (ex. '리캡은' -> '리캡' + '은')
                boolean hasMAG = false; // 부사 여부 확인
                for (Morpheme morpheme : word) {
                    //System.out.println(morpheme);
                    if (morpheme.getTag().toString().equals("MAG")) {
                        hasMAG = true;
                        //System.out.println(morpheme);
                    }
                }

                // 부사가 없는 경우에만 추가
                if (!hasMAG) {
                    withOutMAGSentence.append(word.getSurface());
                    if(num<analyzedSentence.getSize()-1){
                        withOutMAGSentence.append(" ");
                    }
                }
            }
            //System.out.println(withOutMAGSentence);

            //////////////////////////////////////////////

            List<String> collectedWords = new ArrayList<>();

            // 문장 내 단어별
            for (Word word : analyzedSentence.getNouns()) {
                String collectedWord = ""; // 저장될 단어

                // 단어 내 형태소별 (ex. '리캡은' -> '리캡' + '은')
                for (Morpheme morpheme : word) {
                    // 모든 명사 종류 추출
                    if (morpheme.getTag().toString().startsWith("N")) { // 명사 추출
                        collectedWord += morpheme.getSurface().replaceAll("[\".,“”]", ""); // 특수문자 삭제
                    }
                }

                if (collectedWord.length() >= 2) // 단어 길이가 2 이상일 때만 해당 단어 저장
                    collectedWords.add(collectedWord);
            }

            wordsWithSentences.put(withOutMAGSentence.toString(), collectedWords);
        }

        return wordsWithSentences;
    }
}

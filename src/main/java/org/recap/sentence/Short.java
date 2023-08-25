package org.recap.sentence;

import kr.bydelta.koala.data.Morpheme;
import kr.bydelta.koala.data.Sentence;
import kr.bydelta.koala.data.Word;
import kr.bydelta.koala.hnn.Parser;
import kr.bydelta.koala.hnn.Tagger;


import java.util.ArrayList;
import java.util.List;

public class Short {
    public static List<String> sentenceShorter(List<String> sentences) {
        final Tagger tagger = new Tagger();
        final Parser parser = new Parser();
        List<String> withOutMAGSentences = new ArrayList<>();

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
            withOutMAGSentences.add(withOutMAGSentence.toString());
        }

        return withOutMAGSentences;
    }
}

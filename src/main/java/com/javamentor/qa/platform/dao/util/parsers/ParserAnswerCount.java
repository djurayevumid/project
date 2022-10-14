package com.javamentor.qa.platform.dao.util.parsers;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ParserAnswerCount extends Parser<Integer>{

    private static final String ANSWERS = "answers:";

    public ParserAnswerCount(){
        predicate = createPredicate(ANSWERS);
    }

    @Override
    public Integer Parse(List<String> listStr){
        return listStr.stream()
                .filter(predicate)
                .map((s) -> {
                    String number = s.substring(ANSWERS.length());
                    if (number.matches("\\d\\d*") 
                            && number.length() < 5) {
                        return Integer.valueOf(number);
                    }
                    return -1;
                })
                .max(Integer::compare)
                .orElse(-1);
    }

}

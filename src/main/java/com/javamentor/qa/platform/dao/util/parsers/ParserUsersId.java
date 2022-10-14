package com.javamentor.qa.platform.dao.util.parsers;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ParserUsersId extends Parser<List<Long>>{

    private static final String USER = "user:";

    public ParserUsersId(){
        predicate = predicate = createPredicate(USER);
    }

    @Override
    public List<Long> Parse(List<String> listStr) {
        return listStr.stream()
                .filter(predicate)
                .map((s)->{
                    String number = s.substring(USER.length());
                    if (number.matches("\\d\\d*") 
                            && number.length() < Long.toString(Long.MAX_VALUE).length()) {
                        return Long.valueOf(number);
                    }
                    return Long.valueOf(-1);  
                })
                .collect(Collectors.toList());
    }


}

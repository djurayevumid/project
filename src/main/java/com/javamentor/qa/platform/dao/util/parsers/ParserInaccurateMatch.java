package com.javamentor.qa.platform.dao.util.parsers;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ParserInaccurateMatch extends Parser<String>{

    private List<Parser<?>> parserList;

    public ParserInaccurateMatch(List<Parser<?>> parsers){
        predicate = (c)->false;
        for(Parser p: parsers){
            predicate = predicate.and(p.getPredicate());
        }
        predicate = predicate.negate();

    }

    @Override
    public String Parse(List<String> listStr) {
        return listStr.stream()
                .filter(predicate)
                .map((s)-> {
                    StringBuilder str = new StringBuilder();
                    str.append('%');
                    for(char c: s.toCharArray()){
                        str.append(c).append('%');
                    }
                    return str.toString();
                })
                .reduce((s1,s2) -> s1 + " " + s2).orElse("");
    }

}

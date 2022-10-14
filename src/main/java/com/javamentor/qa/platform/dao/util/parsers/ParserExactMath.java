package com.javamentor.qa.platform.dao.util.parsers;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParserExactMath extends Parser<String>{

    ParserExactMath(){
        predicate = createPredicate("\"");
    }

    @Override
    public String Parse(List<String> listStr){
        return listStr.stream()
                .filter(predicate)
                .map((s)-> {
                    StringBuilder str = new StringBuilder();
                    str.append('%')
                            .append( s.substring(1, s.length() - 1))
                            .append('%');
                    return str.toString();

                })
                .reduce((s1,s2) -> s1 + "|" + s2).orElse("");
    }
}

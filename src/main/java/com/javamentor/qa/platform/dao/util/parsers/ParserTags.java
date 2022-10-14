package com.javamentor.qa.platform.dao.util.parsers;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParserTags extends Parser<List<String>>{

    public ParserTags(){
         predicate = createPredicate("[");
    }

    @Override
    public List<String> Parse(List<String> listStr) {
        return listStr.stream()
                .filter(predicate)
                .map((s)-> s.substring(1,s.length() - 1))
                .collect(Collectors.toList());
    }

}

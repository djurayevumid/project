package com.javamentor.qa.platform.dao.util.parsers;

import java.util.List;
import java.util.function.Predicate;


public abstract class Parser<T> {
    protected Predicate<String> predicate;

    public abstract T Parse(List<String> listStr);
    public Predicate<String> getPredicate(){
        return predicate;
    }
    static Predicate<String> createPredicate(String param){
        return (s)->s.startsWith(param);
    }
}

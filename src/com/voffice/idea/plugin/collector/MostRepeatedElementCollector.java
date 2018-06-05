package com.voffice.idea.plugin.collector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import  static java.util.stream.Collector.Characteristics.*;


public class MostRepeatedElementCollector<T> implements Collector<T,Map<T,Integer>,T> {

    @Override
    public Supplier<Map<T, Integer>> supplier() {
        return HashMap<T,Integer>::new;

    }

    @Override
    public BiConsumer<Map<T, Integer>, T> accumulator() {
        return new BiConsumer<Map<T, Integer>, T>() {
            @Override
            public void accept(Map<T, Integer> map, T t) {
                boolean b = map.containsKey(t);
                if(b) {
                    map.put(t,map.get(t)+1);
                }else{
                    map.put(t,1);
                }
            }
        };
    }

    @Override
    public BinaryOperator<Map<T, Integer>> combiner() {
        return new BinaryOperator<Map<T, Integer>>() {
            @Override
            public Map<T, Integer> apply(Map<T, Integer> map, Map<T, Integer> tIntegerMap2) {
                try {
                    tIntegerMap2.forEach((key,value)->{
                        boolean b = map.containsKey(key);
                        if(b) {
                            map.put(key,map.get(key)+value);
                        }else{
                            map.put(key,value );
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return map;
            }
        };
    }

    @Override
    public Function<Map<T, Integer>, T> finisher() {
        return new Function<Map<T, Integer>, T>() {
            @Override
            public T apply(Map<T, Integer> map) {
                T key = null;
                try {
                    key = map.entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue())).get().getKey();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return key;
            }
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(UNORDERED));
    }

    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("hello");
        list.add("hello");
        list.add("world");
        String collect = list.stream().collect(new MostRepeatedElementCollector<>());
        System.out.println(collect);

    }
}

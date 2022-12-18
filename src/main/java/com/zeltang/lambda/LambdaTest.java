package com.zeltang.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTest {

    @Test
    public void sortTest () {
        List<LambdaVo> list = new ArrayList<>();

        list = list.stream().sorted(Comparator.comparing(LambdaVo::getNum).reversed()).collect(Collectors.toList());

        list.stream().sorted(Comparator.comparing(LambdaVo::getNum,Comparator.reverseOrder())
                .thenComparing(LambdaVo::getCode, Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());

        list = list.stream().sorted(Comparator.comparing(LambdaVo::getDate, Comparator.nullsLast(Date::compareTo))).collect(Collectors.toList());

    }

    /**
     * Function<T, R> 是一个功能转换型的接口，可以把将一种类型的数据转化为另外一种类型的数据
     */
    @Test
    public void functionTest () {
        //获取每个字符串的长度，并且返回
        Function<String, Integer> function = String::length;
        Stream<String> stream = Stream.of("1", "12", "123");
        Stream<Integer> resultStream = stream.map(function);
        resultStream.forEach(System.out::println);
    }

    /**
     * Consumer<T>是一个消费性接口，通过传入参数，并且无返回的操作
     */
    @Test
    public void consumerTest () {
        //获取每个字符串的长度，并且返回
        Consumer<String> comsumer = System.out::println;
        Stream<String> stream = Stream.of("1", "12", "123");
        stream.forEach(comsumer);
    }

    /**
     * Predicate<T>是一个判断型接口,并且返回布尔值结果
     */
    @Test
    public void predicateTest () {
        Predicate<Integer> predicate = a -> a > 18;
        LambdaVo lambdaVo = new LambdaVo(2, "tzl");
        System.out.println(predicate.test(lambdaVo.getNum()));
    }

    /**
     * Supplier<T>是一个供给型接口,无参数，有返回结果
     */
    @Test
    public void supplierTest () {
        Supplier<Integer> supplier = () -> Integer.valueOf("666");
        System.out.println(supplier.get());
    }
}

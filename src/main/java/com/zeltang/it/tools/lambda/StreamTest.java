package com.zeltang.it.tools.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamTest {

    /**
     * 过滤操作：只有满足 Predicate 条件的元素会被保留下来，而不满足条件的元素将被过滤掉。
     */
    @Test
    public void testFilter () {

        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> filteredStream = stream.filter(n -> n % 2 == 0);
        filteredStream.forEach(System.out::println); // 输出结果: 2 4




    }


}

package com.zeltang.it.tools.lambda;

import com.zeltang.it.entity.TestEntity;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;

public class OptionalTest {

    @Test
    public void test1 () {
        TestEntity testEntity = new TestEntity("tzl", 3L);

        // "错误"的用法
        Optional<TestEntity> optionalTest = Optional.ofNullable(testEntity);
        if (optionalTest.isPresent()) {
            // do something
        }

        // 正确用法
        optionalTest.map(TestEntity::getCode).ifPresent(code -> {
            // do something
        });

    }

    @Test
    public void test2 () {
        TestEntity testEntity = new TestEntity("tzl", 3L);
        Optional<TestEntity> optionalTest = Optional.ofNullable(testEntity);

        // do something
        optionalTest.ifPresent(this::doThings);






    }

    private void doThings (TestEntity entity) {

    }


}

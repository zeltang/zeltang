package com.zeltang.it.tools.functionalInterface;

import com.zeltang.it.entity.TestEntity;
import com.zeltang.it.tools.utils.FunctionUtil;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 函数式接口
 */
public class FunctionTest {

    /**
     * Function->apply()方法：通常用于对参数进行处理，转换（处理逻辑由 Lambda 表达式实现），然后返回一个新的值
     */
    @Test
    public void functionTest () {
        // 1.数字类型
        Function<Integer, Integer> function1 = (num) -> num * 10;
        functionMethod(function1, 10);

        // 2.字符串类型
        Function<String, String> function2 = (str) -> str + ";";
        functionMethod(function2, "123");

        // 3.引用类型，这里调用String有参构造函数
        Function<String, String> function3 = String::new;
        functionMethod(function3, "456");
    }

    /**
     * Supplier->get()方法：生产型接口，如果指定了接口的泛型是什么类型，那么接口中的 get 方法就会生产什么类型的数据供我们使用
     */
    @Test
    public void supplierTest () {
        // 1
        Supplier<String> supplier1 = () -> "123";
        System.out.println(supplier1.get());

        // 2
        Supplier<TestEntity> supplier2 = TestEntity::new;
        TestEntity testEntity = supplier2.get();
        System.out.println(testEntity);

        // 3
        Supplier<String> supplier3 = testEntity::getCode;
        System.out.println(supplier3.get());
    }

    /**
     * Consumer->accept()方法：消费型接口，它消费的数据的数据类型由泛型指定
     */
    @Test
    public void consumerTest () {
//        Consumer<String> consumer1 =
    }

    @Test
    public void utilTest () {
        // 1.分支判断，不同情况做不同的处理
        if (true) {
            // do things
        } else {
            // throw Exception
        }
        // 上面可以替换为
        FunctionUtil.isTrue(true).throwMessage("error！！！");

        // 2.分支判断，不同情况做不同的处理
        if (true) {
            // do things
        } else {
            // do things
        }
        // 上面可以替换为
        FunctionUtil.isTrueOrFalse(true).trueOrFalseHandler(()->{
            // true执行此段代码 do somethings
            System.out.println("true");
        }, () -> {
            // false执行此段代码 do somethings
            System.out.println("false");
        });

        // 3
        FunctionUtil.isPresent("123").presentOrElseHandler(System.out::println, () -> {
            // false执行此段代码 do somethings
            System.out.println("false");
        });
    }




    public <T, R> void functionMethod (Function<T, R> function, T param) {
        System.out.println(function.apply(param));
    }


}

package com.zeltang.it.tools.functionalInterface;

import org.apache.poi.ss.formula.functions.T;

/**
 * 使用统一定义的接口调用之后
 */
public class RpcTestServerImpl {
    //引用微服务接口
    private TestServer testServer;

    public T test(){
        return ServerExecutor.executeAndReturn(() -> testServer.test());
    }

    public void test2(){
        ServerExecutor.execute(() -> testServer.test2());
    }
}

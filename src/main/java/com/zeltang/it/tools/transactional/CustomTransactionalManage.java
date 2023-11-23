package com.zeltang.it.tools.transactional;

import com.zeltang.it.entity.TestEntity;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 多线程-编程式事务
 */
@Log4j2
@Component
public class CustomTransactionalManage {

    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 获取连接
     * @return
     */
    public DefaultTransactionDefinition getTransactional () {
        return new DefaultTransactionDefinition();
    }

    /**
     * 执行动作 Runable 线程阻塞主线程
     *
     * @param actions 操作方法
     */
    public void executeAction (DefaultTransactionDefinition definition, List<Runnable> actions) throws Exception {

        // 事务状态
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(definition);

        // 保证原子性
        AtomicBoolean isException = new AtomicBoolean(false);

        // 多线程同步器
        CountDownLatch countDownLatch = new CountDownLatch(actions.size());
        for (Runnable runnable : actions) {
            countDownLatch.countDown();
            try {
                runnable.run();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                isException.set(true);
            }
        }
        countDownLatch.await();
        if (!isException.get()) {
            log.info("无异常，全部提交");
            dataSourceTransactionManager.commit(transactionStatus);
        } else {
            log.info("有异常，开始回滚");
            dataSourceTransactionManager.rollback(transactionStatus);
        }
    }

    /**
     * 组装动作方法
     *
     * @return
     */
    public List<Runnable> assumeActions (TestEntity entity) {
        List<Runnable> actions = new ArrayList<>();
        Runnable a = () ->{
            // 数据库操作，插入语句，例如：
            // xxxDao.insert(entity);
        };
//        Runnable b = () -> System.out.println(1/0);
        Runnable c = () -> {
            // 数据库操作，更新语句，例如：
            // xxxDao.update(entity);
        };
        actions.add(a);
//        actions.add(b);
        actions.add(c);

        return actions;
    }


}

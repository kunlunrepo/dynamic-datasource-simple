package com.tl.datasource.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
 * description : 联合事务
 *
 * @author kunlunrepo
 * date :  2023-12-28 11:39
 */
@Component
public class ComboTransaction {

    @Autowired
    private Db1TxBroker db1TxBroker;

    @Autowired
    private Db2TxBroker db2TxBroker;

    // 事务联合
    public <V> V inCombinedTx(Callable<V> callable, String[] transactions) {
        if (callable == null) {
            return null;
        }
        // 循环执行事务
        Callable<V> combined = Stream.of(transactions)
                .filter(ele -> !StringUtils.isEmpty(ele))
                .distinct()
                .reduce(callable, (r, tx) -> {
                    switch (tx) {
                        case DbTxConstants.DB1_TX:
                            return () -> db1TxBroker.inTransaction(r);
                        case DbTxConstants.DB2_TX:
                            return () -> db2TxBroker.inTransaction(r);
                        default:
                            return null;
                    }
                    }, (r1, r2) -> r2);


        try {
            return combined.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

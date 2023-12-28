package com.tl.datasource.service.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-28 11:40
 */
@Component
public class Db1TxBroker {

    @Transactional(DbTxConstants.DB1_TX)
    public <V> V inTransaction(Callable<V> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

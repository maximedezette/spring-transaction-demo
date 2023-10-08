package com.aperotech.spring.transaction.demo.bean.proxy;

public interface JDKProxy {

    void regularTransactionalMethod();

    void callTransactionalMethodFromSameInterface();

    void finalMethod();
    void callPrivateAnnotatedMethod();
    void callProtectedAnnotatedMethod();

}

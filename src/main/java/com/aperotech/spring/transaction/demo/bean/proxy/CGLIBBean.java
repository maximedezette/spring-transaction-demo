package com.aperotech.spring.transaction.demo.bean.proxy;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CGLIBBean {

    /**
     * Advanced topic Spring proxy
     */

    @Transactional
    public void regularTransactionalMethod() {

    }

    public void callTransactionalMethodFromSameClass() {
        this.regularTransactionalMethod();
    }

    @Transactional
    public final void finalMethod() {
    }

    public void callPrivateAnnotatedMethod() {
        this.privateAnnotatedMethod();
    }

    public void callProtectedAnnotatedMethod() {
        this.protectedAnnotatedMethod();
    }

    @Transactional
    private void privateAnnotatedMethod() {

    }

    @Transactional
    protected void protectedAnnotatedMethod() {

    }
}

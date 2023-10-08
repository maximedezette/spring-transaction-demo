package com.aperotech.spring.transaction.demo.bean.proxy;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JDKProxyBean implements JDKProxy {

    /**
     * Advanced topic Spring proxy
     */

    @Override
    @Transactional
    public void regularTransactionalMethod() {

    }

    @Override
    public void callTransactionalMethodFromSameInterface() {
        this.regularTransactionalMethod();
    }

    @Override
    public void callPrivateAnnotatedMethod() {
        this.privateAnnotatedMethod();
    }

    @Override
    public void callProtectedAnnotatedMethod() {
        this.protectedAnnotatedMethod();
    }

    @Transactional
    public final void finalMethod() {
    }
    @Transactional
    private void privateAnnotatedMethod() {

    }

    @Transactional
    protected void protectedAnnotatedMethod() {

    }

    @Transactional
    public void methodNotInInterface() {

    }
}

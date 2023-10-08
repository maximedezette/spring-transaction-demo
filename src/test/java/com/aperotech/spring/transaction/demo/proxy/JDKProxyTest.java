package com.aperotech.spring.transaction.demo.proxy;

import com.aperotech.spring.transaction.demo.bean.proxy.JDKProxy;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.PlatformTransactionManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class JDKProxyTest {


    @Autowired
    private JDKProxy jdkProxyBean;

    @SpyBean
    private PlatformTransactionManager transactionManager;


    /**
     * Advanced topic Spring proxy
     */

    @Test
    void isAOPproxied() {
        boolean isAopProxied = AopUtils.isAopProxy(jdkProxyBean);

        assertThat(isAopProxied).isTrue();
    }

    @Test
    void isJDKproxied() {
        boolean isProxiedByJdkDynamicProxy = AopUtils.isJdkDynamicProxy(jdkProxyBean);

        assertThat(isProxiedByJdkDynamicProxy).isTrue();
    }

    @Test
    void shouldStartTransaction() {
        jdkProxyBean.regularTransactionalMethod();

        verify(transactionManager, times(1)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }

    @Test
    void shouldNotStartTransactionCallingPublicMethodFromInsideTheProxy() {
        jdkProxyBean.callTransactionalMethodFromSameInterface();

        verify(transactionManager, times(0)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }

    @Test
    void shouldStartTransactionFromFinalMethod() {
        jdkProxyBean.finalMethod();

        verify(transactionManager, times(1)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }

    @Test
    void shouldNotStartTransactionCallingPrivateMethods() {
        jdkProxyBean.callPrivateAnnotatedMethod();

        verify(transactionManager, times(0)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }

    @Test
    void shouldNotStartTransactionCallingProtectedMethods() {
        jdkProxyBean.callProtectedAnnotatedMethod();

        verify(transactionManager, times(0)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }
}

package com.aperotech.spring.transaction.demo.proxy;

import com.aperotech.spring.transaction.demo.bean.proxy.CGLIBBean;
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
public class CGLibProxyTest {


    @Autowired
    private CGLIBBean cglibBean;

    @SpyBean
    private PlatformTransactionManager transactionManager;

    /**
     * Advanced topic Spring proxy
     */

    @Test
    void isAOPproxied() {
        boolean isAopProxied = AopUtils.isAopProxy(cglibBean);

        assertThat(isAopProxied).isTrue();
    }
    @Test
    void isCGLibProxied() {
        boolean isProxiedByCGLib = AopUtils.isCglibProxy(cglibBean);

        assertThat(isProxiedByCGLib).isTrue();
    }
    @Test
    void shouldStartTransaction() {
        cglibBean.regularTransactionalMethod();

        verify(transactionManager, times(1)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }
    @Test
    void shouldNotStartTransactionCallingPublicMethodFromInsideTheProxy() {
        cglibBean.callTransactionalMethodFromSameClass();

        verify(transactionManager, times(0)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }
    @Test
    void shouldNotStartTransactionFromFinalMethod() {
        cglibBean.finalMethod();

        verify(transactionManager, times(0)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }

    @Test
    void shouldNotStartTransactionCallingPrivateMethods() {
        cglibBean.callPrivateAnnotatedMethod();

        verify(transactionManager, times(0)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }
    @Test
    void shouldNotStartTransactionCallingProtectedMethods() {
        cglibBean.callProtectedAnnotatedMethod();

        verify(transactionManager, times(0)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }
}

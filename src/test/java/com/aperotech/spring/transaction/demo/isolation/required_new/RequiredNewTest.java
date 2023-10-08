package com.aperotech.spring.transaction.demo.isolation.required_new;

import com.aperotech.spring.transaction.demo.bean.Demo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.PlatformTransactionManager;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;

@SpringBootTest
public class RequiredNewTest {

    @Autowired
    private Demo demoBean;

    @SpyBean
    private PlatformTransactionManager transactionManager;

    @Test
    @Disabled
    void shouldStartANewTransaction() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> demoBean.requiredNew());

        Mockito.verify(transactionManager, times(1)).rollback(Mockito.any());
        Mockito.verify(transactionManager, times(0)).commit(Mockito.any());
    }
}

package com.aperotech.spring.transaction.demo.rollback;

import com.aperotech.spring.transaction.demo.bean.Demo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RollbackTest {

    @Autowired
    private Demo demoBean;

    @SpyBean
    private PlatformTransactionManager transactionManager;


    /**
     * Default rollback behavior
     */
    @Test
    void shouldRollbackTransactionForRuntimeExceptions() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> demoBean.throwRuntimeException());

        assertThatTransactionHasBeenRolledBack();
    }
    @Test
    void shouldRollbackTransactionForError() {
        assertThatExceptionOfType(Error.class)
                .isThrownBy(() -> demoBean.throwError());

        assertThatTransactionHasBeenRolledBack();
    }

    @Test
    void shouldNotRollbackForCheckedException() {
        // SQLException is a checked exception
        assertThatExceptionOfType(SQLException.class)
                .isThrownBy(() -> demoBean.throwCheckedException());

        assertThatTransactionHasBeenCommitted();
    }

    /**
     * Overriding rollback behavior
     */

    @Test
    void shouldNotRollbackForCheckedWithRollbackFor() {
        // SQLException is a CheckedException
        // the method is annotated with @Transactional(rollbackFor = SQLException.class)
        assertThatExceptionOfType(SQLException.class)
                .isThrownBy(() -> demoBean.throwCheckedExceptionSpecifyingRollbackFor());

        assertThatTransactionHasBeenRolledBack();
    }

    @Test
    void shouldNotRollbackForRuntimeExceptionWithRollBackFor() {
        // NPE is a RuntimeException
        // the method is annotated with @Transactional(noRollbackFor = NullPointerException.class)
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> demoBean.throwRuntimeExceptionSpecifyingNoRollbackFor());

        assertThatTransactionHasBeenCommitted();
    }

    private void assertThatTransactionHasBeenCommitted() {
        verify(transactionManager, times(1)).commit(any());
        verify(transactionManager, times(0)).rollback(any());
    }

    private void assertThatTransactionHasBeenRolledBack() {
        verify(transactionManager, times(0)).commit(any());
        verify(transactionManager, times(1)).rollback(any());
    }
}

package com.aperotech.spring.transaction.demo.bean;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Component
public class DemoBeanImpl implements DemoBean {

    /**
     * We could have annotated the class with @Transactional to
     * make all methods transactional.
     */
    @Override
    @Transactional
    public void requiredNew() {

    }

    @Override
    @Transactional
    public void throwRuntimeException() {
        throw new RuntimeException();
    }

    @Override
    @Transactional
    public void throwError() {
        throw new OutOfMemoryError();
    }

    @Override
    @Transactional
    public void throwCheckedException() throws SQLException {
        throw new SQLException();
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public void throwCheckedExceptionSpecifyingRollbackFor() throws SQLException {
        throw new SQLException();
    }

    @Override
    @Transactional(noRollbackFor = NullPointerException.class)
    public void throwRuntimeExceptionSpecifyingNoRollbackFor() {
        throw new NullPointerException();
    }
}

package com.aperotech.spring.transaction.demo.bean;

import java.sql.SQLException;

public interface DemoBean {

    void requiredNew();

    void throwRuntimeException();
    void throwError();

    void throwCheckedException() throws SQLException;

    void throwCheckedExceptionSpecifyingRollbackFor() throws SQLException;

    void throwRuntimeExceptionSpecifyingNoRollbackFor();
}

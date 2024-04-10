package com.etse.ft;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

// Specifies that JUnit should use the SpringRunner to run the tests.
// SpringRunner is a test runner that provides support for loading a Spring ApplicationContext
// and having test instances autowired with Spring beans.
@RunWith(SpringRunner.class)

// Declares an abstract class named BaseDaoTests. Being abstract, this class cannot be instantiated on its own.
// It's designed to be extended by other test classes that need a common testing setup, particularly for DAO (Data Access Object) testing.
public abstract class BaseDaoTests {

    // Annotates a DataSource object for automatic wiring by Spring's dependency injection facilities.
    // The DataSource provides the necessary connection to the database. Its visibility is protected,
    // allowing it to be accessed by classes that extend BaseDaoTests.
    // This dataSource is central to performing database operations in tests.
    @Autowired
    protected DataSource dataSource;

    // Annotates a method to be run after each test method in the class.
    // This is typically used for teardown operations to ensure that tests are isolated from each other.
    // Here, it's used to rollback any database transactions that were made during the test,
    // ensuring that changes made by one test do not affect others. This is crucial for maintaining
    // test independence and ensuring that the database is in a known state before each test.
    @After
    public void rollback() throws SQLException {
        // Retrieves a connection from the dataSource and performs a rollback operation.
        // This undoes all changes made during the current transaction, which is essential for
        // resetting the state of the database after each test.
        dataSource.getConnection().rollback();
    }
}


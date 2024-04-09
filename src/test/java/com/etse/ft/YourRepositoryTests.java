package com.etse.ft;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class YourRepositoryTests {

    @Autowired
   // private YourRepository repository;

    @Test
    public void testYourMethod() {
        // Perform operations and assertions
    }
}


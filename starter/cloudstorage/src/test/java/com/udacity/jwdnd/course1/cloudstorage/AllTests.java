package com.udacity.jwdnd.course1.cloudstorage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserTests.class,
        NoteTests.class,
        // add more test classes here
})
public abstract class AllTests {
}


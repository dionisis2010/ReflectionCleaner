package ru.homework4;

import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionCleanerTest {

    Map map = new HashMap();
    C c;
    B b;
    Set<String> fieldsToCleanup;
    Set<String> fieldsToOutput;
    ReflectionCleaner cleaner;

    @BeforeEach
    public void init() {
        map = new HashMap();
        map.put("fieldB2", "value1");
        map.put("fieldB3", "value4");
        map.put("fieldC2", "value2");
        map.put("fieldA2", "value3");
        map.put("fieldC4", "value5");
        map.put("fieldA1", "value6");
        map.put("fieldB1", "value7");
        map.put("fieldC3", "value8");
        map.put("fieldC1", "value9");
        map.put("fieldB4", "value10");
        map.put("ke1", "value11");
        map.put("key2", "value12");

        c = new C();
        b = new B();

        fieldsToCleanup = new HashSet<>();
        fieldsToCleanup.add("fieldB2");
        fieldsToCleanup.add("fieldC2");
        fieldsToCleanup.add("fieldA2");

        fieldsToOutput = new HashSet<>();
        fieldsToOutput.add("fieldB3");
        fieldsToOutput.add("fieldC4");
        fieldsToOutput.add("fieldB4");

        cleaner = new ReflectionCleaner();
    }

    @org.junit.jupiter.api.Test
    void cleanup() throws IllegalAccessException {
        cleaner.cleanup(map, fieldsToCleanup, fieldsToOutput);
        assertEquals(map.get("fieldB2"), null);
        assertEquals(map.get("fieldB3"), "value4");

        cleaner.cleanup(c, fieldsToCleanup, fieldsToOutput);
        assertEquals(c.fieldC2, 0);
        assertEquals(c.fieldC4, "fieldC4 value");
    }
}

class A {
    public int fieldA1 = 10;
    public String fieldA2 = "String field A2";
}

class B extends A {
    public boolean fieldB1 = true;
    public Boolean fieldB2 = false;
    public Double fieldB3 = 15.15;
    public Object fieldB4 = new Object();
}

class C extends B {
    public Integer fieldC1 = 500;
    public byte fieldC2 = 25;
    public char fieldC3 = '1';
    public String fieldC4 = "fieldC4 value";
}
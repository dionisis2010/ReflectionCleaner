package ru.homework4;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ReflectionCleanerTest {


    @Test
    public void cleanupMap() throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 10);
        map.put("parentName", "nameA");
        map.put("childName", "nameB");
        map.put("height", 15);
        map.put("privateParentField", 10.5);
        map.put("booleanField", true);
        map.put("intField", 100);
        map.put("key1", 1);
        map.put("key2", 3);
        map.put("key3", 4);

        Set<String> fieldsToCleanup = new HashSet<>();
        fieldsToCleanup.add("booleanField");
        fieldsToCleanup.add("parentName");
        fieldsToCleanup.add("height");
        Set<String> fieldsToOutput = new HashSet<>();
        fieldsToOutput.add("intField");
        fieldsToOutput.add("age");
        fieldsToOutput.add("childName");

        ReflectionCleaner cleaner = new ReflectionCleaner();

        cleaner.cleanup(map, fieldsToCleanup, fieldsToOutput);
        assertEquals(10, map.get("age"));
        assertEquals(null, map.get("booleanField"));
        assertEquals(100, map.get("intField"));
        assertEquals(null, map.get("height"));

    }

    @Test
    public void cleanup() throws IllegalAccessException {
        B b = new B();
        Set<String> fieldsToCleanup = new HashSet<>();
        fieldsToCleanup.add("booleanField");
        fieldsToCleanup.add("parentName");
        fieldsToCleanup.add("height");
        Set<String> fieldsToOutput = new HashSet<>();
        fieldsToOutput.add("intField");
        fieldsToOutput.add("age");
        fieldsToOutput.add("childName");

        ReflectionCleaner cleaner = new ReflectionCleaner();
        cleaner.cleanup(b, fieldsToCleanup, fieldsToOutput);

        assertEquals(100, b.getIntField());
        assertEquals(10, b.age);
        assertEquals("nameB", b.childName);
        assertEquals(null, b.parentName);
        assertEquals(null, b.height);
    }
}

class A {
    public int age = 10;
    public String parentName = "nameA";
    private double privateParentField = 10.5;
}

class B extends A {
    public String childName = "nameB";
    public Integer height = 15;
    private boolean booleanField = true;
    private int intField = 100;

    public boolean getBooleanField() {
        return booleanField;
    }

    public int getIntField() {
        return intField;
    }
}
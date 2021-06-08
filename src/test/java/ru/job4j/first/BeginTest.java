package ru.job4j.first;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeginTest {
    @Test
    public void whenGetI() {
        Assert.assertEquals(Begin.getI(), 1);
    }
}
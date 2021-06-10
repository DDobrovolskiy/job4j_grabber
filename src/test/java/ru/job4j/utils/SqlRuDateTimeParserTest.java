package ru.job4j.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SqlRuDateTimeParserTest {

    @Test
    public void whenParseUsual() {
        SqlRuDateTimeParser parse = new SqlRuDateTimeParser();
        Assert.assertEquals(
                parse.parse("18 май 21, 13:59"),
                LocalDateTime.of(2021, 5, 18, 13, 59));
    }

    @Test
    public void whenParseToday() {
        SqlRuDateTimeParser parse = new SqlRuDateTimeParser();
        Assert.assertEquals(
                parse.parse("сегодня, 14:48"),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 48)));
    }

    @Test
    public void whenParseTomorrow() {
        SqlRuDateTimeParser parse = new SqlRuDateTimeParser();
        Assert.assertEquals(
                parse.parse("вчера, 20:45"),
                LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(20, 45)));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenErrorDay() {
        SqlRuDateTimeParser parse = new SqlRuDateTimeParser();
        parse.parse("май 21, 13:59");
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenErrorYear() {
        SqlRuDateTimeParser parse = new SqlRuDateTimeParser();
        parse.parse("18 май, 13:59");
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenErrorTime() {
        SqlRuDateTimeParser parse = new SqlRuDateTimeParser();
        parse.parse("18 май, :59");
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenErrorMont() {
        SqlRuDateTimeParser parse = new SqlRuDateTimeParser();
        parse.parse("18 dfgfd, :59");
    }
}
package ru.job4j.utils;

import java.time.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlRuDateTimeParser implements  DateTimeParser {

    private final Pattern patternTime = Pattern.compile("(\\d{2}):(\\d{2})");
    private final Pattern patternDate = Pattern.compile("(\\d{1,2}) (\\D{3}) (\\d{2})");
    private final List<String> months = List.
            of("янв", "фев", "мар", "апр", "май", "июн", "юил", "авг", "сен", "окт", "ноя", "дек");

    public LocalDateTime parse(String parse) {
        String[] dataTime = parse.split(", ");
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = parseTime(dataTime[1]);
        if (parse.startsWith("вчера")) {
            localDate = LocalDate.now().minusDays(1);
        } else if ((!parse.startsWith("сегодня")) && (!parse.startsWith("вчера"))) {
            localDate = parseDate(dataTime[0]);
        }
        return LocalDateTime.of(localDate, localTime);
    }

    private LocalTime parseTime(String time) {
        Matcher matcherTime = patternTime.matcher(time);
        if (!matcherTime.find()) {
            throw new IllegalArgumentException();
        }
        return LocalTime.of(
                Integer.parseInt(matcherTime.group(1)),
                Integer.parseInt(matcherTime.group(2)));
    }

    private LocalDate parseDate(String date) {
        Matcher matcherDate = patternDate.matcher(date);
        if (!matcherDate.find()) {
            throw new IllegalArgumentException();
        }
        return LocalDate.of(
                Integer.parseInt(matcherDate.group(3)) + 2000,
                getMonthInt(matcherDate.group(2)),
                Integer.parseInt(matcherDate.group(1)));
    }

    private int getMonthInt(String month) {
        return months.indexOf(month) + 1;
    }
}

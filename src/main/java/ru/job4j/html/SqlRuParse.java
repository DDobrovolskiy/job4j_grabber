package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    private static String url = "https://www.sql.ru/forum/job-offers";

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(url).append("/").append(i);
            System.out.println(stringBuilder);
            Document doc = Jsoup.connect(stringBuilder.toString()).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(td.parent().child(5).text());
            }
        }
    }
}

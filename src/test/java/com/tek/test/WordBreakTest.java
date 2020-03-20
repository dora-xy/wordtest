package com.tek.test;

import com.tek.test.utli.WordBreak;
import org.junit.Test;

import java.util.List;

public class WordBreakTest {
    @Test
    public void testOne() {
        WordBreak wordBreak = WordBreak.newDefaultBuilder().build();

        List<String> result = wordBreak.wordBreak("ilikesamsungmobile");
        result.forEach(System.out::println);

        result = wordBreak.wordBreak("ilikeicecreamandmango");
        result.forEach(System.out::println);
    }

    @Test
    public void testTwo() {
        WordBreak wordBreak = WordBreak.newBuilder()
                .appendDictionary("i", "like", "sam", "sung", "mobile", "icecream", "man go", "mango")
                .build();
        List<String> result = wordBreak.wordBreak("ilikesamsungmobile");
        result.forEach(System.out::println);

        result = wordBreak.wordBreak("ilikeicecreamandmango");
        result.forEach(System.out::println);
    }

    @Test
    public void testThere() {
        WordBreak wordBreak = WordBreak.newDefaultBuilder()
                .appendDictionary("i", "like", "sam", "sung", "mobile", "icecream", "man go", "mango")
                .build();

        List<String> result = wordBreak.wordBreak("ilikesamsungmobile");
        result.forEach(System.out::println);

        result = wordBreak.wordBreak("ilikeicecreamandmango");
        result.forEach(System.out::println);
    }

    @Test
    public void testFour() {
        WordBreak wordBreak = WordBreak.newBuilder()
                .appendDictionary("i", "like", "sam", "sung", "mobile", "icecream", "man go")
                .build();

        List<String> result = wordBreak.wordBreak("ilikeicecreamandmango");
        result.forEach(System.out::println);
    }

    @Test
    public void testFive() {
        WordBreak wordBreak = WordBreak.newBuilder()
                .appendDictionary("i", "like", "sam", "sung", "samsung", "mobile", "samsungmobile", "icecream", "man go")
                .build();

        List<String> result = wordBreak.wordBreak("wholikesamsungmobile");
        result.forEach(System.out::println);
    }

    @Test
    public void testSix() {
        WordBreak wordBreak = WordBreak.newDefaultBuilder()
                .appendDictionary("i", "like", "sam", "sung", "samsung", "mobile", "samsungmobile", "ice", "cream", "icecream", "mango")
                .build();

        List<String> result = wordBreak.wordBreak("wholikesamsungmobileandicecreamandmango");
        result.forEach(System.out::println);
    }
}

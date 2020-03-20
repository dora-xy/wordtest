package com.tek.test.utli;

import java.util.*;
import java.util.stream.Collectors;

public class WordBreak {

    private Set<String> dictionary;
    private Map<String, Set<String>> realWords;
    private int mLength;

    public static class WordBreakFactory {
        //类私有化set集合
        private Set<String> dictionary;

        //私有化构造器
        private WordBreakFactory(boolean useDefault) {
            dictionary = new HashSet<>();

            //如果传入的useDefault为ture,将下面的词加到set集合中
            if (useDefault) {
                Collections.addAll(dictionary, "i","like","sam","sung","samsung","mobile","ice","cream","man go");
            }
        }

        public WordBreakFactory appendDictionary(String... words) {
            Arrays.stream(words).peek(String::trim).forEach(dictionary::add);
            return this;
        }

        public WordBreak build() {
            //构建build
            return new WordBreak(dictionary);
        }
    }

    public static WordBreakFactory newBuilder() {
        return new WordBreakFactory(false);
    }

    public static WordBreakFactory newDefaultBuilder() {
        return new WordBreakFactory(true);
    }

    private WordBreak(Set<String> dictionary) {
        this.dictionary = new HashSet<>();
        this.realWords = new HashMap<>();
        //遍历set集合
        for (String word : dictionary) {
            //将所有的空格或tab替换为空
            String newWord = word.replaceAll("\\s", "");
            //根据newWord获取value值
            Set<String> words = this.realWords.get(newWord);
            if (words != null) {
                words.add(word);
            } else if (!newWord.equals(word)) {
                words = new HashSet<>();
                words.add(word);
                this.realWords.put(newWord, words);
            }
            this.dictionary.add(newWord);
            this.mLength = Math.max(mLength, newWord.length());
        }
    }

    public List<String> wordBreak(final String sentence) {
        List<List<String>> result = wordBreak(sentence, mLength, false);
        return result.stream().map(l -> String.join(" ", l)).collect(Collectors.toList());
    }

    private List<List<String>> wordBreak(final String sentence, int max, boolean isSub) {
        List<List<String>> result = new ArrayList<>();
        if (sentence == null || sentence.isEmpty()) {
            return result;
        }

        StringBuffer mismatches = new StringBuffer();
        int len = sentence.length();
        int index = 0;
        while (index < len) {
            Set<String> nodes = new HashSet<>();
            int upper = Math.min(len, index + max);
            String sub = sentence.substring(index, upper);
            int subLen = sub.length();
            boolean mismatch;
            while (mismatch = !dictionary.contains(sub)) {
                if (subLen == 1) {
                    break;
                }
                sub = sub.substring(0, subLen - 1);
                subLen = sub.length();
            }
            if (mismatch && isSub) {
                result.clear();
                return result;
            }
            if (subLen > 1) {
                List<List<String>> r = wordBreak(sub, subLen - 1, true);
                r.stream().filter(l -> !l.isEmpty()).map(l -> String.join(" ", l)).forEach(nodes::add);
            }
            if (mismatch) {
                mismatches.append(sub);
            } else {
                if (mismatches.length() > 0) {
                    result.add(Arrays.asList(mismatches.toString()));
                    mismatches.setLength(0);
                }
                Set<String> words = realWords.get(sub);
                if (words != null) {
                    nodes.addAll(words);
                } else {
                    nodes.add(sub);
                }
                result.add(new ArrayList<>(nodes));
            }
            index += subLen;
        }
        if (mismatches.length() > 0) {
            result.add(Arrays.asList(mismatches.toString()));
        }
        return this.descartes(result);
    }

    public static List<List<String>> descartes(List<List<String>> sourceList) {
        List<List<String>> targetList =  new ArrayList<>();
        descartes(sourceList, targetList, new Stack<>(), 0);
        return targetList;
    }

    private static void descartes(List<List<String>> sourceList, List<List<String>> targetList, Stack<String> stack, int layer) {
        boolean isEnd = layer == sourceList.size() - 1;
        sourceList.get(layer).forEach(str -> {
            stack.push(str);
            if (isEnd) {
                targetList.add(new ArrayList<>(stack));
            } else {
                descartes(sourceList, targetList, stack, layer + 1);
            }
            stack.pop();
        });
    }
}
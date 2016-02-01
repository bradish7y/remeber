package com.xzm.leetcode.longest_length_substring;

/**
 * Created by Bradish7Y on 15/6/30.
 * <p/>
 * Given a string, find the length of the longest substring without repeating characters.
 * <p/>
 * For example, the longest substring without repeating letters for "abcabcbb" is "abc",
 * which the length is 3.
 * <p/>
 * For "bbbbb" the longest substring is "b", with the length of 1.
 */
public class Longest {
    public static void main(String[] args) {
        Longest l = new Longest();
        int length = l.lengthOfLongestSubstring("abcb");
        System.out.println("length = " + length);

    }

    /**
     * 1.从第一个字节开始找第二次出现的位置pos
     * 2.截取字符串0-pos
     * 3.重复1~3，直到最后一个重复的
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {

        int i = 0;
        for (; ; ) {
            if (i == s.length()) {
                break;
            }
            int endPos = s.indexOf(s.charAt(i), i + 1);
            if (endPos > 0) {
                s = s.substring(0, endPos);
            }
            i++;
        }

        System.out.println("string:" + s);
        return s.length();
    }
}

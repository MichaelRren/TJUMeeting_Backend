package com.ictwsn.util;
 
import java.util.Random;
/**
 * 随机字符串生成器类
 * @author yanan
 *
 */
public class RandomString {
	private static Random rand=new Random();
    public static final String SOURCES ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
 
    /**
     * Generate a random string.
     *
     * @param random the random number generator.
     * @param characters the characters for generating string.
     * @param length the length of the generated string.
     * @return
     */
    public static String generateString(int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(rand.nextInt(62));
        }
        return new String(text);
    }
}
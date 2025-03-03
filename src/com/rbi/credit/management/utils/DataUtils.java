package com.rbi.credit.management.utils;

import java.util.Random;

public class DataUtils {
    public static Random random = new Random();

    public static long getRandomSixteenDigitNumber(){
        return 100000000000L + (long)(DataUtils.random.nextDouble() * 900000000000L);
    }

    public static int getRandomThreeDigits(){
        return 100 + random.nextInt(900);
    }

    public static int getRandomFourDigits() {
        return 1000 + random.nextInt(9000);
    }
}

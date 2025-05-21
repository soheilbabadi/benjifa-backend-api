package club.benjifa.benjifa_backend_api.utils;


import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class Strutting {


    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+=-][';.,?><|:";

    private Strutting() {
    }

    public static String generateRandom(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String generateRandomNumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generateRandomSecure() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            sb.append(ALPHANUMERIC.charAt(secureRandom.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }

    public static int generateRandomNumber(int min, int max) {
        return secureRandom.nextInt((max - min) + 1) + min;
    }

}

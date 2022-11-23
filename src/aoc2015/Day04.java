package aoc2015;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day04 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(problem1("yzbqklnj"));
        System.out.println(problem2("yzbqklnj"));
    }

    public static int problem1(String input) throws NoSuchAlgorithmException {
        int counter = 0;
        String temp;

        while (true) {
            temp = input + counter;
            BigInteger hash = getMD5Hash(temp);
            String hashString = String.format("%032X", hash);

            //System.out.printf("%d -> %d:%s\n", counter, hash, hashString);
            if (hashString.substring(0, 5).equals("00000")) {
                return counter;
            }
            counter++;
        }
    }

    public static int problem2(String input) throws NoSuchAlgorithmException {
        int counter = 0;
        String temp;

        while (true) {
            temp = input + counter;
            BigInteger hash = getMD5Hash(temp);
            String hashString = String.format("%032X", hash);

            //System.out.printf("%d -> %d:%s\n", counter, hash, hashString);
            if (hashString.substring(0, 6).equals("000000")) {
                return counter;
            }
            counter++;
        }
    }

    public static BigInteger getMD5Hash(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return new BigInteger(1, md.digest(string.getBytes()));
    }
}

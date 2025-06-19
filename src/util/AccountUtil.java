package util;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class AccountUtil {

    public static String generateAccountNumber(String bankName, int branchId) {
        String abbreviation = Arrays.stream(bankName.trim().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase())
                .collect(Collectors.joining());

        int randomPart = new Random().nextInt(90000) + 10000;

        return abbreviation + branchId + randomPart;
    }
}

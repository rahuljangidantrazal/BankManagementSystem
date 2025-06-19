package util;

import repo.BranchRepo;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class IFSCUtil {

    public static String generateUniqueIFSC(String bankName, BranchRepo branchRepo) {
        String abbreviation = Arrays.stream(bankName.trim().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase())
                .collect(Collectors.joining());

        String ifsc;
        int random = new Random().nextInt(90000) + 10000;
        ifsc = abbreviation + random;

        return ifsc;
    }
}

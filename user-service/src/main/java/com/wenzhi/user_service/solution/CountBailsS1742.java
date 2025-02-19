package com.wenzhi.user_service.solution;

import java.util.HashMap;

public class CountBailsS1742 {
    public int countBails(int lowLimit, int highLimit) {
        int n = highLimit - lowLimit + 1;
        int result = 0;
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int i=lowLimit; i<=highLimit; i++) {
            int s = sum(i);
            count.put(s, count.getOrDefault(s, 0) + 1);
            result = Math.max(result, count.get(s));
        }
        // count.values().stream().mapToInt(Integer::intValue).max().getAsInt();
        return result;
    }

    public int sum(int x) {
        int number = 0;
        while (x > 0) {
            int a = x % 10;
            number += a;
            x = x / 10;
        }
        return number;
    }
}

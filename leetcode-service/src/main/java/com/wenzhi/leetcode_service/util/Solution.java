package com.wenzhi.leetcode_service.util;


import java.util.*;

public class Solution {
    // 方法一：排序 + 二重循环枚举
    public int numberOfPairs(int[][] points) {
        int ans = 0;
        Arrays.sort(points, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        for (int i = 0; i < points.length - 1; i++) {
            int[] pointA = points[i];
            int xMin = pointA[0] - 1;
            int xMax = Integer.MAX_VALUE;
            int yMin = Integer.MIN_VALUE;
            int yMax = pointA[1] + 1;

            for (int j = i + 1; j < points.length; j++) {
                int[] pointB = points[j];
                if (pointB[0] > xMin && pointB[0] < xMax &&
                        pointB[1] > yMin && pointB[1] < yMax) {
                    ans++;
                    xMin = pointB[0];
                    yMin = pointB[1];
                }
            }
        }
        return ans;
    }

}


// 方法二：二维前缀和 + 离散化
class Solution2 {
    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public int numberOfPairs(int[][] points) {
        Map<Integer, Integer> col = new HashMap<>();
        Map<Integer, Integer> row = new HashMap<>();
        Map<Point, int[]> coordinatesMap = new HashMap<>();

        for (int[] point : points) {
            int x = point[0], y = point[1];
            col.put(x, 0);
            row.put(y, 0);
        }
        List<Integer> colKeys = new ArrayList<>(col.keySet());
        Collections.sort(colKeys);
        for (int i = 0; i < colKeys.size(); i++) {
            col.put(colKeys.get(i), i + 1);
        }
        List<Integer> rowKeys = new ArrayList<>(row.keySet());
        Collections.sort(rowKeys);
        for (int i = 0; i < rowKeys.size(); i++) {
            row.put(rowKeys.get(i), i + 1);
        }

        int nc = col.size() + 1;
        int nr = row.size() + 1;
        int[][] m = new int[nc][nr];
        int[][] prefixSum = new int[nc][nr];

        for (int[] point : points) {
            int x = point[0], y = point[1];
            int c = col.get(x), r = row.get(y);
            Point key = new Point(x, y);
            coordinatesMap.put(key, new int[]{c, r});
            m[c][r] = 1;
        }
        for (int i = 1; i < nc; i++) {
            for (int j = 1; j < nr; j++) {
                prefixSum[i][j] = prefixSum[i - 1][j] + prefixSum[i][j - 1]
                        - prefixSum[i - 1][j - 1] + m[i][j];
            }
        }

        int ans = 0;
        Arrays.sort(points, (a, b) -> {
            if (a[0] == b[0]) {
                return Integer.compare(b[1], a[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        int n = points.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (points[i][1] >= points[j][1]) {
                    Point key1 = new Point(points[i][0], points[i][1]);
                    Point key2 = new Point(points[j][0], points[j][1]);
                    int[] coord1 = coordinatesMap.get(key1);
                    int[] coord2 = coordinatesMap.get(key2);
                    int c1 = coord1[0], r1 = coord1[1];
                    int c2 = coord2[0], r2 = coord2[1];
                    int cnt = prefixSum[c2][r1] - prefixSum[c1 - 1][r1]
                            - prefixSum[c2][r2 - 1] + prefixSum[c1 - 1][r2 - 1];
                    if (cnt == 2) {
                        ans++;
                    }
                }
            }
        }

        return ans;
    }
}


/*
*  Weeping for My Daughter
* My daughter smokes. While she is doing her homework, her feet on the bench in front of her and her
* calculator clicking out answers to her geometry problems, I am looking at the half-empty package of
* Camels tossed carelessly at hand. I pick them up, take them into the kitchen, where the light is better,
* and study them—they're filtered, for which I am grateful. My heart feels terrible. I want to weep. In
* fact, I do weep a little, standing there by the stove holding one of the instruments, so while, so
* precisely rolled, that could cause my daughter's death. When she smoked Marlboros and Players I hardened
* myself against feeling so bad; nobody I knew ever smoked these brands.–  — –  -
* */

class Solution3 {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        Integer[] a = nums.toArray(Integer[]::new); // 转成数组处理，更快
        int ans = 0;
        int preCnt = 0;
        int cnt = 0;
        for (int i = 0; i < a.length; i++) {
            cnt++;
            // i 是严格递增段的末尾
            if (i == a.length - 1 || a[i] >= a[i + 1]) {
                ans = Math.max(ans, Math.max(cnt / 2, Math.min(preCnt, cnt)));
                preCnt = cnt;
                cnt = 0;
            }
        }
        return ans;
    }
}

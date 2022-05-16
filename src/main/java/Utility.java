
public class Utility {

    public interface FetchValue<T> {
        String getValue(T t);
    }

    public static <T> void dump(final T[][] t, FetchValue fetchValue) {
        if (null == t) {
            return;
        }
        int m = t.length;
        if (m == 0) {
            return;
        }
        int n = t[0].length;
        if (n == 0) {
            return;
        }
        for (int i = m - 1; i >= 0; -- i) {
            for (int j = 0; j < n; ++ j) {
                System.out.print(String.format("%5s", fetchValue.getValue(t[i][j])));
            }
            System.out.println();
        }
    }

    public static void dump(final double[][] t) {
        if (null == t) {
            return;
        }
        int m = t.length;
        if (m == 0) {
            return;
        }
        int n = t[0].length;
        if (n == 0) {
            return;
        }
        for (int i = m - 1; i >= 0; -- i) {
            for (int j = 0; j < n; ++ j) {
                System.out.print(String.format("%5.2f", t[i][j]));
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void dump(final double[] t) {
        if (null == t) {
            return;
        }
        int m = t.length;
        if (m == 0) {
            return;
        }
        for (int i = 0; i < m; ++ i) {
                System.out.print(String.format("%5.2f", t[i]));
            }
            System.out.println();
    }

    public static void dump(final int[][] t, FetchValue fetchValue) {
        if (null == t) {
            return;
        }
        int m = t.length;
        if (m == 0) {
            return;
        }
        int n = t[0].length;
        if (n == 0) {
            return;
        }
        for (int i = m - 1; i >= 0; -- i) {
            for (int j = 0; j < n; ++ j) {
                System.out.print(String.format("%5s", fetchValue.getValue(t[i][j])));
            }
            System.out.println();
        }
    }

    public static void dump(final int[] t) {
        if (t == null || t.length == 0) {
            return;
        }
        for (int i = 0; i < t.length; ++ i) {
            System.out.print(String.format("%5s", Integer.toString(t[i])));
        }
        System.out.println();
    }

    public static <T> void dump(final T[] t, FetchValue fetchValue) {
        if (t == null || t.length == 0) {
            return;
        }
        for (int i = 0; i < t.length; ++ i) {
            System.out.print(String.format("%12s", fetchValue.getValue(t[i])));
        }
        System.out.println();
    }

    public static boolean isEmpty(final String str) {
        return null == str || str.length() == 0;
    }

}

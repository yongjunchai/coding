
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

}

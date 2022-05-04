
public class Utility {

    public interface FetchValue<T> {
        String getValue(T t);
    }

    public static <T> void dump(final T[][] t, FetchValue fetchValue) {
        int m = t.length;
        int n = t[0].length;
        for (int i = m - 1; i >= 0; -- i) {
            for (int j = 0; j < n; ++ j) {
                System.out.print(String.format("%8s", fetchValue.getValue(t[i][j])));
            }
            System.out.println();
        }
    }
}

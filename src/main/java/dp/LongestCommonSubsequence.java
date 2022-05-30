package dp;

public class LongestCommonSubsequence {

    private boolean isEmpty(final char[] str) {
        return null == str || str.length == 0;
    }

    public static class Note {
        public boolean equal;
        public int subsequenceLen;
        static Note create() {
            Note note = new Note();
            note.equal = false;
            note.subsequenceLen = 0;
            return  note;
        }
    }

    public static class Result {
        public String commonSubsequence;
        public Note[][] notes;

        static public Result create() {
            Result result = new Result();
            result.commonSubsequence = "";
            result.notes = null;
            return result;
        }
    }

    public Result findLongestCommonSubsequence(final char[] left, final char[] right) {
        if (isEmpty(left) || isEmpty(right)) {
            return Result.create();
        }
        int leftLen = left.length;
        int rightLen = right.length;
        Note[][] notes = new Note[leftLen + 1][rightLen + 1];
        for (int i = 0; i <= leftLen; ++ i) {
            notes[i][0] = Note.create();
        }
        for (int j = 0; j <= rightLen; ++ j) {
            notes[0][j] = Note.create();
        }
        for (int i = 1; i <= leftLen; ++ i) {
            for (int j = 1; j <= rightLen; ++ j) {
                Note note = Note.create();
                if (left[i - 1] == right[j - 1]) {
                    note.equal = true;
                    note.subsequenceLen = notes[i - 1][j - 1].subsequenceLen + 1;
                }
                else {
                    if (notes[i - 1][j].subsequenceLen > notes[i][j - 1].subsequenceLen) {
                        note.subsequenceLen = notes[i - 1][j].subsequenceLen;
                    }
                    else {
                        note.subsequenceLen = notes[i][j - 1].subsequenceLen;
                    }
                }
                notes[i][j] = note;
            }
        }
        int lcsLen = notes[leftLen][rightLen].subsequenceLen;
        if (lcsLen == 0) {
            Result result = Result.create();
            result.commonSubsequence = "";
            result.notes = notes;
            return result;
        }
        //reconstruct longest common subsequence
        char[] lcs = new char[lcsLen];
        int index = lcsLen - 1;
        int i = leftLen;
        int j = rightLen;
        while (i > 0 && j > 0 && index >= 0) {
            Note note = notes[i][j];
            if (note.equal) {
                lcs[index] = left[i - 1];
                i -= 1;
                j -= 1;
                index -= 1;
            }
            else {
                if (notes[i - 1][j].subsequenceLen > notes[i][j - 1].subsequenceLen) {
                    i -= 1;
                }
                else {
                    j -= 1;
                }
            }
        }
        Result result = Result.create();
        result.commonSubsequence = new String(lcs);
        result.notes = notes;
        return result;
    }
}

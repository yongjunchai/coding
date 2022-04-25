
public class LongestCommonSubstring {

    private boolean isEmpty(final char[] str) {
        return null == str || str.length == 0;
    }
    private static class Note {
        public boolean equal;
        public int substringLen;
        static Note create() {
            Note note = new Note();
            note.equal = false;
            note.substringLen = 0;
            return  note;
        }
    }

    String longestCommonSubstring(final char[] left, final char[] right) {
        if (isEmpty(left) || isEmpty(right)) {
            return "";
        }
        int leftLen = left.length;
        int rightLen = right.length;
        Note[][] notes = new Note[leftLen + 1][rightLen + 1];
        for (int i = 0; i <= leftLen; ++ i) {
            Note note = Note.create();
            notes[i][0] = note;
        }
        for (int j = 0; j <= rightLen; ++ j) {
            Note note = Note.create();
            notes[0][j] = note;
        }
        int lcsLen = 0;
        int iLcs = -1;
        int jLcs = -1;
        for (int i = 1; i <= leftLen; ++ i) {
            for (int j = 1; j <= rightLen; ++ j) {
                Note note = Note.create();
                if(left[i - 1] == right[j - 1]) {
                    note.equal = true;
                    note.substringLen = notes[i - 1][j - 1].substringLen + 1;
                    if (note.substringLen > lcsLen) {
                        lcsLen = note.substringLen;
                        iLcs = i;
                        jLcs = j;
                    }
                }
                notes[i][j] = note;
            }
        }
        if (lcsLen == 0) {
            return "";
        }
        //reconstruct the longest substring
        char[] lcs  = new char[lcsLen];
        int i = iLcs;
        int j = jLcs;
        int index = lcsLen - 1;
        while (i > 0 && j > 0) {
            if (! notes[i][j].equal) {
                break;
            }
            lcs[index] = left[i - 1];
            index -= 1;
            i -= 1;
            j -= 1;
        }
        if (index != -1) {
            System.out.println("error detected. failed to fetch lcs");
        }
        return new String(lcs);
    }
}

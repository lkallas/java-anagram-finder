
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Lennar Kallas
 */
public class AnagramFinder {

    private static final StringBuilder RESULT = new StringBuilder();

    /**
     * IMPORTANT: Expects input parameters to be same length and in lowercase.
     *
     * @param a Lowercase string
     * @param b Lowercase string
     * @return
     */
    public static boolean areAnagrams(String a, String b) {

        int[] charFrequencyCount = new int[500];

        int differencesCount = 0;

        for (int i = 0; i < a.length(); i++) {

            int charCodeA = (int) a.charAt(i);
            if (charFrequencyCount[charCodeA] >= 0) {
                differencesCount++;
            } else {
                differencesCount--;
            }
            charFrequencyCount[charCodeA]++;

            int charCodeB = (int) b.charAt(i);
            if (charFrequencyCount[charCodeB] <= 0) {
                differencesCount++;
            } else {
                differencesCount--;
            }
            charFrequencyCount[charCodeB]--;
        }

        return differencesCount == 0;
    }

    public static void main(String[] args) throws Exception {

        long start = System.nanoTime();

        if (args.length != 2) {
            System.out.println("Invalid arguments!\nUsage: [application] [path/to/dictionary/file] [word]");
            System.exit(-1);
        }

        String dictionaryFilePath = args[0];
        String word = args[1].toLowerCase();
        int wordLength = word.length();

        char[] chars = word.toCharArray();

        Arrays.sort(chars);
        String sortedWord = new String(chars);

        int sortedWordLastCharCode = (int) sortedWord.charAt(wordLength - 1);
        int sortedWordFirstCharCode = (int) sortedWord.charAt(0);

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFilePath), "cp1257"));

            String line;

            while ((line = bf.readLine()) != null) {

                int lineLength = line.length();

                // Skipping words that do not have the same length. Also skip identical words as they are not anagrams
                if (wordLength == lineLength && !line.equalsIgnoreCase(word)) {

                    String lineLowercase = line.toLowerCase();
                    int lineFirstCharCode = (int) lineLowercase.charAt(0);

                    if (lineFirstCharCode < sortedWordFirstCharCode || lineFirstCharCode > sortedWordLastCharCode) {
                        continue; // Skipping words that do not start with expected characters
                    }

                    // Check if anagram
                    if (areAnagrams(sortedWord, lineLowercase)) {
                        RESULT.append(",");
                        RESULT.append(line);
                    }
                }
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }

        long end = System.nanoTime();
        long elapsedTime = (end - start) / 1000;
        System.out.println(elapsedTime + "" + RESULT.toString());
    }
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CountLongWords {
    public static void main(String[] args) throws IOException {
        final String PATH = "D:/Jimmy/JAVA/Core Java 2/src/main/resources/alice.txt";

        // Read file into string
        String contents = Files.readString(Paths.get(PATH));

        //Split into words; non-letters are delimiters
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        long count = 0;

        for (String w :
                words) {
            if (w.length() > 12) count++;
        }
        System.out.println(count);

        count = words.stream().filter(w -> w.length() > 12).count();
        System.out.println(count);

        count = words.parallelStream().filter(w -> w.length() > 12).count();
        System.out.println(count);


    }
}

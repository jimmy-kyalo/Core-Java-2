import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ParallelStreams {
    final static String PATH = "D:/Jimmy/JAVA/Core Java 2/src/main/resources/alice.txt";

    public static void main(String[] args) throws IOException {
        String contents = Files.readString(Paths.get(PATH));
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));

        // bad code

    }
}

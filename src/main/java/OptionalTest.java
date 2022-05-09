import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OptionalTest {
    final static String PATH = "D:/Jimmy/JAVA/Core Java 2/src/main/resources/alice.txt";

    public static void main(String[] args) throws IOException {
        // Read file into string
        String contents = Files.readString(Paths.get(PATH));

        //Split into words; non-letters are delimiters
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));

        Optional<String> optionalValue  = wordList.parallelStream().filter(s -> s.contains("fred")).findAny();
        System.out.println(optionalValue.orElse("No word") + " contains fred");

        Optional<String> optionalString = Optional.empty();
        String result = optionalString.orElse("N/A");
        System.out.println("result: " + result);
        result = optionalString.orElseGet(()-> Locale.getDefault().getDisplayName());
        System.out.println("result: " + result);

//        try {
//            result = optionalString.orElseThrow(IllegalStateException::new);
//            System.out.println("result: " + result);
//        } catch (Throwable t){
//            t.printStackTrace();
//        }

        optionalValue = wordList.parallelStream().filter(s -> s.contains("red")).findAny();
        optionalValue.ifPresent(s -> System.out.println(s + " contains red"));

        Set<String> results = new HashSet<>();
        optionalValue.ifPresent(results::add);
        Optional<Boolean> added = optionalValue.map(results::add);
        System.out.println(added);

        System.out.println(inverse(4.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(-1.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(0.0).flatMap(OptionalTest::squareRoot));

        Optional<Double> result2 = Optional.of(4.0).flatMap(OptionalTest::inverse).flatMap(OptionalTest::squareRoot);
        System.out.println(result2);
    }

    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}
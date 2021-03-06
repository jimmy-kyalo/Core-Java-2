import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class DownstreamCollectors {
    static final String PATH = "D:/Jimmy/JAVA/Core Java 2/src/main/resources/cities.txt";

    public static class City {
        private final String name;
        private final String state;
        private final int population;

        public City(String name, String state, int population) {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public int getPopulation() {
            return population;
        }
    }

    public static Stream<City> readCities(String filename) throws IOException {
        return Files.lines(
                        Paths.get(filename))
                .map(l -> l.split(","))
                .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
    }

    public static void main(String[] args) throws IOException {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<Locale>> countryToLocaleSet = locales.collect(
                groupingBy(
                        Locale::getCountry, toSet()
                )
        );
        System.out.println("countryToLocaleSet: " + countryToLocaleSet);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Long> countryToLocaleCounts = locales.collect(
                groupingBy(
                        Locale::getCountry, counting()
                )
        );
        System.out.println("countryToLocaleCounts: " + countryToLocaleCounts);

        Stream<City> cities = readCities(PATH);
        Map<String, Integer> stateToCityPopulation = cities.collect(
                groupingBy(
                        City::getState, summingInt(City::getPopulation)
                )
        );
        System.out.println("stateToCityPopulation: " + stateToCityPopulation);

        cities = readCities(PATH);
        Map<String, Optional<String>> stateToLongestCityName = cities.collect(
                groupingBy(
                        City::getState,
                        mapping(
                                City::getName,
                                maxBy(Comparator.comparing(String::length))
                        )
                )
        );
        System.out.println("stateToLongestCityName: " + stateToLongestCityName);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<String>> countryToLanguages = locales.collect(
                groupingBy(
                        Locale::getDisplayCountry,
                        mapping(Locale::getDisplayLanguage, toSet())
                )
        );
        System.out.println("countryToLanguages: " + countryToLanguages);

        cities = readCities(PATH);
        Map<String, IntSummaryStatistics> stateToCityPopulationSummary = cities.collect(
                groupingBy(
                        City::getState,
                        summarizingInt(City::getPopulation)
                )
        );
        System.out.println("stateToCityPopulationSummary: " + stateToCityPopulationSummary);

        cities = readCities(PATH);
        Map<String, String> stateToCityNames = cities.collect(
                groupingBy(
                        City::getState,
                        reducing("", City::getName, (s, t) -> s.length() == 0 ? t : s + ", " + t)
                )
        );
        System.out.println("stateToCityNames: " + stateToCityNames);

        cities = readCities(PATH);
        stateToCityNames = cities.collect(
                groupingBy(
                        City::getState,
                        mapping(City::getName, joining(", "))
                )
        );
        System.out.println("stateToCityNames: " + stateToCityNames);
    }
}

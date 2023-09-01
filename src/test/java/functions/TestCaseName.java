package functions;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCaseName {
    public static String convert(String testCaseName) {
        // Read the test case method name and split it based on capital letters
        Stream<String> convertTestMethodName = Arrays.stream(testCaseName.split("(?=\\p{Lu})"));

        // Capitalise the first letter of each word in test case name
        Stream<String> testNameCapitalize = convertTestMethodName.map(StringUtils::capitalize);

        // Join words in test case name with a space between each
        return testNameCapitalize.collect(Collectors.joining(" "));
    }
}

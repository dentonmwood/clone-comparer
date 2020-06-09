package source;

import java.util.stream.Stream;

public class SourceTest {
    protected Stream<String> getValidFilenames() {
        return Stream.of("", "a", "abca;sd", "skrjoiwenfowieoifj", "ß∂ƒ©˙∆˚¬");
    }

    protected Stream<Long> getValidLineNumbers() {
        return Stream.of(1L, 2L, 324L, Long.MAX_VALUE);
    }

    protected Stream<Long> getInvalidLineNumbers() {
        return Stream.of(0L, -1L, -2342L, Long.MIN_VALUE);
    }
}

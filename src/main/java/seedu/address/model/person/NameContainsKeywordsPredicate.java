package seedu.address.model.person;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.CosineNGramUtil;
import seedu.address.commons.util.FuzzySimilarityUtil;
import seedu.address.commons.util.LevenshteinDistanceUtil;
import seedu.address.commons.util.SimilarityMetric;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final FuzzySimilarityUtil fuzzyUtil;

    /**
     * Constructs a predicate with a custom similarity threshold.
     * @param keywords  The list of keywords to match
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        List<SimilarityMetric> metrics = List.of(
                new LevenshteinDistanceUtil(),
                new CosineNGramUtil(2)
        );
        this.fuzzyUtil = new FuzzySimilarityUtil(metrics);
    }

    @Override
    public boolean test(Person person) {
        String[] name = person.getName().fullName.toLowerCase().split("\\s+");
        return keywords.stream()
                .map(String::toLowerCase)
                .anyMatch(keyword -> {
                    String normalizedKeyword = keyword.toLowerCase();
                    double dynamicThreshold = getThreshold(
                            Math.min(keyword.length(), normalizedKeyword.length()));
                    return Arrays.stream(name)
                            .anyMatch(word ->
                                    word.contains(normalizedKeyword)
                                            || fuzzyUtil.isSimilar(word, normalizedKeyword,
                                            dynamicThreshold));
                });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    /**
     * Returns a similarity threshold for fuzzy matching
     * based on the length of the word.
     * <p>
     * Short words are given a lower threshold to allow for
     * more leniency in fuzzy matches,
     * while longer words use a stricter threshold to reduce false positive.
     *
     * <ul>
     *     <li>Words of length 3 or less: threshold are set to be more lenient to 0.5</li>
     *     <li>Words of length long than 3: threshold are set to be stricter to 0.8</li>
     * </ul>
     *
     * @param minWordLength Length of the shorter of the two words being compared
     * @return the similarity threshold in the range [0.0, 1.0] for use in fuzzy search
     */
    private static double getThreshold(int minWordLength) {
        if (minWordLength <= 3) {
            return 0.5;
        }
        return 0.8;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

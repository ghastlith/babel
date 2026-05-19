package ghastlith.babel.generation;

import static ghastlith.babel.generation.CharacterSet.LETTERS;
import static ghastlith.babel.generation.CharacterSet.NUMBERS;
import static ghastlith.babel.generation.CharacterSet.SPECIAL;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Manages and performs all password generation related operations.
 */
@Component
@Validated
@RequiredArgsConstructor
public class GenerationEngine {

  private final SecureRandom random;

  private static final int RANDOM_LOWER_BOUND = 0;

  /**
   * Generate random sets of characters based on the {@link GenerationPolicy}
   * rules and then shuffle them based on currently built entropy present on
   * {@link SecureRandom}.
   *
   * @param policy the set of rules to generate a password
   * @return The generated password string.
   */
  public String generatePassword(@Valid final GenerationPolicy policy) {
    final var letters = getRandomUnits(LETTERS, policy.lettersLength());
    final var numbers = getRandomUnits(NUMBERS, policy.numbersLength());
    final var special = getRandomUnits(SPECIAL, policy.specialLength());

    final var password = mergeAndShuffle(letters, numbers, special);

    return password;
  }

  private String getRandomUnits(final CharacterSet set, final int length) {
    final var characters = set.getCharacters();
    final var upperBound = characters.length();

    return random.ints(RANDOM_LOWER_BOUND, upperBound)
        .limit(length)
        .mapToObj(characters::charAt)
        .map(String::valueOf)
        .collect(joining());
  }

  private String mergeAndShuffle(final String... parts) {
    final var characters = Arrays.stream(parts)
        .collect(joining())
        .chars()
        .mapToObj(c -> (char) c)
        .collect(toList());

    Collections.shuffle(characters, random);

    return characters.stream()
        .map(String::valueOf)
        .collect(joining());
  }

}

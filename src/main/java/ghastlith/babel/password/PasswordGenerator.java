package ghastlith.babel.password;

import static ghastlith.babel.password.CharacterSet.allCharacters;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * Manages and performs all password generation related operations.
 */
@Component
@Validated
public class PasswordGenerator {

  private final SecureRandom random = new SecureRandom();

  private static final int RANDOM_LOWER_BOUND = 0;

  /**
   * Generate random sets of characters based on the {@link PasswordPolicy} rules
   * and then shuffle them based on currently built entropy present on
   * {@link SecureRandom}.
   *
   * @param policy the set of rules to generate a password
   * @return The generated password string.
   */
  public String generate(@Valid final PasswordPolicy policy) {
    final var requiredCharacters = policy.minimumPerCharacterSet()
        .entrySet()
        .stream()
        .map(this::getRandomCharacters)
        .collect(joining());

    final var unreservedLength = policy.length() - requiredCharacters.length();
    final var unreservedSet = allCharacters(policy.hasSymbols());
    final var unreservedCharacters = getRandomCharacters(unreservedSet, unreservedLength);

    final var password = mergeAndShuffle(requiredCharacters, unreservedCharacters);

    return password;
  }

  private String getRandomCharacters(final Entry<CharacterSet, Integer> entry) {
    final var characters = entry.getKey().getCharacters();
    final var length = entry.getValue();

    return getRandomCharacters(characters, length);
  }

  private String getRandomCharacters(final String characters, final int length) {
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

    shuffle(characters, random);

    return characters.stream()
        .map(String::valueOf)
        .collect(joining());
  }

}

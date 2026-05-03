package ghastlith.passwordgenerator.generation;

import static ghastlith.passwordgenerator.generation.CharacterSet.LETTERS;
import static ghastlith.passwordgenerator.generation.CharacterSet.NUMBERS;
import static ghastlith.passwordgenerator.generation.CharacterSet.SPECIAL;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Component
@Validated
@RequiredArgsConstructor
public class GenerationEngine {

  private final SecureRandom random;

  private static final int RANDOM_LOWER_BOUND = 0;

  public String generatePassword(@Valid final GenerationPolicy policy) {
    final var letters = getRandomCharacters(LETTERS, policy.lettersLength());
    final var numbers = getRandomCharacters(NUMBERS, policy.numbersLength());
    final var special = getRandomCharacters(SPECIAL, policy.specialLength());

    final var password = shuffleCharacters(letters, numbers, special);

    return password;
  }

  private String getRandomCharacters(final String set, final int length) {
    final var upperBound = set.length();

    return random.ints(RANDOM_LOWER_BOUND, upperBound)
        .limit(length)
        .mapToObj(set::charAt)
        .map(String::valueOf)
        .collect(joining());
  }

  private String shuffleCharacters(final String... parts) {
    final var characters = Arrays.stream(parts)
        .collect(joining())
        .chars()
        .mapToObj(c -> (char) c)
        .collect(toList());

    Collections.shuffle(characters);

    return characters.stream()
        .map(String::valueOf)
        .collect(joining());
  }

}

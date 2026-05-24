package ghastlith.babel.password;

import static ghastlith.babel.password.CharacterSet.LOWER_CASE;
import static ghastlith.babel.password.CharacterSet.NUMBERS;
import static ghastlith.babel.password.CharacterSet.SPECIAL;
import static ghastlith.babel.password.CharacterSet.UPPER_CASE;

import java.util.Map;

import ghastlith.babel.argument.Arguments;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

/**
 * The policy used when generating a new random password.
 */
@Builder
public record PasswordPolicy(
    @Min(value = 16, message = "Length must be at least 16")
    @Max(value = 32, message = "Length must be at most 32")
    int length,
    boolean hasSymbols,
    Map<CharacterSet, Integer> minimumPerCharacterSet
) {

  private static final Integer MINIMUM_SECTION_LENGTH = 1;
  private static final Integer DISABLED_LENGTH = 0;

  /**
   * Constructor that builds a {@link PasswordPolicy} to delegate how a new
   * password should be made.
   *
   * @param arguments the {@link Arguments} inputted by the user
   * @return The {@link PasswordPolicy} based on user inputted arguments.
   */
  public static PasswordPolicy fromArguments(final Arguments arguments) {
    final var hasSymbols = !arguments.isAlphanumeric();
    final var minimumPerCharacterSet = getMinimumPerCharacterSet(hasSymbols);

    return PasswordPolicy.builder()
        .length(arguments.getLength())
        .hasSymbols(hasSymbols)
        .minimumPerCharacterSet(minimumPerCharacterSet)
        .build();
  }

  private static Map<CharacterSet, Integer> getMinimumPerCharacterSet(final boolean hasSymbols) {
    return Map.of(
      UPPER_CASE, MINIMUM_SECTION_LENGTH,
      LOWER_CASE, MINIMUM_SECTION_LENGTH,
      NUMBERS, MINIMUM_SECTION_LENGTH,
      SPECIAL, hasSymbols ? MINIMUM_SECTION_LENGTH : DISABLED_LENGTH
    );
  }

}

package ghastlith.babel.password;

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
    boolean hasSymbols
) {

  private static final float NON_ALPHANUMERIC_WEIGHT = 0.25f;
  private static final int BASE_LENGTH = 0;

  /**
   * Constructor that builds a PasswordPolicy to delegate how a new password
   * should be made.
   *
   * @param arguments the {@link Arguments} inputted by the user
   * @return The PasswordPolicy based on user inputted arguments.
   */
  public static PasswordPolicy fromArguments(final Arguments arguments) {
    return PasswordPolicy.builder()
        .length(arguments.getLength())
        .hasSymbols(!arguments.isAlphanumeric())
        .build();
  }

  /**
   * Calculate the amount of letters a password should have.
   *
   * @return The calculated length.
   */
  public int lettersLength() {
    final var numbers = numbersLength();
    final var special = specialLength();

    return length() - numbers - special;
  }

  /**
   * Calculate the amount of numbers a password should have.
   *
   * @return The calculated length.
   */
  public int numbersLength() {
    return (int) Math.ceil(length() * NON_ALPHANUMERIC_WEIGHT);
  }

  /**
   * Calculate the amount of special characters a password should have.
   *
   * @return The calculated length.
   */
  public int specialLength() {
    return hasSymbols ? numbersLength() : BASE_LENGTH;
  }

}

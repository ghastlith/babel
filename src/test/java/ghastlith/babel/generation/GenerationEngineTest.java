package ghastlith.babel.generation;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.SecureRandom;

import org.junit.jupiter.api.Test;

public class GenerationEngineTest {

  private final SecureRandom random = new SecureRandom();
  private final GenerationEngine generationEngine = new GenerationEngine(random);

  @Test
  void generatePassword_shouldGeneratePasswordWithCorrectLength() {
    // given
    final var policy = GenerationPolicy.builder()
        .length(20)
        .build();

    // when
    final var password = generationEngine.generatePassword(policy);

    // then
    assertThat(password.length()).isEqualTo(20);
  }

  @Test
  void generatePassword_shouldGeneratePasswordWithIntentedAmountOfCharactersBasedOnType() {
    // given
    final var policy = GenerationPolicy.builder()
        .length(24)
        .hasSymbols(true)
        .build();

    // when
    final var password = generationEngine.generatePassword(policy);

    final var letters = password.chars().filter(Character::isLetter).count();
    final var numbers = password.chars().filter(Character::isDigit).count();
    final var special = password.chars().filter(c -> !Character.isLetterOrDigit(c)).count();

    // then
    assertThat(letters).isEqualTo(policy.lettersLength());
    assertThat(numbers).isEqualTo(policy.numbersLength());
    assertThat(special).isEqualTo(policy.specialLength());
  }

  @Test
  void generatePassword_shouldGeneratePasswordWithoutSpecialCharactersWhenSymbolsDisabled() {
    // given
    final var policy = GenerationPolicy.builder()
        .length(32)
        .hasSymbols(false)
        .build();

    // when
    final var password = generationEngine.generatePassword(policy);

    final var hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

    // then
    assertThat(hasSpecial).isFalse();
  }

}

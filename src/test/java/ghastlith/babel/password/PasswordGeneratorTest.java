package ghastlith.babel.password;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import ghastlith.babel.argument.ArgumentProcessor;

public class PasswordGeneratorTest {

  private final ArgumentProcessor argumentsProcessor = new ArgumentProcessor();
  private final PasswordGenerator passwordGenerator = new PasswordGenerator();

  @Test
  void generate_shouldGeneratePasswordWithCorrectLength() {
    // given
    final var arguments = new String[] { "--length=20" };
    final var parsed = argumentsProcessor.parse(arguments);
    final var policy = PasswordPolicy.fromArguments(parsed);

    // when
    final var password = passwordGenerator.generate(policy);

    // then
    assertThat(password.length()).isEqualTo(20);
  }

  @Test
  void generate_shouldGeneratePasswordWithoutSpecialCharactersWhenSymbolsEnabled() {
    // given
    final var arguments = new String[] { "--length=32" };
    final var parsed = argumentsProcessor.parse(arguments);
    final var policy = PasswordPolicy.fromArguments(parsed);

    // when
    final var password = passwordGenerator.generate(policy);

    final var hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

    // then
    assertThat(hasSpecial).isTrue();
  }

  @Test
  void generate_shouldGeneratePasswordWithoutSpecialCharactersWhenSymbolsDisabled() {
    // given
    final var arguments = new String[] { "--length=32", "--alphanumeric" };
    final var parsed = argumentsProcessor.parse(arguments);
    final var policy = PasswordPolicy.fromArguments(parsed);

    // when
    final var password = passwordGenerator.generate(policy);

    final var hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

    // then
    assertThat(hasSpecial).isFalse();
  }

}

package ghastlith.passwordgenerator.generation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ghastlith.passwordgenerator.argument.Arguments;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
public class GenerationPolicyTest {

  @Mock private Arguments mockArguments;

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void fromArguments_shouldBuildAndInsertValuesCorrectly() {
    // given
    when(mockArguments.getLength()).thenReturn(18);
    when(mockArguments.isAlphanumeric()).thenReturn(true);

    // when
    final var policy = GenerationPolicy.fromArguments(mockArguments);
    final var violations = validator.validate(policy);

    // then
    assertEquals(18, policy.length());
    assertEquals(!mockArguments.isAlphanumeric(), policy.hasSymbols());
    assertThat(violations).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(ints = { 0, 15, 35, 100 })
  void fromArguments_shouldContainViolationWhenProvidedPasswordLengthIsOutsideRange(final int length) {
    // given
    final var policy = GenerationPolicy.builder()
        .length(length)
        .build();

    // then
    final var violations = validator.validate(policy);

    // then
    assertThat(violations).isNotEmpty();
  }

  @Test
  void lettersLength_shouldReturnCalculatedAmountOfLettersCorrectlyWhenSymbolsAreEnabled() {
    // given
    final var policy = GenerationPolicy.builder()
        .length(22)
        .hasSymbols(true)
        .build();

    // when
    final var length = policy.lettersLength();

    // then
    assertEquals(10, length);
  }

  @Test
  void lettersLength_shouldReturnCalculatedAmountOfLettersCorrectlyWhenSymbolsAreDisabled() {
    // given
    final var policy = GenerationPolicy.builder()
        .length(22)
        .hasSymbols(false)
        .build();

    // when
    final var length = policy.lettersLength();

    // then
    assertEquals(16, length);
  }

  @Test
  void numbersLength_shouldReturnCalculatedAmountOfNumbersCorrectly() {
    // given
    final var policy = GenerationPolicy.builder()
        .length(26)
        .build();

    // when
    final var length = policy.numbersLength();

    // then
    assertEquals(7, length);
  }

  @Test
  void specialLength_shouldReturnCalculatedAmountOfSpecialCharactersCorrectlyWhenSymbolsAreEnabled() {
    // given
    final var policy = GenerationPolicy.builder()
        .length(30)
        .hasSymbols(true)
        .build();

    // when
    final var length = policy.specialLength();

    // then
    assertEquals(8, length);
  }

  @Test
  void specialLength_shouldReturnCalculatedAmountOfSpecialCharactersCorrectlyWhenSymbolsAreDisabled() {
    // given
    final var policy = GenerationPolicy.builder()
        .length(30)
        .hasSymbols(false)
        .build();

    // when
    final var length = policy.specialLength();

    // then
    assertEquals(0, length);
  }

}

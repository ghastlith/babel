package ghastlith.babel.password;

import static ghastlith.babel.password.CharacterSet.SPECIAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ghastlith.babel.argument.Arguments;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
public class PasswordPolicyTest {

  @Mock private Arguments mockArguments;

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void fromArguments_shouldBuildAndInsertValuesCorrectly() {
    // given
    when(mockArguments.getLength()).thenReturn(18);
    when(mockArguments.isAlphanumeric()).thenReturn(true);

    // when
    final var policy = PasswordPolicy.fromArguments(mockArguments);
    final var violations = validator.validate(policy);

    // then
    assertThat(policy.length()).isEqualTo(18);
    assertThat(policy.hasSymbols()).isNotEqualTo(mockArguments.isAlphanumeric());
    assertThat(policy.minimumPerCharacterSet().get(SPECIAL)).isEqualTo(0);
    assertThat(violations).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(ints = { 0, 15, 35, 100 })
  void fromArguments_shouldContainViolationWhenProvidedPasswordLengthIsOutsideRange(final int length) {
    // given
    final var policy = PasswordPolicy.builder()
        .length(length)
        .build();

    // then
    final var violations = validator.validate(policy);

    // then
    assertThat(violations).isNotEmpty();
  }

}

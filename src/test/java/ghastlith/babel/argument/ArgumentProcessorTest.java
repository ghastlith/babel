package ghastlith.babel.argument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import picocli.CommandLine.ParameterException;

@ExtendWith(OutputCaptureExtension.class)
public class ArgumentProcessorTest {

  private ArgumentProcessor argumentProcessor;

  @BeforeEach
  void setUp() {
    argumentProcessor = new ArgumentProcessor();
  }

  @Test
  void parse_shouldParseSuccessfullyWhenValidArgumentsProvided() {
    // given
    final var args = new String[] { "--length=16", "--alphanumeric" };

    // when
    final var arguments = argumentProcessor.parse(args);

    // then
    assertThat(arguments.getLength()).isEqualTo(16);
    assertThat(arguments.isAlphanumeric()).isTrue();
  }

  @Test
  void parse_shouldParseSuccessfullyWhenNoArgumentsProvided() {
    // given
    final var args = new String[0];

    // when
    final var arguments = argumentProcessor.parse(args);

    // then
    assertThat(arguments.getLength()).isEqualTo(20);
    assertThat(arguments.isAlphanumeric()).isFalse();
  }

  @Test
  void parse_shouldParseSuccessfullyWhenCaseInsensitiveArgumentsProvided() {
    // given
    final var args = new String[] { "--LENGTH=24", "--aLpHaNuMeRiC" };

    // when
    final var arguments = argumentProcessor.parse(args);

    // then
    assertThat(arguments.getLength()).isEqualTo(24);
    assertThat(arguments.isAlphanumeric()).isTrue();
  }

  @Test
  void parse_shouldParseSuccessfullyWhenAbbreviatedArgumentsProvided() {
    // given
    final var args = new String[] { "--l=28", "--a" };

    // when
    final var arguments = argumentProcessor.parse(args);

    // then
    assertThat(arguments.getLength()).isEqualTo(28);
    assertThat(arguments.isAlphanumeric()).isTrue();
  }

  @Test
  void parse_shouldCaptureAndLogUnmatchedArgumentListWhenProvided(final CapturedOutput output) {
    // given
    final var args = new String[] { "--unmatched=argument" };

    // when
    final var arguments = argumentProcessor.parse(args);

    // then
    assertThat(arguments.getUnmatched().size()).isEqualTo(1);
    assertThat(output.getOut()).contains("unrecognized argument: --unmatched");
  }

  @Test
  void parse_shouldThrowParameterExceptionWhenInvalidLengthArgumentProvided() {
    // given
    final var args = new String[] { "--length=notanumber" };

    // when
    final var throwable = catchThrowable(() -> argumentProcessor.parse(args));

    // then
    assertThat(throwable).isInstanceOf(ParameterException.class);
  }

  @Test
  void parse_shouldThrowParameterExceptionWhenInvalidAlphanumericArgumentProvided() {
    // given
    final var args = new String[] { "--alphanumeric=notboolean" };

    // when
    final var throwable = catchThrowable(() -> argumentProcessor.parse(args));

    // then
    assertThat(throwable).isInstanceOf(ParameterException.class);
  }

}

package ghastlith.passwordgenerator.argument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    assertEquals(16, arguments.getLength());
    assertTrue(arguments.isAlphanumeric());
  }

  @Test
  void parse_shouldParseSuccessfullyWhenNoArgumentsProvided() {
    // given
    final var args = new String[0];

    // when
    final var arguments = argumentProcessor.parse(args);

    // then
    assertEquals(20, arguments.getLength());
    assertFalse(arguments.isAlphanumeric());
  }

  @Test
  void parse_shouldParseSuccessfullyWhenCaseInsensitiveArgumentsProvided() {
    // given
    final var args = new String[] { "--LENGTH=24", "--aLpHaNuMeRiC" };

    // when
    final var arguments = argumentProcessor.parse(args);

    // then
    assertEquals(24, arguments.getLength());
    assertTrue(arguments.isAlphanumeric());
  }

  @Test
  void parse_shouldParseSuccessfullyWhenAbbreviatedArgumentsProvided() {
    // given
    final var args = new String[] { "--l=28", "--a" };

    // when
    final var arguments = argumentProcessor.parse(args);

    // then
    assertEquals(28, arguments.getLength());
    assertTrue(arguments.isAlphanumeric());
  }

  @Test
  void parse_shouldCaptureAndLogUnmatchedArgumentListWhenProvided(final CapturedOutput output) {
    // given
    final var args = new String[] { "--unmatched=argument" };

    // when
    final var arguments = argumentProcessor.parse(args);

    // then
    assertEquals(1, arguments.getUnmatched().size());
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

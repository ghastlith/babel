package ghastlith.babel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class MainTest {

  @Test
  void run_shouldLogSuccessMessageWithGeneratedPassword(final CapturedOutput output) {
    // given
    final var args = new String[] { "--length=20", "--alphanumeric" };

    // when
    SpringApplication.run(Main.class, args);

    // then
    assertThat(output.getOut()).contains("generated password");
  }

  @Test
  void run_shouldLogFailureMessageWithExceptionReason(final CapturedOutput output) {
    // given
    final var args = new String[] { "--length=100" };

    // when
    SpringApplication.run(Main.class, args);

    // then
    assertThat(output.getAll()).contains("error when generating password");
  }

}

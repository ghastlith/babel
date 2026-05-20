package ghastlith.babel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ghastlith.babel.argument.ArgumentProcessor;
import ghastlith.babel.password.PasswordGenerator;
import ghastlith.babel.password.PasswordPolicy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class Main implements CommandLineRunner {

  @Autowired private ApplicationContext context;
  @Autowired private ArgumentProcessor argumentProcessor;
  @Autowired private PasswordGenerator passwordGenerator;

  private static final int BASE_ERROR_CODE = 1;

  public static void main(final String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {
    try {
      final var arguments = argumentProcessor.parse(args);
      final var policy = PasswordPolicy.fromArguments(arguments);
      final var password = passwordGenerator.generatePassword(policy);

      log.info("generated password: {}", password);
    } catch (Exception e) {
      log.error("error when generating password", e);
      SpringApplication.exit(context, () -> BASE_ERROR_CODE);
    }
  }

}

package ghastlith.passwordgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ghastlith.passwordgenerator.argument.ArgumentProcessor;
import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class Main implements CommandLineRunner {

  @Autowired private ArgumentProcessor argumentProcessor;

  public static void main(final String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {
    final var arguments = argumentProcessor.parse(args);
  }

}

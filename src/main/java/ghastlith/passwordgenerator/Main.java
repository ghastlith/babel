package ghastlith.passwordgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ghastlith.passwordgenerator.argument.ArgumentProcessor;
import ghastlith.passwordgenerator.generation.GenerationEngine;
import ghastlith.passwordgenerator.generation.GenerationPolicy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class Main implements CommandLineRunner {

  @Autowired private ArgumentProcessor processor;
  @Autowired private GenerationEngine engine;

  public static void main(final String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {
    final var arguments = processor.parse(args);
    final var policy = GenerationPolicy.fromArguments(arguments);
    final var password = engine.generatePassword(policy);
    log.info(password);
  }

}

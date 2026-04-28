package ghastlith.passwordgenerator.argument;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArgumentProcessor {

  public InputArguments parse(final String... args) {
    final var arguments = new InputArguments();

    parser(arguments).parseArgs(args);
    arguments.getUnmatched()
        .forEach(arg -> log.warn("Unrecognized parameter: {}", arg));

    return arguments;
  }

  private CommandLine parser(final Object arguments) {
    return new CommandLine(arguments)
        .setOptionsCaseInsensitive(true)
        .setAbbreviatedOptionsAllowed(true);
  }

}

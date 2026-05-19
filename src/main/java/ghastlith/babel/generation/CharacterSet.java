package ghastlith.babel.generation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Sets containing the lists of characters allowed during password generation.
 */
@Getter
@RequiredArgsConstructor
public enum CharacterSet {

  LETTERS("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"),
  NUMBERS("0123456789"),
  SPECIAL("!#$%&()*+,-./:;<=>?@[]^_{}~");

  private final String characters;

}

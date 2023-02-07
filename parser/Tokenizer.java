package parser;

import java.util.*;
import parser.Token;

public class Tokenizer {

  public static Queue<Token> getTokens(String code) {
    Queue<Token> tokens = new LinkedList<Token>();
    String[] lines = code.split("\\R");
    String terminals = "(,|\\.|;|:=|\\+|-|\\*|/|%|\\(|\\)|\\|=|<|=<|>|=>|=|:)";
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];
      line = line.replaceAll("\\s+", " ").replaceAll(terminals, " $1 ");
      if (line == "") continue;
      String[] values = line.split(" ");
      for (String value : values) {
        if (value == "") continue;
        tokens.add(new Token(value, i + 1));
      }
    }
    return tokens;
  }
}

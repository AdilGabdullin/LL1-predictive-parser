import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import parser.Parser;
import parser.ParsingTable;
import parser.Token;
import parser.Tokenizer;

class Main {

  public static void main(String[] args) throws IOException {
    String code = Files.readString(Paths.get(args[0]));
    boolean verbose = args.length > 1 && args[1].equals("verbose");
    Parser parser = new Parser();
    parser.verbose = verbose;
    parser.parse(code);
  }
}

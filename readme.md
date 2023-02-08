# LL1 predictive parser

See assignment.doc

## Usage:
    java Main tests/valid01.txt

## Commands:
### compile
    javac Main.java
### run
    java Main <FILENAME>
### run in verbose mode
    java Main <FILENAME> verbose
### tests
    bash run-tests.sh

## Classes:
### Parser
Parses code and prints "Successfully parsed" or dies with detailed error. In verbose mode also outputs stack, token and symbol table on each iteration
### Tokenizer
Transforms code to a queue of tokens
### ParsingTable
derrivation table
### Token
Primitive data transfer class
### TokenType
Enum KEYWORD, OPERATOR, NAME, INTEGER_VALUE, DELIMETER, UNKNOWN

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
tests: bash run-tests.sh

## Classes:
### Token
Primitive data transfer class
### TokenType
enum KEYWORD, OPERATOR, NAME, INTEGER_VALUE, DELIMETER, UNKNOWN
### Tokenizer
transform code to a queue of tokens
### ParsingTable
derrivation table
### Parser
Parse code, prints "Successfully parsed" or detailed error.
In verbose mode also outputs stack, token and symbol table on each iteration

package com.lthorup.prolog;

import java.util.ArrayList;

public class Tokenizer {

	private SymbolTable symTable;
	private ArrayList<Token> tokens;
	private String input;
	private int inputNext;
	private int inputEnd;
	private int line;
	private int tokNext;
		
	//-------------------------------------------
	public Tokenizer() {
		symTable = new SymbolTable();
		tokens = new ArrayList<Token>();
	}
	
	//-------------------------------------------
	public void parse(String str) throws Exception {
		input = str;
		inputNext = 0;
		inputEnd = input.length();
		line = 1;
		tokens.clear();
		while ( parseWhiteSpace() ||
				parseComment() ||
				parseSymbol() ||
				parseId() ||
				parseVariable() ||
				parseNumber());
		input = null;
		if (inputNext < inputEnd)
			throw new Exception("unexpected input on line: " + line);
		tokens.add(new Token(Token.Type.END, Symbol.none, line));
		tokNext = 0;
		line = 1;
	}
	
	//-------------------------------------------
	public void printTokens() {
		System.out.printf("----------- Tokens ----------\n");
		for (Token t : tokens) {
			System.out.printf("%s\n", t.toString());
		}
	}
		
	//-------------------------------------------
	public Token peek(int index) {
		return (tokNext+index) >= tokens.size() ? tokens.get(tokens.size()-1) : tokens.get(tokNext+index);
	}
	
	//-------------------------------------------
	public Token expect(Token.Type type) throws Exception {
		Token t = tokNext >= tokens.size() ? tokens.get(tokens.size()-1) : tokens.get(tokNext);
		if (t.type() != type)
			throw new Exception("expected " + t.type() + " on line " + t.line());
		tokNext++;
		return t;
	}

	//-------------------------------------------
	public Token expect(Symbol s) throws Exception {
		Token t = tokNext >= tokens.size() ? tokens.get(tokens.size()-1) : tokens.get(tokNext);
		if (t.symbol() != s)
			throw new Exception("expected symbol " + s.name() + " on line " + t.line());
		tokNext++;
		return t;
	}

	//-------------------------------------------
	public Token expect(Symbol.Type type) throws Exception {
		Token t = tokNext >= tokens.size() ? tokens.get(tokens.size()-1) : tokens.get(tokNext);
		if (t.symbol().type() != type)
			throw new Exception("expected symbol " + type + " on line " + t.line());
		tokNext++;
		return t;
	}
	
	//-------------------------------------------
	private boolean parseWhiteSpace() {
		int i = inputNext;
		while (i < inputEnd && Character.isWhitespace(input.charAt(i))) {
			if (input.charAt(i) == '\n')
				line++;
			i++;
		}
		boolean result = i > inputNext;
		inputNext = i;
		return result;
	}
	
	//-------------------------------------------
	private boolean parseComment() {
		if (! input.startsWith("//", inputNext))
			return false;
		int i = inputNext;
		while (i < inputEnd && input.charAt(i) != '\n')
			i++;
		boolean result = i > inputNext;
		inputNext = i;
		return result;
	}
	
	//-------------------------------------------
	private boolean parseId() {
		if (inputNext >= inputEnd || ! Character.isLowerCase(input.charAt(inputNext)))
			return false;
		int i = inputNext;
		while (i < inputEnd && Character.isLetter(input.charAt(i)))
			i++;
		String name = input.substring(inputNext, i);
		Symbol symbol = symTable.add(name);
		tokens.add(new Token(Token.Type.ID, symbol, line));
		inputNext = i;
		return true;
	}
	
	//-------------------------------------------
	private boolean parseVariable() {
		if (inputNext >= inputEnd || (! Character.isUpperCase(input.charAt(inputNext)) && input.charAt(inputNext) != '_'))
			return false;
		int i = inputNext;
		while (i < inputEnd && (Character.isLetter(input.charAt(i)) || input.charAt(i) == '_'))
			i++;
		String name = input.substring(inputNext, i);
		Symbol symbol = symTable.add(name);
		tokens.add(new Token(Token.Type.VARIABLE, symbol, line));
		inputNext = i;
		return true;
	}

	//-------------------------------------------
	private boolean parseNumber() {
		if (inputNext < inputEnd && (Character.isDigit(input.charAt(inputNext)) || input.charAt(inputNext) == '-')) {
			int i = inputNext + 1;
			while (Character.isDigit(input.charAt(i)))
				i++;
			String name = input.substring(inputNext, i);
			inputNext = i;
			int value = Integer.parseInt(name);
			tokens.add(new Token(value, line));
			return true;
		}
		return false;
	}
	
	//-------------------------------------------
	private boolean parseSymbol() {
		for (Symbol s : Symbol.symbols) {
			if (input.startsWith(s.name(), inputNext)) {
				tokens.add(new Token(Token.Type.SYMBOL, s, line));
				inputNext += s.name().length();
				return true;
			}
		}
		return false;
	}
}

package com.lthorup.prolog;

public class Token {
	
	public enum Type { ID, VARIABLE, NUMBER, SYMBOL, END };
	
	private Type type;
	private Symbol symbol;
	private double value;
	private int line;
	
	public Token(Token.Type type, Symbol symbol, int line) {
		this.type = type;
		this.symbol = symbol;
		this.line = line;
	}
	
	public Token(double value, int line) {
		this.type = Token.Type.NUMBER;
		this.symbol = Symbol.none;
		this.value = value;
		this.line = line;
	}
	
	public Type type() { return type; }
	public Symbol symbol() { return symbol; }
	public double value() { return value; }
	public int line() { return line; }
	
	public String toString() {
		String s = "unknown";
		switch (type) {
		case ID:
			s = "ID: " + symbol.name();
			break;
		case VARIABLE:
			s = "VAR: " + symbol.name();
			break;
		case SYMBOL:
			s = "SYM: " + symbol.name();
			break;
		case NUMBER:
			s = "NUM: " + String.valueOf(value);
			break;
		case END:
			s = "EOF";
			break;
		default:
			s = "UNKNOWN";
			break;
		}
		return s;
	}
}

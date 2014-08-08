package com.lthorup.prolog;

import java.util.ArrayList;

public class SymbolTable {

	private ArrayList<Symbol> table = new ArrayList<Symbol>();
	
	public SymbolTable() {
		for (Symbol s : Symbol.symbols) {
			table.add(s);
		}
		table.add(Symbol.under);
		table.add(Symbol.not);
	}
	
	public Symbol add(String n) {
		if (n.equals("_"))
			return new Symbol(n);
		for (Symbol s : table) {
			if (n.equals(s.name()))
				return s;
		}
		Symbol s = new Symbol(n);
		table.add(s);
		return s;
	}
	
}

package com.lthorup.prolog;

import java.util.ArrayList;

public class Rule {
	
	private Clause thenPart;
	private ArrayList<Clause> ifPart;
	
	public Rule(Clause thenPart, ArrayList<Clause> ifPart) {
		this.thenPart = thenPart;
		this.ifPart = ifPart;
	}
		
	public Symbol functor() { return thenPart.functor(); }
	public int arity() { return thenPart.arity(); }
	public Clause thenPart() { return thenPart; }
	public ArrayList<Clause> ifPart() { return ifPart; }
	
	@Override
	public String toString() {
		String rule = thenPart.toString(null);
		int cnt = 0;
		for (Clause c : ifPart) {
			if (cnt == 0)
				rule += " :- ";
			else
				rule += ", ";
			rule += c.toString(null);
			cnt++;
		}
		rule = rule + ".";
		return rule;
	}
}
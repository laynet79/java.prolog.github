package com.lthorup.prolog;

import java.util.ArrayList;

public class Parser {

	private Tokenizer tok = new Tokenizer();
	
	public Parser() {
	}
	
	//---------------------------------------------
	public ArrayList<Rule> parseRules(String input) throws Exception {
		tok.parse(input);
		ArrayList<Rule> rules = new ArrayList<Rule>();
		while (tok.peek(0).type() != Token.Type.END) {
			Rule rule = parseRule();
			rules.add(rule);
		}
		return rules;
	}
	
	//---------------------------------------------
	public Goal parseGoals(String input) throws Exception {
		tok.parse(input);
		ArrayList<Clause> goalList = new ArrayList<Clause>();
		while (tok.peek(0).symbol() != Symbol.dot) {
			Clause g = parseClause();
			if (tok.peek(0).symbol() != Symbol.dot)
				tok.expect(Symbol.comma);
			goalList.add(g);
		}
		tok.expect(Symbol.dot);
		Goal goals = null;
		for (int i = goalList.size()-1; i >= 0; i--)
			goals = new Goal(goalList.get(i), goals);
		return goals;
	}

	//---------------------------------------------
	private Rule parseRule() throws Exception {
		Clause thenPart = parseClause();
		ArrayList<Clause> ifPartList = new ArrayList<Clause>();
		if (tok.peek(0).symbol() == Symbol.imp) {
			tok.expect(Symbol.imp);
			while (tok.peek(0).symbol() != Symbol.dot) {
				if (ifPartList.size() > 0)
					tok.expect(Symbol.comma);
				Clause ifPart = parseClause();
				ifPartList.add(ifPart);
			}
		}
		tok.expect(Symbol.dot);
		Rule rule = new Rule(thenPart, ifPartList);
		return rule;
	}
		
	//---------------------------------------------
	private Clause parseClause() throws Exception {
		Token t = tok.peek(0);
		if (t.symbol() == Symbol.cut) {
			tok.expect(Symbol.cut);
			return new Cut();
		}
		if (t.symbol() == Symbol.fail) {
			tok.expect(Symbol.fail);
			return new Fail();
		}
		if (t.symbol() == Symbol.not) {
			tok.expect(Symbol.not);
			Term term = parseTerm();
			return new Not(term);
		}
		Term term = parseTerm();
		if (term instanceof Symbol)
			return new Clause((Symbol)term, null);
		if (term instanceof Clause)
			return (Clause)term;
		throw new Exception("bad clause found on line " + t.line());
	}
	
	//---------------------------------------------
	private Term parseTerm() throws Exception {
		Term term = parseTermLogic();
		Token t = tok.peek(0);
		if (t.symbol() == Symbol.unify) {
			tok.expect(Symbol.unify);
			Term rest = parseTermLogic();
			return new Unify(term, rest);
		}
		if (t.symbol() == Symbol.is) {
			tok.expect(Symbol.is);
			Term rest = parseTermLogic();
			return new Is(term, rest);
		}
		return term;
	}
	
	//---------------------------------------------
	private Term parseTermLogic() throws Exception {
		Term term = parseTermAdd();
		if (tok.peek(0).symbol().type() == Symbol.Type.LOGIC) {
			Token op = tok.expect(Symbol.Type.LOGIC);
			Term rest = parseTermAdd();
			term = Logic.create(op.symbol(), term, rest);
		}
		return term;
	}
	
	//---------------------------------------------
	private Term parseTermAdd() throws Exception {
		Term term = parseTermMult();
		while (tok.peek(0).symbol().type() == Symbol.Type.ADD) {
			Token op = tok.expect(Symbol.Type.ADD);
			Term rest = parseTermMult();
			term = Arithmetic.create(op.symbol(), term, rest);
		}
		return term;
	}
	
	//---------------------------------------------
	private Term parseTermMult() throws Exception {
		Term factor = parseTermFactor();
		while (tok.peek(0).symbol().type() == Symbol.Type.MULT) {
			Token op = tok.expect(Symbol.Type.MULT);
			Term rest = parseTermFactor();
			factor = Arithmetic.create(op.symbol(),factor,rest);
		}
		return factor;		
	}
	
	//---------------------------------------------
	private Term parseTermFactor() throws Exception {
		Token t = tok.peek(0);
		Term term = null;
		if (t.symbol() == Symbol.lp){
			tok.expect(Symbol.lp);
			term = parseTerm();
			tok.expect(Symbol.rp);
		}
		else if (t.symbol() == Symbol.lb) {
			tok.expect(Symbol.lb);
			term = parseList();
		}
		else if (t.symbol() == Symbol.minus) {
			tok.expect(Symbol.minus);
			term = parseTerm();
			if (term instanceof Number)
				((Number)term).negate();
			else
				term = new Neg(term);
		}
		else if (t.type() == Token.Type.NUMBER)
			term = new Number(tok.expect(Token.Type.NUMBER).value());
		else if (t.type() == Token.Type.VARIABLE)
			term = new Variable(tok.expect(Token.Type.VARIABLE).symbol(),0);
		else if (t.type() == Token.Type.ID && tok.peek(1).symbol() == Symbol.lp)
			term = parseStruct();
		else if (t.type() == Token.Type.ID)
			term = tok.expect(Token.Type.ID).symbol();
		else
			throw new Exception("bad term found on line " + tok.peek(0).line());
		return term;
	}

	//---------------------------------------------
	private Clause parseList() throws Exception {
		if (tok.peek(0).symbol() == Symbol.rb) {
			tok.expect(Symbol.rb);
			return List.nil;
		}
		Term head = parseTerm();
		Term tail;
		if (tok.peek(0).symbol() == Symbol.bar) {
			tok.expect(Symbol.bar);
			tail = parseTerm();
			tok.expect(Symbol.rb);
		}
		else {
			if (tok.peek(0).symbol() != Symbol.rb)
				tok.expect(Symbol.comma);
			tail = parseList();
		}
		return new List(head, tail);
	}
	
	//---------------------------------------------
	private Clause parseStruct() throws Exception {
		Token functor = tok.expect(Token.Type.ID);
		ArrayList<Term> termList = new ArrayList<Term>();
		if (tok.peek(0).symbol() == Symbol.lp) {
			tok.expect(Symbol.lp);
			while (tok.peek(0).symbol() != Symbol.rp) { 
				if (termList.size() > 0)
					tok.expect(Symbol.comma);
				Term term = parseTerm();
				termList.add(term);
			}
			tok.expect(Symbol.rp);
		}
		Clause clause = new Clause(functor.symbol(), termList);
		return clause;
	}
}


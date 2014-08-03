package com.lthorup.prolog;

public class Symbol extends Term {
	
	enum Type { GENERAL, MULT, ADD, LOGIC }

	private String name;
	private Type type;
	private boolean isInfix;
	
	public static Symbol none = new Symbol("__none__");

	public static Symbol dot = new Symbol(".");
	public static Symbol comma = new Symbol(",");
	public static Symbol imp = new Symbol(":-");
	public static Symbol lp = new Symbol("(");
	public static Symbol rp = new Symbol(")");
	public static Symbol lb = new Symbol("[");
	public static Symbol rb = new Symbol("]");
	public static Symbol bar = new Symbol("|");
	
	public static Symbol plus  = new Symbol("+", Type.ADD, true);
	public static Symbol minus = new Symbol("-", Type.ADD, true);
	public static Symbol mult  = new Symbol("*", Type.MULT, true);
	public static Symbol div   = new Symbol("/", Type.MULT, true);
	public static Symbol mod   = new Symbol("%", Type.MULT, true);
	
	public static Symbol lte = new Symbol("<=", Type.LOGIC, true);
	public static Symbol lt  = new Symbol("<", Type.LOGIC, true);
	public static Symbol gte = new Symbol(">=", Type.LOGIC, true);
	public static Symbol gt  = new Symbol(">", Type.LOGIC, true);
	public static Symbol eq  = new Symbol("==", Type.LOGIC, true);
	public static Symbol neq = new Symbol("!=", Type.LOGIC, true);

	public static Symbol unify = new Symbol("=", Type.GENERAL, true);
	public static Symbol is = new Symbol("is", Type.GENERAL, true);
	public static Symbol cut = new Symbol("!");
	public static Symbol fail = new Symbol("fail");
	public static Symbol under = new Symbol("_");
	public static Symbol nil = new Symbol("nil");
	public static Symbol not = new Symbol("not");
	
	public static Symbol[] symbols = new Symbol[] { dot, comma, imp, lp, rp, lb, rb, bar, plus, minus, mult, div, mod, lte, lt, gte, gt, eq, neq, unify, is, cut, fail };
	
	public Symbol(String name) {
		this.name = name;
		this.type = Type.GENERAL;
		this.isInfix = false;
	}
	public Symbol(String name, Type type, boolean isInfix) {
		this.name = name;
		this.type = type;
		this.isInfix = isInfix;
	}
	public String name() { return name; }
	public Type type() { return type; }
	public boolean isInfix() { return isInfix; }
	
	@Override
	public Binding unify(Term other, Binding env) {
		if (other instanceof Variable)
			return other.unify(this, env);
		if (this != other)
			return null;
		return env;
	}
	
	@Override
	public String toString(Binding env) { return name; }
}

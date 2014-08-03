package com.lthorup.prolog;

public class Binding {

	private Variable var;
	private Term value;
	private Binding rest;
	
	public static Binding empty = new Binding(new Variable(Symbol.dot,100),null,null);
	
	public static Binding bind(Variable var, Term value, Binding env) {
		//System.out.printf("binding %s:%d to %s\n", var.symbol().name(), var.level(), value.toString(null));
		return new Binding(var, value, env);
	}
	
	public Binding(Variable var, Term value, Binding rest) {
		
		this.var = var;
		this.value = value;
		this.rest = rest;
	}
	
	public Variable var() { return var; }
	public Term value() { return value; }
	public Binding rest() { return rest; }
	
	public Binding lookup(Variable var) {
		Binding result = null;
		Binding b = this;
		while (b != null) {
			if (var.equals(b.var)) {
				result = b;
				if (b.value instanceof Variable) {
					var = (Variable)b.value;
					b = this;
				}
				else
					break;
			}
			b = b.rest;
		}
		return result;
	}
	
	public String toString() {
		String s = String.format("(%s_%d),\n", var.toString(), var.level());
		if (rest == null)
			return s;
		return s + rest.toString();
	}
}

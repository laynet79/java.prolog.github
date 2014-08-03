package com.lthorup.prolog;

public class List extends Clause {
	
	public static List nil = new List(null,null);
	private final int MAX_PRINT_LENGTH = 20;
	
	public List(Term head, Term tail) {
		super(Symbol.dot, null);
		if (head != null) {
			addTerm(head);
			addTerm(tail);
		}
	}
		
	@Override
	public String toString(Binding env) {
		if (this == List.nil)
			return "[]";
		return "[" + tailToString(1, env);
	}
	private String tailToString(int cnt, Binding env) {
		if (cnt > MAX_PRINT_LENGTH)
			return " ...]";
		if (this == List.nil)
			return "]";
		String head = terms.get(0).toString(env);
		Term tailTerm = terms.get(1);
		if (env != null && tailTerm instanceof Variable) {
			Binding b = env.lookup((Variable)tailTerm);
			if (b != null)
				tailTerm = b.value();
		}
		if (!(tailTerm instanceof List)) {
			return String.format("%s|%s]", head, tailTerm.toString(env));
		}
		return String.format("%s%s%s", head, (tailTerm == List.nil) ? "" : ",", ((List)tailTerm).tailToString(cnt+1, env));
	}
}

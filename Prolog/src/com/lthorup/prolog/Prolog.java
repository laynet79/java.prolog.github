package com.lthorup.prolog;

import java.util.ArrayList;

/*
 * This class implements a simple prolog interpreter
 */
public class Prolog implements Runnable {

	Parser parser;
	Database database;
	boolean success;
	boolean trace = false;
	Goal goals;
	volatile boolean running;
	Console console = new Console(null);
	
	private static String program =
			/*
			"parent(layne,jeff)." +
			"parent(layne,becca)." +
			"parent(layne,stephanie)." +
			"sibling(X,Y) :- parent(Z,X), parent(Z,Y)." +
			
			"append([],X,X)." +
			"append([X|Y],T,[X|W]) :- append(Y,T,W)." +
			
			"delete(X,[X|Y],Y)." +
			"delete(X,[Y|Z],[Y|T]) :- delete(X,Z,T)." +
			
			"member(X,[X|Y])." +
			"member(X,[Y|Z]) :- member(X,Z)." +
			*/
			"fac(0,1) :- !." +
			"fac(N,F) :- M is N-1, fac(M,G), F is N*G.";
	
	public Prolog() {
		parser = new Parser();
		database = new Database();
		//trace = true;
	}
	
	public void setConsole(Console console) {
		this.console = console;
	}
	
	public boolean loadProgram(String prog) {
		try {
			ArrayList<Rule> rules = parser.parseRules(prog);
			for (Rule r : rules)
				database.addRule(r);
			console.print("yes\n> ");
		}
		catch(Exception e) {
			console.print(String.format("ERROR: %s\n", e.getMessage()));
			return false;
		}
		return true;
	}
	
	public boolean runQuery(String query) {
		try {
			goals = parser.parseGoals(query);
			console.print(String.format("<<--- %s --->>\n", goals.toString()));
			Thread thread = new Thread(this);
			thread.start();
		}
		catch(Exception e) {
			console.print(String.format("ERROR: %s\n", e.getMessage()));
			return false;
		}
		return true;
	}
	
	public void run() {
		running = true;
		try {
			success = false;
			solve(goals, Binding.empty, 0);
			console.print(String.format("%s\n", success ? "yes" : "no"));
		}
		catch(Exception e) {
			console.print(String.format("ERROR: %s\n", e.getMessage()));
		}
		running = false;
	}
	
	public void abort() {
		running = false;
	}
	
	public void printProgram() { database.print(); }
	
	protected boolean solve(Goal goals, Binding env, int level) throws Exception {
		if (! running)
			throw new Exception("aborted");
		boolean cut = (goals != null && goals.clause() instanceof Cut);
		boolean not = (goals != null && goals.clause() instanceof Not);
		if (cut)
			goals = goals.rest();
		else if (not) {
			Clause goal = (Clause)goals.clause().terms.get(0);
			goals = goals.rest();
			goals = new Goal(goal, goals);
		}
		if (goals == null) {
			printEnvironment(env);
			success = true;
		}
		else {
			if (trace) {
				System.out.printf("\n");
				for (int i = 0; i < level; i++)
					System.out.printf(" ");
				System.out.printf("%s", goals.clause().toString(env));
			}
			if (goals.clause().isPrimative()) {
				Binding newEnv = goals.clause().unify(env);
				if (not) {
					if (newEnv == null)
						newEnv = env;
					else
						newEnv = null;
				}
				if (newEnv != null)
					solve(goals.rest(), newEnv, level+1);
			}
			else {
			ArrayList<Rule> rules = database.getRules(goals.clause().functor());
				if (rules != null) {
					for (int nextRule = 0; nextRule < rules.size(); nextRule++) {
						Rule rule = rules.get(nextRule);
						Clause goal = goals.clause();
						Clause head = (Clause)rule.thenPart().copy(level+1);
						Binding newEnv = goal.unify(head, env);
						if (not) {
							if (newEnv == null)
								newEnv = env;
							else
								newEnv = null;
						}
						if (newEnv != null) {
							if (trace) {
								System.out.printf(" ----> %s", head.toString(env));
							}
							Goal newGoals = goals.rest();
							if (newGoals != null && newGoals.clause() instanceof Cut) {
								nextRule++;
								newGoals = newGoals.rest();
							}
							for (int i = rule.ifPart().size() - 1; i >= 0; i--)
								newGoals = new Goal((Clause)rule.ifPart().get(i).copy(level+1), newGoals);
							boolean wasCut = solve(newGoals, newEnv, level+1);
							if (wasCut)
								nextRule = rules.size();
						}
					}
				}
			}
		}
		return cut;
	}
	
	protected void printEnvironment(Binding env) {
		console.print("---------------------------------\n");
		Binding b = env;
		while (b != null) {
			if (b.var().level() == 0 && b.var().symbol() != Symbol.under)
				console.print(String.format("%s = %s\n", b.var().toString(null), b.value().toString(env)));
			b = b.rest();
		}
	}
	
	/*
	public static void main(String[] args) {

		Prolog prolog = new Prolog();
		if (! prolog.loadProgram(program)) {
			System.out.printf("unable to load program\n");
			return;
		}
		
		prolog.printProgram();
		
		//prolog.runQuery("sibling(jeff,X).");
		//prolog.runQuery("append([a,b,c,d],[1,2,3],X).");
		//prolog.runQuery("member(c,[a,b,c,d]).");
		//prolog.runQuery("delete(3,[1,2,3,4,5,6],X).");
		//prolog.runQuery("X is (1 + 2) * 5 / 4.");
		//prolog.runQuery("(2*6) > 4.");
		prolog.runQuery("fac(5,X).");
	}
	*/
}

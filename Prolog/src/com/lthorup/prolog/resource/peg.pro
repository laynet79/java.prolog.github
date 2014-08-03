peg(Result) :- solve([2,3,4,5,6,7,8,9,10,11,12,13,14,15], [1], Result).

solve([Peg], Empty, []) :- !.
solve(Occupied, Empty, [[From,To]|X]) :-
	move(From,Over,To),
	member(From, Occupied),
	member(Over, Occupied),
	member(To, Empty),
	delete(To, Empty, NewEmpty),
	delete(From, Occupied, NewOccupied),
	delete(Over, NewOccupied, NewNewOccupied),
	solve([To|NewNewOccupied], [From | [Over | NewEmpty]], X).

member(X,[X|Y]) :- !.
member(X,[Y|Z]) :- member(X,Z).

delete(X,[X|Y],Y) :- !.
delete(X,[Y|Z],[Y|T]) :- delete(X,Z,T).

move(4,2,1).		move(1,2,4).
move(6,3,1).		move(1,3,6).
move(4,5,6).		move(6,5,4).
move(7,4,2).		move(2,4,7).
move(10,6,3).		move(3,6,10).
move(7,8,9).		move(9,8,7).
move(8,9,10).		move(10,9,8).
move(8,5,3).		move(3,5,8).
move(9,5,2).		move(2,5,9).
move(11,7,4).		move(4,7,11).
move(15,10,6).		move(6,10,15).
move(11,12,13).		move(13,12,11).
move(12,13,14).		move(14,13,12).
move(13,14,15).		move(15,14,13).
move(12,8,5).		move(5,8,12).
move(13,9,6).		move(6,9,13).
move(13,8,4).		move(4,8,13).
move(14,9,5).		move(5,9,14).

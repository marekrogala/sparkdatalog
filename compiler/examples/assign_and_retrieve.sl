declare P(int x).
declare Q(int x).

P(x) :- x = 1.
Q(x) :- P(x), x = 5.

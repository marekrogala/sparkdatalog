declare P(int x).
declare Q(int x).

P(x) :- x = 0.
P(x) :- x = 1.
P(x) :- x = 2.
Q(x) :- P(x), x = x + x.

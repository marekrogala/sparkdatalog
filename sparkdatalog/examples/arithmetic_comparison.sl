declare P(int x, int y).
declare Q(int x).

P(x, y) :- x = 0, y = 4.
P(x, y) :- x = 1, y = 1.
P(x, y) :- x = 2, y = 1.
P(x, y) :- x = 3, y = 2.

Q(x) :- P(x, y), y >= x.

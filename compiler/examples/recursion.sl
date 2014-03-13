declare P(int x).
declare Q(int x).

P(x) :- x = 0.
P(x) :- x = 1.
P(x) :- x = 2.
Q(x) :- x = 0;
     :- P(z), Q(z), x = z + 1.

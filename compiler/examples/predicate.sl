declare P(int x).
declare Q(int x).

P(x) :- x = 1;
     :- x = 2. 

Q(x) :- P(x).

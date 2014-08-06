declare Arc(int x, int z).
declare Tc(int x aggregate R, int z aggregate Q).
 
Arc(x, y) :- x=1, y=2.
Arc(x, y) :- x=2, y=3.
Arc(x, y) :- x=3, y=4.

Tc(x, y) :- Arc(x, y).
Tc(x, y) :- Arc(x, z), Tc(z, y).


declare Edge(int a, int b, int d).
declare Path(int t, int d aggregate Max).
 
Edge(x, y, d) :- x=1, y=2, d=1.
Edge(x, y, d) :- x=2, y=3, d=1.
Edge(x, y, d) :- x=1, y=3, d=5.

Path(x, d) :- Edge(1, x, d).
Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.


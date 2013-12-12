int N = 1768195.

declare Edge [int src: 0..N] (int sink).
declare Nodes [int n: 0..N].
declare Comp [int n: 0..N](int root).
declare CompIds (int id).
declare CompCount.

Comp(n, $Min(i)) 	:− NODES(n), i = n;

		:− COMP(p, i), EDGE(p, n).


COMPIDS(id) :− COMP(_, id).

COMPCOUNT($SUM(1)) : − COMPIDS(id). 

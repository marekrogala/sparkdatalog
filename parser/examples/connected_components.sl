int N = 1768195.

declare Edge [int src: 0..N] (int sink).
declare Nodes [int n: 0..N].
declare Comp [int n: 0..N](int root).
declare CompIds (int id).
declare CompCount.

Comp(n, $Min(i))    :- NODES(n), i = n;
                    :- COMP(p, i), EDGE(p, n).
CompIds(id) :- Comp(_, id).
CompCount($Sum(1)) :- CompIds(id). 

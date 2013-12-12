const int N = 1768195.

declare Edge[int src:0..N](int sink).
declare Triangle[int x:0..N](int y, int z).
//Total (int cnt).

Triangle[x](y, z) :− Edge[x](y), x < y, Edge[y](z), y < z, Edge[x](z).
//Total($Sum(1)) :− Triangle(x, y, z). 


declare Edge[int s:0..9999] (int t, double dist).
declare Path[int t:0..9999] (double dist).

Path[t]($Min(d)) :- t = 1, d = 0.0;
                 :- Path[s](d1), Edge[s](t, d2), d = d1 + d2.

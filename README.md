s2g
===

SociaLite to Giraph compiler.

Currently, there is only parser available.

Requirements
------------
The following programs are currently required to run the parser: bnfc, alex, latex, happy, ghc

```
sudo apt-get install bnfc alex texlive-full happy ghc -y
```

How to run parser
-----------------

```
git@github.com:rogrog/s2g.git
cd s2g/parser
bnfc -m socialite.cf
make
./Testsocialite examples/shortest_paths.sl
```

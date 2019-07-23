# Data-Structures-and-Algorithms

## Dynamic Connectivity (Union-find)
**Task** : Our goal is to write a program to filter out extraneous pairs from the
			sequence: When the program reads a pair p q from the input, it should
			write the pair to the output only if the pairs it has seen to that point
			do not imply that p is connected to q. If the previous pairs do imply that
			p is connected to q, then the program should ignore the pair p q and proceed
			to read in the next pair.
**Input** : The input is a sequence of pairs of integers, where each integer
			represens an object of some type and we are to interpret the pair
			p q as meaning p is connected to q.
**Output** : 
**Assume** : We assume that "is connected to" is an equivalence relation:
			1. Reflexive: p is connected to p.
			2. Symmetric: if p is connected to q, then q is connected to p.
			3. Transitive: if p is connected to q and q is connected to r,
				then p is connected to r.
			An equivalence relation partition the objects into equivalence classes
			or connected components.
**code** : UF.java
**class** : UF
**data** : tinyUF.txt, mediumUF.txt, largeUF.txt
**last Update** : July 22, 2019
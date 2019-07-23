/**********************************************************************************
 * Union-find algorithm (Weighted quick-union by rank with path compression by halving.
 * Data files: tinyUF.txt, mediumUF.txt, largeUF.txt
 * @author Chaitanyakumar Shah
 * @date July 22, 2019
 ***********************************************************************************/

import java.util.Scanner;

/**
 * The {@code UF} class represents a union-find data type
 * (also known as the disjoint-sets data type).
 * It supports the union and find operations, along with a connected operation for
 * determining whether two sites are in the same component and a count operation that
 * returns the total number of components.
 * 
 * The union-find data type models connectivity among a set of n sites,
 * named 0 through n-1.
 * The is-connected-to relation must be an equivalence relation:
 * 1. Reflexive: p is connected to p.
 * 2. Symmetric: If p is connected to q, then q is connected to p.
 * 3. Transitive: If p is connected to q and q is connected to r,
 * 					then p is connected to r.
 * 
 * An equivalence relation partitions the sites into equivalence classes (or components).
 * In this case, two sites are in the same component if and only if they are connected.
 * Both sites and components are identified with integers between 0 and n-1.
 * Initially, there are n components, with each site in its own component.
 * The component identifier of a component (also known as the root, canonical element,
 * leader, or set representative is one of the sites in the component: two sites have
 * the same component identifier if and only if they are in the same component.
 * 1. union(p, q) adds a connection between the two sites p and q.
 * 		If p and q are in different components, then it replaces these two components
 * 		with a new component that is the union of the two.
 * 2. find(p) returns the component identifier of the component containing p.
 * 3. connected(p, q) returns true if both p and q are in the same component,
 * 		and false otherwise.
 * 4. count() returns the number of components.
 * 
 * The component identifier of a component can change only when the component itself
 * changes during a call to union - it cannot change during a call to find, connected,
 * or count.
 * 
 * This implementation uses weighted quick union by rank with path compression by
 * halving.
 * Initializing a data structure with n sites takes linear time.
 * Afterwards, the union, find, and connected operations take logarithmic time
 * (in the worst case) and the count operation takes constant time.
 * Moreover, the amortized time per union, find, and connected operation has inverse
 * Ackermann complexity.
 * For alternate implementation of the same API, see {@link QuickUnionUF}, 
 * {@link QuickFindUF}, and {@link WeightedQuickUnionUF}.
 */
public class UF {

	private int[] parent; // parent[i] = parent of i
	private byte[] rank; // rank[i] = rank of subtree rooted at i (never more than 31)
	private int count; // number of components

	private static Scanner scanner = new Scanner(System.in);

	//initialize union-find data structure with N objects (0 to N-1)
	public UF(int N) {
		if(N < 0) throw new IllegalArgumentException();
		count = N;
		parent = new int[N];
		rank = new byte[N];
		for(int i = 0; i < N; i++) {
			parent[i] = i;
			rank[i] = 0;
		}
	}

	// add connection between p and q
	public void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ) return;
		// make root of smaller rank point to root of larger rank
		if(rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
		else if(rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
		else {
			parent[rootQ] = rootP;
		    rank[rootP]++;
		}
        count--;
	}

	// are p and q in the same component?
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	// component identifier for p(0 to N-1)
	public int find(int p) {
		validate(p);
		while(p != parent[p]) {
			parent[p] = parent[parent[p]];
			p = parent[p];
		}
		return p;
	}

	// number of components
	public int count() {
		return count;
	}

	// validate that p is a valid index
	private void validate(int p) {
		int n = parent.length;
		if(p < 0 || p >= n) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
		}
	}

	public static void main(String[] args) {
		int N = scanner.nextInt();
		UF uf = new UF(N);
		while (scanner.hasNext()) {
			int p = scanner.nextInt();
			int q = scanner.nextInt();
			if(!uf.connected(p, q)) {
				uf.union(p, q);
				System.out.println(p + " " + q);
			}
		}
		System.out.println(uf.count() + " components");
	}

}


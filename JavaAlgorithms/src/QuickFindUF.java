/**
 * Quick-find algorithm.
 * Data files: tinyUF.txt, mediumUF.txt, largeUF.txt
 * @author Chaitanyakumar Shah
 * @date July 22, 2019
 */

import java.util.Scanner;

/**
 * The {@code QuickFindUF} class represents a union–find data type (also known as the
 * disjoint-sets data type). It supports the union and find operations, along with a
 * connected operation for determining whether two sites are in the same component and
 * a count operation that returns the total number of components.
 * <p>
 * The union–find data type models connectivity among a set of n sites, named 0 through
 * n–1. The is-connected-to relation must be an equivalence relation:
 * <ul>
 * <li>Reflexive: p is connected to p.
 * <li>Symmetric: If p is connected to q, then q is connected to p.
 * <li>Transitive: If p is connected to q and q is connected to r,
 * 					then p is connected to r.
 * </ul>
 * <p>
 * An equivalence relation partitions the sites into equivalence classes (or components).
 * In this case, two sites are in the same component if and only if they are connected.
 * Both sites and components are identified with integers between 0 and n–1. 
 * Initially, there are n components, with each site in its own component.
 * The component identifier of a component (also known as the root, canonical element,
 * leader, or set representative) is one of the sites in the component: two sites have
 * the same component identifier if and only if they are in the same component.
 * <ul>
 * <li>union(p, q) adds a connection between the two sites p and q.
 *     If p and q are in different components, then it replaces
 *     these two components with a new component that is the union of the two.
 * <li>find(p) returns the component identifier of the component containing p.
 * <li>connected(p, q) returns true if both p and q are in the same component,
 * 		and false otherwise.
 * <li>count() returns the number of components.
 * </ul>
 * <p>
 * The component identifier of a component can change only when the component itself
 * changes during a call to union — it cannot change during a call to find, connected,
 * or count.
 * <p>
 * This implementation uses quick find.
 * Initializing a data structure with n sites takes linear time.
 * Afterwards, the find, connected, and count operations take constant time but the
 * union operation takes linear time.
 * For alternate implementations of the same API, see
 * {@link UF}, {@link QuickUnionUF}, and {@link WeightedQuickUnionUF}.
 */
public class QuickFindUF {

	private int[] id; // id[i] = component identifier of i
	private int count; // number of components

	private static Scanner scanner = new Scanner(System.in);

	/**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own 
     * component.
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
	public QuickFindUF(int N) {
		if(N < 0) throw new IllegalArgumentException();
		count = N;
		id = new int[N];
		for(int i = 0; i < N; i++) id[i] = i;
	}

	/**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
	public int count() {
		return count;
	}

	/**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
	public int find(int p) {
		validate(p);
		return id[p];
	}

	// validate that p is a valid index
	private void validate(int p) {
		int n = id.length;
		if(p < 0 || p >= n) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
		}
	}

	/**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
	public boolean connected(int p, int q) {
		validate(p);
		validate(q);
		return id[p] == id[q];
	}

	/**
     * Merges the component containing site {@code p} with the 
     * the component containing site {@code q}.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
	public void union(int p, int q) {
		validate(p);
		validate(q);
		int pid = id[p]; // needed for correctness
		int qid = id[q]; // to reduce the number of array accesses
		// p and q are already in the same component
		if(pid == qid) return;
		for(int i = 0; i < id.length; i++)
			if(id[i] == pid) id[i] = qid;
		count--;
	}

	/**
     * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input, 
     * where each integer represents some site;
     * if the sites are in different components, merge the two components
     * and print the pair to standard output.
     *
     * @param args the command-line arguments
     */
	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		int N = scanner.nextInt();
		QuickFindUF uf = new QuickFindUF(N);
		while (scanner.hasNext()) {
			int p = scanner.nextInt();
			int q = scanner.nextInt();
			if(!uf.connected(p, q)) {
				uf.union(p, q);
				//System.out.println(p + " " + q);
			}
		}
		System.out.println(uf.count() + " components");
		long now = System.currentTimeMillis();
		System.out.println("elapsed time = " + (now-start));
	}

}

/**
 * Output:
 * 
 * % java QuickFindUF < tinyUF.txt
 * 2 components
 * elapsed time = 8
 * 
 * % java QuickFindUF < mediumUF.txt
 * 3 components
 * elapsed time = 60
 * 
 * % java QuickFindUF < largeUF.txt
 * 6 components
 * elapsed time = 1890548
 */
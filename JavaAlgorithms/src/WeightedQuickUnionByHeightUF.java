/**
 * Weighted Quick Union By Height Union-Find algorithm.
 * Data files: tinyUF.txt, mediumUF.txt, largeUF.txt
 * @author Chaitanyakumar Shah
 * @date July 22, 2019
 */

 import java.util.Scanner;

 /**
  * The {@code WeightedQuickUnionByHeightUF} class represents a union-find
  * data structure.
  * It supports the union and find operations, along with methods for
  * determining whether two sites are in the same component and the total
  * number of components.
  *
  * This implementation uses weighted quick union by height (without path
  * compression).
  * Initializing a data structure with n sites takes linear time.
  * Afterwards, union, find, and connected take logarithmic time (in the
  * worst case) and count takes constant time.
  */
public class WeightedQuickUnionByHeightUF {

	private int[] parent; // parent[i] = parent of i
	private int[] height; // height[i] = height of subtree rooted at i
	private int count; // number of components

	private static Scanner scanner = new Scanner(System.in);

	/**
	 * Initializes an empty union-find data structure with {@code n} sites
	 * {@code 0} through {@code n-1}. Each site is initially in its own
	 * component.
	 *
	 * @param n the number of sites
	 * @throws IllegalArgumentException if {@code n < 0}
	 */
	public WeightedQuickUnionByHeightUF(int n) {
		if (n < 0) throw new IllegalArgumentException();
		count = n;
		parent = new int[n];
		height = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			height[i] = 0;
		}
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
	 * Returns the component identifier for the component containing site
	 * {@code p}.
	 *
	 * @param p the integer representing one site
	 * @return the component identifier for the component containing site
	 * 			{@code p}.
	 * @throws IllegalArgumentException unless {@code 0 <= p < n}
	 */
	public int find(int p) {
		validate(p);
		while (p != parent[p]) p = parent[p];
		return p;
	}

	// validate that p is a valid index
	private void validate(int p) {
		int n = parent.length;
		if (p < 0 || p >=n) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
		}
	}

	/**
	 * Returns true if the two sites are in the same component.
	 *
	 * @param p the integer representing one site
	 * @param q the integer representing the other site
	 * @return {@code true} if the two sites {@code p} and {@code q} are in
	 *			the same component; {@code false} otherwise
	 * @throws IllegalArgumentException unless both
	 *			{@code 0 <= p < n} and {@code 0 <= q < n}
	 */
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	/**
	 * Merges the component containing site {@code p} with the component
	 * containing site {@code q}.
	 *
	 * @param p the integer representing one site
	 * @param q the integer representing the other site
	 * @throws IllegalArgumentException unless both
	 *			{@code 0 <= p < n} and {@code 0 <= q < n}
	 */
	public void union(int p, int q) {
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		// make shorter root point to taller one
		if (height[i] < height[j]) parent[i] = j;
		else if (height[i] > height[j]) parent[j] = i;
		else {
			parent[j] = i;
			height[i]++;
		}
		count--;
	}

	/**
	 * Reads in a sequence of pairs of integers (between 0 and n-1) from
	 * standard input, where each integer represents some site;
	 * if the sites are in different components, merge the two components
	 * and print the pair to standard output.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		int n = scanner.nextInt();
		WeightedQuickUnionByHeightUF uf = new WeightedQuickUnionByHeightUF(n);
		while(scanner.hasNext()) {
			int p = scanner.nextInt();
			int q = scanner.nextInt();
			if(uf.connected(p, q)) continue;
			uf.union(p, q);
			//System.out.println(p + " " + q);
		}
		System.out.println(uf.count() + " components");
		long now = System.currentTimeMillis();
		System.out.println("elapsed time = " + (now-start));
	}

}

/**
 * Output:
 * 
 * % java QuickUnionUF < tinyUF.txt
 * 2 components
 * elapsed time = 6
 * 
 * % java QuickUnionUF < mediumUF.txt
 * 3 components
 * elapsed time = 25
 * 
 * % java QuickUnionUF < largeUF.txt
 * 6 components
 * elapsed time = 3439
 */
/**
 * Quick-union with path compression (but no weighting by size or rank).
 * Data files: tinyUF.txt, mediumUF.txt, largeUF.txt
 * @author Chaitanyakumar Shah
 * @date July 23, 2019
 */

import java.util.Scanner;

/**
 *  The {@code QuickUnionPathCompressionUF} class represents a
 *  union–find data structure.
 *  It supports the <em>union</em> and <em>find</em> operations, along with
 *  methods for determining whether two sites are in the same component
 *  and the total number of components.
 *  <p>
 *  This implementation uses quick union (no weighting) with full path compression.
 *  Initializing a data structure with <em>n</em> sites takes linear time.
 *  Afterwards, <em>union</em>, <em>find</em>, and <em>connected</em> take
 *  logarithmic amortized time <em>count</em> takes constant time.
 */
public class QuickUnionPathCompressionUF {
    
	private int[] id;    // id[i] = parent of i
    private int count;   // number of components
    
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Initializes an empty union–find data structure with n isolated components 0 through n-1.
     * @param n the number of sites
     * @throws java.lang.IllegalArgumentException if n < 0
     */
    public QuickUnionPathCompressionUF(int n) {
    	if(n < 0) throw new IllegalArgumentException();
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
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
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one object
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        int root = p;
        while (root != id[root])
            root = id[root];
        while (p != root) {
            int newp = id[p];
            id[p] = root;
            p = newp;
        }
        return root;
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
        return find(p) == find(q);
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
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        id[rootP] = rootQ;
        count--;
    }

    /**
     * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input, 
     * where each integer represents some object;
     * if the sites are in different components, merge the two components
     * and print the pair to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    	final long start = System.currentTimeMillis();
        int n = scanner.nextInt();
        QuickUnionPathCompressionUF uf = new QuickUnionPathCompressionUF(n);
        while (scanner.hasNext()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            if (uf.connected(p, q)) continue;
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
 * elapsed time = 4
 * 
 * % java QuickUnionUF < mediumUF.txt
 * 3 components
 * elapsed time = 27
 * 
 * % java QuickUnionUF < largeUF.txt
 * 6 components
 * elapsed time = 3466
 */
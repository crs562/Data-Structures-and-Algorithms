/**
 * Repeatedly add random edges (with replacement) to a graph on a vertices until the
 * graph is connected. Report the mean and standard deviation of the number of edges
 * added.
 * 
 * When n is large, Erdos and Renyi proved that after about 1/2 n ln n additions, the
 * graph will have a 50/50 chance of being connected.
 * 
 * Execution: java ErdosRenyi n trials
 * Dependencies: UF.java
 * @author Chaitanyakumar Shah
 * @date July 23, 2019
 */

import java.util.Random;

public class ErdosRenyi {
	
	private static Random random; // pseudo-random number generator
	private static long seed; // pseudo-random number generator seed
	
	// static initializer
	static {
		seed = System.currentTimeMillis();
		random = new Random(seed);
	}
	
	// number of random edges (with replacement) needed for an n-vertex
	// graph to become connected
	public static int count(int n) {
		if(n <= 0) throw new IllegalArgumentException("argument must be positive: " + n);
		int edges = 0;
		UF uf = new UF(n);
		while (uf.count() > 1) {
			int i = random.nextInt(n);
			int j = random.nextInt(n);
			uf.union(i, j);
			edges++;
		}
		return edges;
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]); // number of vertices
		int trials = Integer.parseInt(args[1]); // number of trials
		int[] edges = new int[trials]; // record statistics
		// repeat the experiment trials times
		for (int t = 0; t < trials; t++) edges[t] = count(n);
		// report statistics
		System.out.println("1/2 n ln n = " + 0.5*n*Math.log(n));
		System.out.println("mean = " + StdStats.mean(edges));
		System.out.println("stddev = " + StdStats.stddev(edges));
	}

}

/**
 * Output: trials = 1000
 * 
 *      n | 1/2 n ln n | mean # edges | stddev # edges |
 * -------|------------|--------------|----------------|
 *    100 | 230        | 260          | 65             |
 *    200 | 530        | 595          | 126            |
 *    400 | 1198       | 1315         | 271            |
 *    800 | 2673       | 2908         | 515            |
 *   1600 | 5902       | 6366         | 1040           |
 *   3200 | 12913      | 13865        | 1991           |
 *   6400 | 28044      | 29829        | 4055           |
 *  12800 | 60536      | 63995        | 7818           |
 *  25600 | 129924     | 136572       | 15282          |
 *  51200 | 277593     | 293759       | 34838          |
 * 102400 | 590676     | 619969       | 64500          |
 * 204800 | 1252330    | 1302538      | 124331         |
 * 409600 | 2646617    | 2700000      | 4306250        |
 */
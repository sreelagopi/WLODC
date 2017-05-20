package net.atos.awl.tes;

import java.util.Arrays;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] a = new int[]{5};
         int n = a.length;
		Arrays.sort(a);
		int m = a[n - 1];
		int ans = m;
		for (int k = 1; k <= m; k++) {
			int divs = 0;
			for (int x : a) {
				divs += (x - 1) / k;
			}
			ans = Math.min(ans, k + divs);
		}
		System.out.println(ans);
		

	}

}

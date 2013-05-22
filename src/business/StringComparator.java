/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author M
 */
public class StringComparator {
    
    public static double compare(String s1, String s2) {
	String words1[] = s1.split(" ");
	String words2[] = s2.split(" ");
	
	int levSum = 0;
	int m = words1.length;
	int n = words2.length;
	for (int i = 0; i < m; i++) {
	    for (int j = 0; j < n; j++) {
		levSum += levenshteinDistance(words1[i], words2[j]);
	    }
	}
	double factor = 2.0 * levSum / (m + n);
	
	return factor;
    }
    
    private static int levenshteinDistance(String s1, String s2) {
	int lengthS1 = s1.length();
	int lengthS2 = s2.length();
	int tab[][] = new int[lengthS1 + 1][lengthS2 + 1];
	int i, j, diff;
	for (i = 0; i <= lengthS1; i++) {
	    tab[i][0] = i;
	}
	for (j = 0; j <= lengthS2; j++) {
	    tab[0][j] = j;
	}
	for (i = 1; i <= lengthS1; i++) {
	    for (j = 1; j <= lengthS2; j++) {
		if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
		    diff = 0;
		} else {
		    diff = 1;
		}
		tab[i][j] = minimum(tab[i - 1][j] + 1, // insertion
			tab[i][j - 1] + 1, // deletion
			tab[i - 1][j - 1] + diff); // substitution
	    }
	}
	return tab[lengthS1][lengthS2];
    }
    
    private static int minimum(int a, int b, int c) {
	int mi = a;
	if (b < mi) {
	    mi = b;
	}
	if (c < mi) {
	    mi = c;
	}
	return mi;
    }
}

package handlers;

public class ArrayHandler {
	
	public static int[][] copyOf(int[][] original, int s, int f) {
		int[][] copy = new int[original.length][];
	    for(int i = s; i < f; i++)
	        copy[i] = original[i].clone();
		return copy;
	}
	
}

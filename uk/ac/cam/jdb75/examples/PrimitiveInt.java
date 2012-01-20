package uk.ac.cam.jdb75.examples;

class PrimitiveInt {
	public static void main(String[] args) {
		int z = 1<<5;
		int x = 1<<5;
		int y = 1<<6;
		int right = (z & x) >> 5;
		int wrong = (z & y) >> 5;
		System.out.println(right);
		System.out.println(wrong);
	}
}
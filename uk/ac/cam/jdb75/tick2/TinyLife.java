package uk.ac.cam.jdb75.tick2;

public class TinyLife {
	public static void main(String[] args) {
		long world = 0x20A0600000000000L;
		System.out.println(countNeighbours(world, 6, 6));
	}

	public static boolean getCell(long world, int col, int row) {
		return (col > 7 | row > 7 | col < 0 | row < 0) ? false : PackedLong.get(world, (col + 8*row));
	}

	public static long setCell(long world, int col, int row, boolean value) {
		return (col > 7 | row > 7 | col < 0 | row < 0) ? world : PackedLong.set(world, (col + 8*row), value);
	}

	public static void print(long world) {
		System.out.println("-");
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				System.out.print(getCell(world, col, row) ? '#' : "_");
			}
			System.out.println();
		}

	}

	public static int countNeighbours(long world, int col, int row) {
		int neighbours = 0; 	
		for (int c = -1; c <= 1; c++) {
			System.out.println(col+c);
			//neighbours += (getCell(world, col, row)) ? 1 : 0; 
			//System.out.println("checked " + col + " " + row);
		}

		return neighbours;
	}

	public static boolean computeCell(long world, int col, int row) {
		return true;
	}

	public static long nextGeneration(long world) {
		return 1L;
	}
}
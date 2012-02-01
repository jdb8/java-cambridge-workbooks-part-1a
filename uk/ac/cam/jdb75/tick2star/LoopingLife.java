package uk.ac.cam.jdb75.tick2star;

public class LoopingLife {
	public static void main(String[] args) throws Exception {
 		findLoop(Long.decode(args[0]));
	}

	public static boolean getCell(long world, int col, int row) {
		return (col > 7 || row > 7 || col < 0 || row < 0) ? false : PackedLong.get(world, (col + 8*row));
	}

	public static long setCell(long world, int col, int row, boolean value) {
		return (col > 7 || row > 7 || col < 0 || row < 0) ? world : PackedLong.set(world, (col + 8*row), value);
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
		
		// increase neighbours by 1 if getCell is true for each adjacent square 
		neighbours += (getCell(world, col-1, row-1)) ? 1 : 0;
		neighbours += (getCell(world, col, row-1)) ? 1 : 0;
		neighbours += (getCell(world, col+1, row-1)) ? 1 : 0;
		neighbours += (getCell(world, col-1, row)) ? 1 : 0;
		neighbours += (getCell(world, col+1, row)) ? 1 : 0;
		neighbours += (getCell(world, col-1, row+1)) ? 1 : 0;
		neighbours += (getCell(world, col, row+1)) ? 1 : 0;
		neighbours += (getCell(world, col+1, row+1)) ? 1 : 0;

		return neighbours;
	}

	public static boolean computeCell(long world, int col, int row) {

		// liveCell is true if the cell at position (col,row) in world is live
 		boolean liveCell = getCell(world, col, row);

 		// neighbours is the number of live neighbours to cell (col,row)
 		int neighbours = countNeighbours(world, col, row);

 		// we will return this value at the end of the method to indicate whether 
 		// cell (col,row) should be live in the next generation
 		boolean nextCell = false;
	
 		//A live cell with less than two neighbours dies (underpopulation)
 		if (neighbours < 2) {
 			nextCell = false;
		}
 
 		//A live cell with two or three neighbours lives (a balanced population)
 		if (liveCell && neighbours == 2 || liveCell && neighbours == 3) {
 			nextCell = true;
 		}

 		//A live cell with with more than three neighbours dies (overcrowding)
 		if (neighbours > 3) {
 			nextCell = false;
 		}

 		//A dead cell with exactly three live neighbours comes alive
 		if (!liveCell && neighbours == 3) {
 			nextCell = true;
 		}
	
 		return nextCell;
	}

	public static long nextGeneration(long world) {
		long newWorld = 0L;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				newWorld = setCell(newWorld, col, row, computeCell(world, col, row));
			}
		}
		return newWorld;
	}

	public static void findLoop(long world) throws Exception {
		long[] loopArray = new long[100];
		loopArray[0] = world;		

		for (int i = 1; i<100; i++) {

			loopArray[i] = nextGeneration(loopArray[i-1]);

			for (int j = 0; j<i; j++) {
				if (loopArray[i] == loopArray[j]) {
					System.out.println(j + " to " + (i-1));
					return;
				}
			}
		}
		System.out.println("No loops found");
		return;		
	}
}
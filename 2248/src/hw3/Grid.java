package hw3;
/**
 * @author Rahul Sudev
 */

import api.Tile;

/**
 * Represents the game's grid.
 */
public class Grid {
	
	//Instance variable holding numbrt of rows
	private int heightOfGrid;
	
	//Instance variable holding number of columns
	private int widthOfGrid;
	
	// 2D array representing the grid
	private Tile[][] gameGrid;
	
	/**
	 * Creates a new grid.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public Grid(int width, int height) {
		heightOfGrid = height;
		widthOfGrid = width;
		gameGrid = new Tile[width][height];
	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width
	 */
	public int getWidth() {
		return widthOfGrid;
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height
	 */
	public int getHeight() {
		return heightOfGrid;
	}

	/**
	 * Gets the tile for the given column and row.
	 * 
	 * @param x the column
	 * @param y the row
	 * @return
	 */
	public Tile getTile(int x, int y) {
		return gameGrid[x][y];
	}

	/**
	 * Sets the tile for the given column and row. Calls tile.setLocation().
	 * 
	 * @param tile the tile to set
	 * @param x    the column
	 * @param y    the row
	 */
	public void setTile(Tile tile, int x, int y) {
		tile.setLocation(x, y);
		gameGrid[x][y]= tile;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int y=0; y<getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			str += "[";
			for (int x=0; x<getWidth(); x++) {
				if (x > 0) {
					str += ",";
				}
				str += getTile(x, y);
			}
			str += "]";
		}
		return str;
	}
}

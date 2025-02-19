package hw3;
/**
 * @author Rahul Sudev
 */

import java.util.Random;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Tile;

/**
 * Class that models a game.
 */
public class ConnectGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	
	//Variable that holds minimum tile level
	private int minVal;
	
	//variable that holds maximum tile level
	private int maxVal;
	
	
	//holds the width of the grid
	private int widthOfGrid;
	
	//holds the height of the grid
	private int heightOfGrid;
	
	//2D array holding the grid
	private Grid grid;
	
	// object holding rand in the constructor
	private Random rand;
	
	//Array list holding Tiles selected by user
	private ArrayList<Tile> tileList;
	
	//Variable keeping count of player score
	private long playerScore;


	
	
	/**
	 * Constructs a new ConnectGame object with given grid dimensions and minimum
	 * and maximum tile levels.
	 * 
	 * @param width  grid width
	 * @param height grid height
	 * @param min    minimum tile level
	 * @param max    maximum tile level
	 * @param rand   random number generator
	 */
	public ConnectGame(int width, int height, int min, int max, Random rand) {
		minVal = min;
		maxVal = max;
		widthOfGrid = width;
		heightOfGrid = height;
		grid = new Grid(width,height);
		this.rand =rand;
		playerScore =0;
	}

	/**
	 * Gets a random tile with level between minimum tile level inclusive and
	 * maximum tile level exclusive. For example, if minimum is 1 and maximum is 4,
	 * the random tile can be either 1, 2, or 3.
	 * <p>
	 * DO NOT RETURN TILES WITH MAXIMUM LEVEL
	 * 
	 * @return a tile with random level between minimum inclusive and maximum
	 *         exclusive
	 */
	public Tile getRandomTile() {
		Tile c = new Tile(rand.nextInt((maxVal - minVal)) + minVal);
		return c;
	}

	/**
	 * Regenerates the grid with all random tiles produced by getRandomTile().
	 */
	public void radomizeTiles() {
		for(int y = 0; y< heightOfGrid; y++) {
			for(int x = 0; x<widthOfGrid;x++) {
				grid.setTile(getRandomTile(), x, y);
				
			}
		}
		
		
	}

	/**
	 * Determines if two tiles are adjacent to each other. The may be next to each
	 * other horizontally, vertically, or diagonally.
	 * 
	 * @param t1 one of the two tiles
	 * @param t2 one of the two tiles
	 * @return true if they are next to each other horizontally, vertically, or
	 *         diagonally on the grid, false otherwise
	 */
	public boolean isAdjacent(Tile t1, Tile t2) {
		for(int j = 0; j<heightOfGrid; j++) {
			for(int i = 0; i<widthOfGrid; i++) {
				if(grid.getTile(i,j)== t1) {
					//checking left
					if (j - 1 >= 0 && grid.getTile(i, j-1) == t2) { 
	                    return true;
					}
	                //checking right
	                else if (j + 1 < widthOfGrid && grid.getTile(i, j+1) == t2) { 
	                    return true;
	                }
	                //checking up
	                else if (i - 1 >=0 && grid.getTile(i-1, j) == t2) { 
	                    return true;
	                }
	                //checking down
	                else if (i + 1 < heightOfGrid && grid.getTile(i+1, j) == t2) { 
	                    return true;
	                }
					//checking upper left 
	                else if(i-1>=0 && j-1>=0 && grid.getTile(i-1, j-1) == t2) {
	                	return true;
	                }
					//checking lower right
	                else if(i+1<heightOfGrid && j+1<widthOfGrid && grid.getTile(i+1, j+1)==t2) {
	                	return true;
	                }
					//checking upper right
	                else if(i-1>=0 && j+1<widthOfGrid && grid.getTile(i-1, j+1) == t2) {
	                	return true;
	                }
					//checking lower left
	                else if(i+1<heightOfGrid && j-1>=0 && grid.getTile(i+1, j-1)== t2) {
	                	return true;
	                }
	                else {
	                	return false;
	                }
	                
				}
			}
		}
		return false;
	}

	/**
	 * Indicates the user is trying to select (clicked on) a tile to start a new
	 * selection of tiles.
	 * <p>
	 * If a selection of tiles is already in progress, the method should do nothing
	 * and return false.
	 * <p>
	 * If a selection is not already in progress (this is the first tile selected),
	 * then start a new selection of tiles and return true.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return true if this is the first tile selected, otherwise false
	 */
	public boolean tryFirstSelect(int x, int y) {
		if(this.tileList== null) {
			tileList = new ArrayList<Tile>();
			tileList.add(grid.getTile(x,y));
			grid.getTile(x, y).setSelect(true);
			return true;
		}
		return false;
	}

	/**
	 * Indicates the user is trying to select (mouse over) a tile to add to the
	 * selected sequence of tiles. The rules of a sequence of tiles are:
	 * 
	 * <pre>
	 * 1. The first two tiles must have the same level.
	 * 2. After the first two, each tile must have the same level or one greater than the level of the previous tile.
	 * </pre>
	 * 
	 * For example, given the sequence: 1, 1, 2, 2, 2, 3. The next selected tile
	 * could be a 3 or a 4. If the use tries to select an invalid tile, the method
	 * should do nothing. If the user selects a valid tile, the tile should be added
	 * to the list of selected tiles.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 */
	public void tryContinueSelect(int x, int y) {
		if(tileList.size()==1) {
	
			 if((tileList.get(0)).getLevel()== (grid.getTile(x, y).getLevel())){
				tileList.add(grid.getTile(x, y));
				grid.getTile(x, y).setSelect(true);
			}
		
	
		}
		else if(tileList.size()>=2) {
			if(tileList.get(tileList.size()-2)== grid.getTile(x, y)) {
				tileList.get(tileList.size()-1).setSelect(false);
				tileList.remove(tileList.size()-1);
			} else if((tileList.get(tileList.size()-1).getLevel() == grid.getTile(x, y).getLevel())||(grid.getTile(x, y).getLevel()==(tileList.get(tileList.size()-1).getLevel())+1)){
				tileList.add(grid.getTile(x, y));
				grid.getTile(x, y).setSelect(true);
			}
		
		
			

	
		}
		

	}

	/**
	 * Indicates the user is trying to finish selecting (click on) a sequence of
	 * tiles. If the method is not called for the last selected tile, it should do
	 * nothing and return false. Otherwise it should do the following:
	 * 
	 * <pre>
	 * 1. When the selection contains only 1 tile reset the selection and make sure all tiles selected is set to false.
	 * 2. When the selection contains more than one block:
	 *     a. Upgrade the last selected tiles with upgradeLastSelectedTile().
	 *     b. Drop all other selected tiles with dropSelected().
	 *     c. Reset the selection and make sure all tiles selected is set to false.
	 * </pre>
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return return false if the tile was not selected, otherwise return true
	 */
	public boolean tryFinishSelection(int x, int y) {
		if(tileList.size()==1 && tileList.get(tileList.size()-1)== grid.getTile(x, y)) {
			unselect(x,y);
			return true;
			
		}

		if(tileList.size()>=1) {
			for(int j = 0; j<tileList.size();j++) {
				playerScore+= tileList.get(j).getValue();
			}
			upgradeLastSelectedTile();
			dropSelected();

			for(int i = 0; i< tileList.size();i++) {
				tileList.get(i).setSelect(false);
	
				
			}
			tileList.clear();
			return true;
		}
		return false;
	}

	/**
	 * Increases the level of the last selected tile by 1 and removes that tile from
	 * the list of selected tiles. The tile itself should be set to unselected.
	 * <p>
	 * If the upgrade results in a tile that is greater than the current maximum
	 * tile level, both the minimum and maximum tile level are increased by 1. A
	 * message dialog should also be displayed with the message "New block 32,
	 * removing blocks 2". Not that the message shows tile values and not levels.
	 * Display a message is performed with dialogListener.showDialog("Hello,
	 * World!");
	 */
	public void upgradeLastSelectedTile() {
		tileList.get(tileList.size()-1).setLevel((tileList.get(tileList.size()-1)).getLevel()+1);
		if(maxVal<tileList.get(tileList.size()-1).getLevel()+1){
			maxVal++;
			minVal++;
			dialogListener.showDialog("New block"+Math.pow(2, maxVal)+",removing blocks"+Math.pow(2,minVal-1));
			dropLevel(minVal-1);
		}
	}

	/**
	 * Gets the selected tiles in the form of an array. This does not mean selected
	 * tiles must be stored in this class as a array.
	 * 
	 * @return the selected tiles in the form of an array
	 */
	public Tile[] getSelectedAsArray() {
		Tile[] tileArray = new Tile[tileList.size()];
		for(int i = 1; i<tileList.size();i++) {
			tileArray[i] = tileList.get(i);
		}
		return tileArray;
	}

	/**
	 * Removes all tiles of a particular level from the grid. When a tile is
	 * removed, the tiles above it drop down one spot and a new random tile is
	 * placed at the top of the grid.
	 * 
	 * @param level the level of tile to remove
	 */
	public void dropLevel(int level) {
		for(int j = 0; j<heightOfGrid; j++) {
			for(int i = 0; i<widthOfGrid;i++) {
				if(grid.getTile(i, j).getLevel()== level) {
					for(int p =j; p>0;p--) {
						grid.setTile(grid.getTile(i,p-1), i, p);
					}
					grid.setTile(getRandomTile(), i, 0);
				}
			}
		}
		
	}

	/**
	 * Removes all selected tiles from the grid. When a tile is removed, the tiles
	 * above it drop down one spot and a new random tile is placed at the top of the
	 * grid.
	 */
	public void dropSelected() {
		for(int i =0; i<widthOfGrid;i++) {
			for(int j =0;j<heightOfGrid;j++) {
				for(int x =0; x<tileList.size();x++) {
					if(grid.getTile(i,j)==tileList.get(x)) {
						for(int p =j; p>0;p--) {
							grid.setTile(grid.getTile(i,p-1), i, p);
						}
						grid.setTile(getRandomTile(), i, 0);
					}
				}
			}
		}
		
	}

	/**
	 * Remove the tile from the selected tiles.
	 * 
	 * @param x column of the tile
	 * @param y row of the tile
	 */
	public void unselect(int x, int y) {
		grid.getTile(x, y).setSelect(false);
		tileList.remove(tileList.indexOf(grid.getTile(x, y)));
	}

	/**
	 * Gets the player's score.
	 * 
	 * @return the score
	 */
	public long getScore() {
		return playerScore;
	}

	/**
	 * Gets the game grid.
	 * 
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * Gets the minimum tile level.
	 * 
	 * @return the minimum tile level
	 */
	public int getMinTileLevel() {
		return minVal;
	}

	/**
	 * Gets the maximum tile level.
	 * 
	 * @return the maximum tile level
	 */
	public int getMaxTileLevel() {
		return maxVal;
	}

	/**
	 * Sets the player's score.
	 * 
	 * @param score number of points
	 */
	public void setScore(long score) {
		playerScore = score;
	}

	/**
	 * Sets the game's grid.
	 * 
	 * @param grid game's grid
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Sets the minimum tile level.
	 * 
	 * @param minTileLevel the lowest level tile
	 */
	public void setMinTileLevel(int minTileLevel) {
		minVal = minTileLevel;
	}

	/**
	 * Sets the maximum tile level.
	 * 
	 * @param maxTileLevel the highest level tile
	 */
	public void setMaxTileLevel(int maxTileLevel) {
		maxVal = maxTileLevel;
	}
	


	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Save the game to the given file path.
	 * 
	 * @param filePath location of file to save
	 */
	public void save(String filePath) {
		GameFileUtil.save(filePath, this);
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 * @throws FileNotFoundException 
	 */
	public void load(String filePath) throws FileNotFoundException {
		GameFileUtil.load(filePath, this);
	}
}

package hw3;
/**
 * @author Rahul Sudev
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import api.Tile;

/**
 * Utility class with static methods for saving and loading game files.
 */
public class GameFileUtil {
	/**
	 * Saves the current game state to a file at the given file path.
	 * <p>
	 * The format of the file is one line of game data followed by multiple lines of
	 * game grid. The first line contains the: width, height, minimum tile level,
	 * maximum tile level, and score. The grid is represented by tile levels. The
	 * conversion to tile values is 2^level, for example, 1 is 2, 2 is 4, 3 is 8, 4
	 * is 16, etc. The following is an example:
	 * 
	 * <pre>
	 * 5 8 1 4 100
	 * 1 1 2 3 1
	 * 2 3 3 1 3
	 * 3 3 1 2 2
	 * 3 1 1 3 1
	 * 2 1 3 1 2
	 * 2 1 1 3 1
	 * 4 1 3 1 1
	 * 1 3 3 3 3
	 * </pre>
	 * 
	 * @param filePath the path of the file to save
	 * @param game     the game to save
	 */
	public static void save(String filePath, ConnectGame game) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(game.getGrid().getWidth()+" "+game.getGrid().getHeight()+" "+game.getMinTileLevel()+" "+game.getMaxTileLevel()+" "+game.getScore());
			writer.write("\n");
			for(int i =0; i<game.getGrid().getHeight();i++) {
				for(int j =0; j<game.getGrid().getWidth();j++) {
					if(j<game.getGrid().getWidth()-1) {
					writer.write(game.getGrid().getTile(j, i).getLevel()+" ");
					}
					else {
						writer.write(game.getGrid().getTile(j, i).getLevel() + "");
						
					}
				}
				if (i < game.getGrid().getHeight()-1) {
					writer.write("\n");
				}
			}
			
			
			
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * <p>
	 * See the save() method for the specification of the file format.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 * @throws FileNotFoundException 
	 */
	public static void load(String filePath, ConnectGame game) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scnr = new Scanner(file);
		int j = 0;
		String line = scnr.nextLine();
		String[] tokens = line.split(" ");
		game.setMinTileLevel(Integer.parseInt(tokens[2]));
		game.setMaxTileLevel(Integer.parseInt(tokens[3]));
		Grid grid = new Grid(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));
		game.setScore(Integer.parseInt(tokens[4]));
		System.out.println();
		while(scnr.hasNextLine()) {
			String line1 = scnr.nextLine();
			//System.out.println(line1);
			String[] tokens1 = line1.split(" ");
			for(int i = 0;i < tokens1.length;i++) {
				Tile tile = new Tile(Integer.parseInt(tokens1[i]));
			//	System.out.print(Integer.parseInt(tokens[i]) + " ");
				tile.setLocation(i, j);
				grid.setTile(tile, i, j);
			}
			j++;
		//	System.out.println();
		}
		game.setGrid(grid);
		}
	}


// Samuel Voor
// 3971335
// COP 3503, Spring 2023

import java.io.*;
import java.util.*;

public class Pathogen
{
	// Used to toggle "animated" output on and off (for debugging purposes).
	private static boolean animationEnabled = false;

	// "Animation" frame rate (frames per second).
	private static double frameRate = 4.0;

	// Setters. Note that for testing purposes you can call enableAnimation()
	// from your backtracking method's wrapper method (i.e., the first line of
	// your public findPaths() method) if you want to override the fact that the
	// test cases are disabling animation. Just don't forget to remove that
	// method call before submitting!
	public static void enableAnimation() { Pathogen.animationEnabled = true; }
	public static void disableAnimation() { Pathogen.animationEnabled = false; }
	public static void setFrameRate(double fps) { Pathogen.frameRate = fps; }

	// Maze constants.
	private static final char WALL       = '#';
	private static final char PERSON     = '@';
	private static final char EXIT       = 'e';
	private static final char BREADCRUMB = '.';
	private static final char SPACE      = ' ';
	private static final char VIRUS		 = '*';	

	// Takes a 2D char maze and returns true if it can find a path from the
	// starting position to the exit. Assumes the maze is well-formed according
	// to the restrictions above.


    // New method to find all paths in the maze
	public static HashSet<String> findPaths(char[][] maze) 
	{
		// maze dimensions
        int height = maze.length;
        int width = maze[0].length;

		// visited array, initialized to an empty maze
        char[][] visited = new char[height][width];
        for (int i = 0; i < height; i++)
            Arrays.fill(visited[i], SPACE);

        int startRow = -1;
        int startCol = -1;

		// find starting position of person in the maze
        for (int i = 0; i < height; i++) 
		{
            for (int j = 0; j < width; j++) 
			{
                if (maze[i][j] == PERSON) 
				{
                    startRow = i;
                    startCol = j;
                }
            }
        }

		    // Check if the starting position was found
			// if not found return an empty set of paths
			if (startRow == -1 || startCol == -1) 
			{
				return new HashSet<>();
			}
		
		// initialize set of paths and current path
        HashSet<String> paths = new HashSet<>();
        StringBuilder currentPath = new StringBuilder();

		// call findPaths method recursively
        findPaths(maze, visited, startRow, startCol, height, width, currentPath, paths);
		
		// return set of paths found
        return paths;
    }

	// recursive method to find all paths from current position of person to the exit
	public static void findPaths(char[][] maze, char[][] visited, int currentRow, int currentCol,
	 int height, int width, StringBuilder currentPath, HashSet<String> paths) 
	{

		// if the current position is the exit add the current path to the set of paths
		if (visited[currentRow][currentCol] == EXIT)
		{
			paths.add(currentPath.toString());
			return;
		}

		// define the possible move of left, right, up and down
		int[][] moves = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
		String[] moveNames = new String[]{"l", "r", "u", "d"};
		String separator = " ";
	
		// iterate through all possible moves
		for (int i = 0; i < moves.length; i++) 
		{
			int newRow = currentRow + moves[i][0];
			int newCol = currentCol + moves[i][1];
			
			// check if move is legal
			if (!isLegalMove(maze, visited, newRow, newCol, height, width))
				continue;
				
			// mark position as visited if it's the exit
			if (maze[newRow][newCol] == EXIT)
				visited[newRow][newCol] = EXIT;

			// update maze and visited array
			maze[currentRow][currentCol] = BREADCRUMB;
			visited[currentRow][currentCol] = BREADCRUMB;
			maze[newRow][newCol] = PERSON;
			
			// update current path
			int pathLengthBeforeMove = currentPath.length();
			if (pathLengthBeforeMove != 0) {
				currentPath.append(separator);
			}
			currentPath.append(moveNames[i]);
			findPaths(maze, visited, newRow, newCol, height, width, currentPath, paths);
			currentPath.delete(pathLengthBeforeMove, currentPath.length());
			
			// revert maze back to its previous state
			maze[newRow][newCol] = BREADCRUMB;
			maze[currentRow][currentCol] = PERSON;
		}
	}
	
	// Returns true if moving to row and col is legal (i.e., we have not visited
	// that position before, and it's not a wall).
	private static boolean isLegalMove(char [][] maze, char [][] visited,
	int row, int col, int height, int width)
	{
		// if you hit a wall, a virus or you've stumbled into a spot
		// where you have already been nasty, not a valid path

		// Check if row and col are within the bounds of the maze
		if (row < 0 || row >= height || col < 0 || col >= width)
		return false;

		if (maze[row][col] == WALL || visited[row][col] == BREADCRUMB || maze[row][col] == VIRUS)
			return false;

		return true;
	}

	// This effectively pauses the program for waitTimeInSeconds seconds.
	private static void wait(double waitTimeInSeconds)
	{
		long startTime = System.nanoTime();
		long endTime = startTime + (long)(waitTimeInSeconds * 1e9);

		while (System.nanoTime() < endTime);
	}

	// Prints maze and waits. frameRate is given in frames per second.
	private static void printAndWait(char [][] maze, int height, int width,
	                                 String header, double frameRate)
	{
		if (header != null && !header.equals(""))
			System.out.println(header);

		if (height < 1 || width < 1)
			return;

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				System.out.print(maze[i][j]);
			}

			System.out.println();
		}

		System.out.println();
		wait(1.0 / frameRate);
	}

	// Read maze from file. This function dangerously assumes the input file
	// exists and is well formatted according to the specification above.
	private static char [][] readMaze(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));

		int height = in.nextInt();
		int width = in.nextInt();

		char [][] maze = new char[height][];

		// After reading the integers, there's still a new line character we
		// need to do away with before we can continue.

		in.nextLine();

		for (int i = 0; i < height; i++)
		{
			// Explode out each line from the input file into a char array.
			maze[i] = in.nextLine().toCharArray();
		}

		return maze;
	}
	
	public static double difficultyRating()
	{
		return 3.5;
	}

	// I was close to properly debugging the code but i did not set aside enough time,
	// I will probably have to repeat the course in the summer because of this repeated mistake,
	// i'll get better tho
	public static double hoursSpent()
	{
		return 8.0;
		
	}
}

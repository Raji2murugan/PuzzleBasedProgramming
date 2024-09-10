import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game1 {

	private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	private static final char ADVENTURE = 'A';
	private static final char GOLD = 'G';
	private static final char PATH = '.';

	int x, y;

	public Game1(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

// _________________ INPUT_____________________________
		Scanner sc = new Scanner(System.in);
		System.out.println("Dimensions of the Duregon");
		int row = sc.nextInt();
		int col = sc.nextInt();

		char[][] grid = new char[row][col];

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				grid[i][j] = PATH;
			}
		}

		System.out.println("Position of the Adventure");
		row = sc.nextInt();
		col = sc.nextInt();

		grid[row - 1][col - 1] = ADVENTURE;

		System.out.println("Position of the Gold");
		row = sc.nextInt();
		col = sc.nextInt();

		grid[row - 1][col - 1] = GOLD;

		// _______________ Find the adventure and gold position are inside the grid
		// _________________________

		Game1 adventure = findAdventure(grid);
		Game1 gold = findGold(grid);

		if (adventure != null && gold != null) {
			int minimumPath = findShortestPath(grid, adventure, gold);
			if (minimumPath != -1)
				System.out.println("Minimum Number of Steps: " + minimumPath);
			else
				System.out.println("No path.");
		}
	}

//_________________  Checking Position___________________________-
	public static Game1 findAdventure(char[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == ADVENTURE) {
					return new Game1(i, j);
				}
			}
		}
		return null;
	}

	public static Game1 findGold(char[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == GOLD) {
					return new Game1(i, j);
				}
			}
		}
		return null;
	}

//____________  method_____________________
	public static int findShortestPath(char[][] grid, Game1 adventure, Game1 gold) {
		boolean[][] visited = new boolean[grid.length][grid[0].length];

		Queue<Game1> queue = new LinkedList<>();
		Queue<Integer> distances = new LinkedList<>();

		queue.add(adventure);

		distances.add(0);
		visited[adventure.x][adventure.y] = true;

		while (!queue.isEmpty()) {
			Game1 current = queue.poll();
			int distance = distances.poll();

			if (current.x == gold.x && current.y == gold.y) {
				return distance;
			}

			for (int[] dir : DIRECTIONS) {
				int newX = current.x + dir[0];
				int newY = current.y + dir[1];

				if (isValidMove(grid, newX, newY, visited)) {
					queue.add(new Game1(newX, newY));
					distances.add(distance + 1);
					visited[newX][newY] = true;
				}
			}
		}
		return -1;
	}

	// _________________ Check ValidMove________________________--

	public static boolean isValidMove(char[][] grid, int x, int y, boolean[][] visited) {
		return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && !visited[x][y];
	}

}

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Game3 {

	private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	private static final char ADVENTURE = 'A';
	private static final char GOLD = 'G';
	private static final char MONSTER = 'M';
	private static final char PATH = '.';

	int x, y;

	public Game3(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static void main(String[] args) {
		// __________________ INPUT_____________________________
		Scanner sc = new Scanner(System.in);
		System.out.println("Dimensions of the Dungeon:");
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
		Game3 adventure = new Game3(row - 1, col - 1);

		System.out.println("Position of the Monster");
		row = sc.nextInt();
		col = sc.nextInt();
		grid[row - 1][col - 1] = MONSTER;
		Game3 monster = new Game3(row - 1, col - 1);

		System.out.println("Position of the Gold");
		row = sc.nextInt();
		col = sc.nextInt();
		grid[row - 1][col - 1] = GOLD;
		Game3 gold = new Game3(row - 1, col - 1);

		int minimumPath = findShortestPath(grid, adventure, gold, monster);

		if (minimumPath != -1) {
			System.out.println("Minimum Number of Steps to the Gold: " + minimumPath);
		} else {
			System.out.println("No path to the gold.");
		}

		Random random = new Random();
		boolean caught = false;

		while (!caught) {
			int[] move = DIRECTIONS[random.nextInt(4)];
			int newX = monster.x + move[0];
			int newY = monster.y + move[1];

			if (isValidMonsterMove(grid, newX, newY)) {
				grid[monster.x][monster.y] = PATH;
				monster.x = newX;
				monster.y = newY;
				grid[monster.x][monster.y] = MONSTER;
				if (monster.x == adventure.x && monster.y == adventure.y) {
					caught = true;
				}
			}
		}
	}

	public static int findShortestPath(char[][] grid, Game3 adventure, Game3 gold, Game3 monster) {
		boolean[][] visited = new boolean[grid.length][grid[0].length];
		Game3[][] predecessors = new Game3[grid.length][grid[0].length];

		Queue<Game3> queue = new LinkedList<>();
		Queue<Integer> distances = new LinkedList<>();

		queue.add(adventure);
		distances.add(0);
		visited[adventure.x][adventure.y] = true;

		while (!queue.isEmpty()) {
			Game3 current = queue.poll();
			int distance = distances.poll();

			if (current.x == gold.x && current.y == gold.y) {
				printPath(predecessors, adventure, gold);
				return distance;
			}

			for (int[] dir : DIRECTIONS) {
				int newX = current.x + dir[0];
				int newY = current.y + dir[1];

				if (isValidMove(grid, newX, newY, visited, monster)) {
					queue.add(new Game3(newX, newY));
					distances.add(distance + 1);
					visited[newX][newY] = true;
					predecessors[newX][newY] = current;
				}
			}
		}
		return -1;
	}

	public static void printPath(Game3[][] predecessors, Game3 start, Game3 goal) {
		LinkedList<Game3> path = new LinkedList<>();
		Game3 step = goal;

		while (step != null) {
			path.addFirst(step);
			step = predecessors[step.x][step.y];
		}

		System.out.println("Path taken:");
		for (Game3 p : path) {
			System.out.println("(" + (p.x + 1) + ", " + (p.y + 1) + ")");
		}
	}

	public static boolean isValidMove(char[][] grid, int x, int y, boolean[][] visited, Game3 monster) {
		return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && grid[x][y] != MONSTER && !visited[x][y]
				&& !(x == monster.x && y == monster.y);
	}

	public static boolean isValidMonsterMove(char[][] grid, int x, int y) {
		return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length;
	}
}

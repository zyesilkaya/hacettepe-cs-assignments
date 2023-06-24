import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class IMECEPathFinder{
	  public int[][] grid;
	  public int[][] grayScale;
	  public int height, width;
	  public int maxFlyingHeight;
	  public double fuelCostPerUnit, climbingCostPerUnit;
	  public int min = Integer.MAX_VALUE;
	  public int max = Integer.MIN_VALUE;

	  public IMECEPathFinder(String filename, int rows, int cols, int maxFlyingHeight, double fuelCostPerUnit, double climbingCostPerUnit){

		  grid = new int[rows][cols];
		  this.height = rows;
		  this.width = cols;
		  this.maxFlyingHeight = maxFlyingHeight;
		  this.fuelCostPerUnit = fuelCostPerUnit;
		  this.climbingCostPerUnit = climbingCostPerUnit;



			// TODO: fill the grid variable using data from filename

		  try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			  String line;
			  int row = 0;
			  while ((line = br.readLine()) != null && row < rows) {
				  String[] values = line.trim().split("\\s+");
				  for (int col = 0; col < cols; col++) {
					  grid[row][col] = Integer.parseInt(values[col]);
					  min = Math.min(min,Integer.parseInt(values[col]));
					  max = Math.max(max,Integer.parseInt(values[col]));
				  }
				  row++;
			  }
		  } catch (IOException e) {
			  e.printStackTrace();
		  }

		  try(FileWriter writer = new FileWriter("grayscaleMap.dat")){
			  for (int i = 0; i < grid.length; i++) {
				  for (int j = 0; j < grid[0].length; j++) {
					  int value = 255 * (grid[i][j] - min) / (max - min);
					  writer.write(value + " ");
				  }
				 writer.write("\n");
			  }
		  }catch (IOException e){

		  }

	  }


	  /**
	   * Draws the grid using the given Graphics object.
	   * Colors should be grayscale values 0-255, scaled based on min/max elevation values in the grid
	   */
	  public void drawGrayscaleMap(Graphics g){

		  // TODO: draw the grid, delete the sample drawing with random color values given below
		  for (int i = 0; i < grid.length; i++)
		  {
			  for (int j = 0; j < grid[0].length; j++) {
				  int value = 255 * (grid[i][j] - min) / (max - min);
				  g.setColor(new Color(value, value, value));
				  g.fillRect(j, i, 1, 1);
			  }
		  }
	  }

	/**
	 * Get the most cost-efficient path from the source Point start to the destination Point end
	 * using Dijkstra's algorithm on pixels.
	 * @return the List of Points on the most cost-efficient path from start to end
	 */


	private Point[][] edgeTo;
	private double[][] distTo;
	private PriorityQueue<Point> pq;
	public List<Point> getMostEfficientPath(Point start, Point end) {

		List<Point> path = new ArrayList<>();

		Comparator<Point> compareByCost = new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.cost,o2.cost);
			}
		};

		edgeTo = new Point[height][width];
		distTo = new double[height][width];
		pq = new PriorityQueue<>(compareByCost);

		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				distTo[i][j] = Double.POSITIVE_INFINITY;
			}
		}

		distTo[start.y][start.x] = 0.0;
		pq.add(start);

		while (!pq.isEmpty()) {
			Point v = pq.poll();
			if (v.x == end.x && v.y==end.y)
				break;

			relaxNeighbors(v);
		}

		if (edgeTo[end.y][end.x] == null)
			return path; // No path found

		// Reconstruct the path

		end.cost = getCost(edgeTo[end.y][end.x],end) + distTo[edgeTo[end.y][end.x].y][edgeTo[end.y][end.x].x];

		for (Point p = end; p != null; p = edgeTo[p.y][p.x]) {
			path.add(0, p);
		}

		return path;
	}

	private double getCost(Point start, Point point) {

		double dist = 0;

		if (Math.abs(start.x - point.x) != 0 && Math.abs(start.y - point.y) != 0){
			dist = Math.sqrt(2);
		}

		else if (Math.abs(start.x - point.x) == 0 && Math.abs(start.y - point.y) != 0){
			dist = 1;
		}

		else if (Math.abs(start.x - point.x) != 0 && Math.abs(start.y - point.y) == 0){
			dist = 1;
		}

		int startHeight = grid[start.y][start.x];
		int pointHeight = grid[point.y][point.x];

		double cost = (dist * fuelCostPerUnit) + (climbingCostPerUnit * Math.max(0, pointHeight - startHeight));

		return cost;
	}


	private void relaxNeighbors(Point v) {
		int x = v.x;
		int y = v.y;

		int[][] neighbors = {
				{x-1, y-1}, {x-1, y}, {x-1, y+1},
				{x, y-1},   {x, y+1},
				{x+1, y-1}, {x+1, y}, {x+1, y+1}
		};

		for (int[] neighbor : neighbors) {
			int nx = neighbor[0];
			int ny = neighbor[1];

			if (isValid(nx, ny) && grid[ny][nx] <= maxFlyingHeight){

				Point e = new Point(nx,ny);

				double newDist = getCost(v,e);

				if ( distTo[e.y][e.x] > newDist + distTo[v.y][v.x])  {

					distTo[e.y][e.x] = newDist + distTo[v.y][v.x];
					edgeTo[e.y][e.x] = v;
					e.cost = newDist + distTo[v.y][v.x];
					pq.add(e);

				}
			}
		}
	}

	private boolean isValid(int x, int y) {
		return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
	}



		// TODO: Your code goes here
		// TODO: Implement the Mission 0 algorithm here
		// her nokta 8 yere gidebilir edge classı açmaya vs gerek yok
		//her 8 nokta için gidebileceği yükseklikte mi diye kontrol edip cost hesapla

		/*Comparator<Point> compareByCost = new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {

				int first = grid[start.x][start.y] >= grid[o1.x][o1.y] ? 0 : grid[o1.x][o1.y] - grid[start.x][start.y] ;
				int second = grid[start.x][start.y] >= grid[o2.x][o2.y] ? 0 : grid[o2.x][o2.y] - grid[start.x][start.y] ;

				return Integer.compare(first,second);
			}
		};

		edgeTo = new Point[this.height];
		distTo = new double[this.height];
		pq = new PriorityQueue<Point>(compareByCost);

		for (int v = 0; v < this.height; v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[0] = 0.0;
		pq.add(start);
		while (!pq.isEmpty())
		{
			Point v = pq.poll();
			for (Point e : G.adj(v))
				relax(e);
		}*/


	/*void relax(Point e)
	{
		int v = e.x, w = e.y;
		if (distTo[w] > distTo[v] + grid[v][w])
		{
			distTo[w] = distTo[v] + grid[v][w];
			edgeTo[w] = e;
			if (pq.contains(w)) decreaseKey(w, distTo[w]);
			else pq.add(new Point(v,w));
		}
	}

	void decreaseKey(int v, double dist) {
		// Find the node with vertex v in the priority queue
		for (Point node : pq) {
			if (node.x == v) {
				// Remove the node with old distance value
				pq.remove(node);
				// Update the distance value
				distTo[node.x] = dist;
				// Add the node back to the queue with new distance value
				pq.add(node);
				return;
			}
		}
	}*/

	/**
	 * Calculate the most cost-efficient path from source to destination.
	 * @return the total cost of this most cost-efficient path when traveling from source to destination
	 */
	public double getMostEfficientPathCost(List<Point> path){
		double totalCost = 0.0;

		// TODO: Your code goes here, use the output from the getMostEfficientPath() method

		return path.get(path.size() - 1).cost;
	}


	/**
	 * Draw the most cost-efficient path on top of the grayscale map from source to destination.
	 */
	public void drawMostEfficientPath(Graphics g, List<Point> path){
		// TODO: Your code goes here, use the output from the getMostEfficientPath() method

		// Iterate over the path and draw the path on the grayscale map
		for (int i = 0; i < path.size(); i++) {
			Point currentPoint = path.get(i);
			g.setColor(new Color(0,255,0));
			g.fillRect(currentPoint.x,currentPoint.y,1,1);
		}
	}

	/**
	 * Find an escape path from source towards East such that it has the lowest elevation change.
	 * Choose a forward step out of 3 possible forward locations, using greedy method described in the assignment instructions.
	 * @return the list of Points on the path
	 */
	public List<Point> getLowestElevationEscapePath(Point start){
		List<Point> pathPointsList = new ArrayList<>();

		// TODO: Your code goes here
		// TODO: Implement the Mission 1 greedy approach here

		Point temp = start;
		pathPointsList.add(temp);

		while(isValid(temp.x,temp.y) && temp.x < grid[0].length -1){

			int p = grid[temp.y][temp.x];
			int p1 = grid[temp.y][temp.x + 1];
			int p2 = (temp.y + 1) > grid.length ? Integer.MAX_VALUE : grid[temp.y + 1][temp.x + 1];
			int p3 = (temp.y - 1) < 0? Integer.MAX_VALUE :grid[temp.y - 1][temp.x + 1];

			if(Math.abs(p - p1) <= Math.abs(p - p2) && Math.abs(p - p1) <= Math.abs(p - p3)){
				temp = new Point(temp.x + 1,temp.y);
			}
			else if(Math.abs(p - p3) < Math.abs(p - p1) && Math.abs(p - p3) <= Math.abs(p - p2)){
				temp = new Point(temp.x + 1,temp.y - 1);

			}else{
				temp = new Point(temp.x + 1,temp.y + 1);
			}

			pathPointsList.add(temp);
		}

		return pathPointsList;
	}


	/**
	 * Calculate the escape path from source towards East such that it has the lowest elevation change.
	 * @return the total change in elevation for the entire path
	 */
	public int getLowestElevationEscapePathCost(List<Point> pathPointsList){
		int totalChange = 0;

		// TODO: Your code goes here, use the output from the getLowestElevationEscapePath() method
		for(int i=0;i<pathPointsList.size() - 1;i++){
			int first = grid[pathPointsList.get(i).y][pathPointsList.get(i).x];
			int second = grid[pathPointsList.get(i+1).y][pathPointsList.get(i+1).x];

			totalChange += Math.abs(first - second);
		}
		return totalChange;
	}


	/**
	 * Draw the escape path from source towards East on top of the grayscale map such that it has the lowest elevation change.
	 */
	public void drawLowestElevationEscapePath(Graphics g, List<Point> pathPointsList){
		// TODO: Your code goes here, use the output from the getLowestElevationEscapePath() method
		for (int i = 0; i < pathPointsList.size(); i++) {
			Point currentPoint = pathPointsList.get(i);
			g.setColor(new Color(255, 255,0));
			g.fillRect(currentPoint.x,currentPoint.y,1,1);
		}

	}


}

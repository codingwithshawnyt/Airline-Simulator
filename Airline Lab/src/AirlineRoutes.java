import java.util.Arrays;
import java.io.IOException;

public class AirlineRoutes
{

	public static void main(String args[]) throws IOException{
    	Graph map = new Graph();
    	System.out.println(map);

    	System.out.println("FindRoute Tests:");
    	System.out.println("\tSFO to JFK, 1 hop");
    	String route = map.findRoute(1, "SFO", "JFK");
		System.out.println("\t"+route);

    	System.out.println("\n\tPHX to MIA, 2 hops");
    	route = map.findRoute(2, "PHX", "MIA");
		System.out.println("\t"+route);

    	System.out.println("\n\tSFO to JFK, 4 hops");
    	route = map.findRoute(4, "SFO", "JFK");
		System.out.println("\t"+route);

    	System.out.println("\n\tHNL to BOS, 5 hops");
    	route = map.findRoute(5, "HNL", "BOS");
		System.out.println("\t"+route);


		System.out.println("CheapestRoute Tests:");

		System.out.println("\tCheapest Route From SFO to JFK");
    	route = map.cheapestRoute("SFO", "JFK");
		System.out.println("\t"+route);

		System.out.println("\n\tCheapest Route From PHX to MIA");
    	route = map.cheapestRoute("PHX", "MIA");
		System.out.println("\t"+route);

		System.out.println("\n\tCheapest Route From HNL to BOS");
    	route = map.cheapestRoute("HNL", "BOS");
		System.out.println("\t"+route);

		//Optional shortestPath Tests

			//Make sure you have the shortestPathCheck.class file in your project folder
	/*
	 	shortestPathCheck checker = new shortestPathCheck();
		int[] yourCheapestCostList;
		int[] actualCheapestCostList;
		int successCount = 0;

		for(int v = 0; v <map.airportCode.length; v++)
		{
			System.out.print("\nShortest Path From "+map.airportCode[v]+": ");

			yourCheapestCostList = map.shortestPath(map.airportCode[v]);
			actualCheapestCostList = checker.dijkstra(map.airportCode[v]);
			if(Arrays.equals(yourCheapestCostList, actualCheapestCostList))
			{
				successCount++;
				System.out.println("PASSED");
			}
			else
			{
				System.out.println("FAILED");
				System.out.println("\tYour Smallest Costs: "+Arrays.toString(yourCheapestCostList));
				System.out.println("\tActual Smallest Costs: "+Arrays.toString(actualCheapestCostList));
			}

		}
	*/

	int[][] shortestPaths = {
		{0, 226, 522, 315, 764, 357, 261, 113, 456, 509},
		{226, 0, 296, 89, 538, 131, 259, 113, 230, 283},
		{489, 263, 0, 222, 388, 165, 293, 376, 264, 123},
		{424, 198, 335, 0, 488, 329, 457, 311, 428, 194},
		{712, 486, 351, 451, 0, 388, 516, 599, 487, 352},
		{324, 98, 165, 187, 407, 0, 128, 211, 99, 288},
		{269, 246, 313, 335, 555, 148, 0, 156, 247, 436},
		{113, 113, 409, 202, 651, 244, 148, 0, 343, 396},
		{392, 166, 233, 255, 475, 68, 196, 279, 0, 356},
		{523, 297, 141, 99, 294, 306, 434, 410, 405, 0}
	};

	String[] airportCodes = {"BOS", "CHI", "DFW", "DEN", "HNL", "IAH", "MIA", "JFK", "PHX", "SFO"};

	for (int i = 0; i < airportCodes.length; i++) {
		if (Arrays.equals(map.shortestPath(airportCodes[i]), shortestPaths[i])) {
			System.out.println("Passes");
		} else {
			System.out.println("Failed Test " + i);
		}
	}

    }
}
interface AirlineGraph
{
	static final int SIZE = 10;
	
    static final String airportCode[] = { "BOS", "CHI", "DFW", "DEN", "HNL",
    									  "IAH", "MIA", "JFK", "PHX", "SFO" };
    										
    static final String city[] = {	"Boston, MA", "Chicago, IL", "Dallas-Ft Worth, TX",
    								"Denver, CO", "Honolulu, HI", "Houston, TX",
    								"Miami, FL", "New York, NY", "Phoenix, AZ",
    								"San Francisco, CA" };										
    
    
    abstract String findRoute(int length, String start, String end);
}
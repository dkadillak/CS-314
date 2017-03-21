DTR-18 Sprint2 Release Notes		3/20/2017


Overview
TripCo takes a csv input file, produces an XML itinerary and SVG map. All files have the same base name with different extensions.
Optional command line arguments:
	 
		java TripCo [options] file.csv [map.svg] 
						-i			  background	
						-m				  map		
						-n							  
						-g
						-2
						-3
-i: Display ID's
-m: Display Mileages
-n: Display Names
-g: launch gui
-2: run TwoOpt
-3: run ThreeOpt

//talk about backgroun map and not selection.xml


Purpose
-We have added route optimization with the 2opt 3opt algorithms.
-itinerary and map gui display
-gui option selection
-background map integration



Issue Summary
-Did not include sub selection functionality.
-possibly 3opt or svg display
-for now 3opt will run infinitely with 14ers


Notes
-Haversine great-circle distance formula and code from http://www.movable-type.co.uk/scripts/latlong.html
-TwoOpt based on pseudo code from https://en.wikipedia.org/wiki/2-opt
-

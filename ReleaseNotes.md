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
-SVG generation based on example from: https://xmlgraphics.apache.org/batik/using/swing.html
-XML creation based on example from: http://alvinalexander.com/java/jframe-example
-File line removal from stack overflow
-Dependencies for batik-1.8 and javafx, had trouble getting javafx to work on the lab machines. We added a batik zip file and a javafx jar called jfxrt.jar to our github repo in case you need to configure those to the build path when you test our code.

EclEmma coverage: 67.9%

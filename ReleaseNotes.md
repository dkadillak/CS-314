DTR-18 Sprint4 Release Notes	5/2/2017


Overview
TripCo gets data from the database, then produces a KML itinerary and SVG map. All files have the same base name with different extensions and our team number (18) with the selected options as well.
Optional command line arguments:
	 
		/*command line example:
		 
		java TripCo [options] [map.svg] [selection.xml]
						-i	  background
						-m	  	map
						-g
						-k
						-d
						-2
						-3
		*/
-i: Display ID's
-m: Use miles
-g: launch gui
-d: Display distances (specified by -m or -k)
-k: Use kilometers
-2: run TwoOpt
-3: run ThreeOpt


Purpose
-TripCo produces a KML file instead of an XML file now.
-The GUI now has more features including automatically searching the src directory for XML files and letting you load those into the GUI and the ability to remove individual locations from your selection on the GUI.
-Now you can save a subselection file in the GUI and load it in the same session.
-The user no longer has to provide an XML file in the command line when using the -g option, now you can run the GUI just using -g and a background map SVG.
-2 opt and 3 opt and nearestNeighbor are much faster now.
-Panning and zooming is not supported.
-We did not end up using Grommet, we opted to improve our existing GUI.


Issue Summary
-We could not get coveralls to work with Travis.
-The Travis badge on our README on Github always says "failing" even though our latest builds passed and clicking on the badge takes you to our latest build which is passing.


Notes
-Haversine great-circle distance formula and code from http://www.movable-type.co.uk/scripts/latlong.html
-TwoOpt based on pseudo code from https://en.wikipedia.org/wiki/2-opt
-SVG generation based on example from: https://xmlgraphics.apache.org/batik/using/swing.html
-XML creation based on example from: http://alvinalexander.com/java/jframe-example
-File line removal from stack overflow
-Dependencies for batik-1.8 and javafx, had trouble getting javafx to work on the lab machines.

EclEmma coverage: 58.1%

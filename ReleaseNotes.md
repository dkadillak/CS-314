DTR-18 Sprint3 Release Notes	4/10/2017


Overview
TripCo gets data from the database, then produces a detailed XML itinerary and SVG map. All files have the same base name with different extensions and our team number (18) with the selected options as well.
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
-Now TripCo reads data from the database and not a .csv file.
-The GUI now has more features including being able to save/load a selection file and the ability to search through the database with 6 fields and add locations to the selection xml file.
-Itinerary display and the produced itinerary xml is now more detailed.
-The background map is now of the whole world and not just Colorado and accounts for wrapping.



Issue Summary
-We could not get coveralls to work with Travis.
-The Travis badge on our README on Github always says "failing" even though our latest builds passed and clicking on the badge takes you to our latest build which is passing.


Notes
-Haversine great-circle distance formula and code from http://www.movable-type.co.uk/scripts/latlong.html
-TwoOpt based on pseudo code from https://en.wikipedia.org/wiki/2-opt
-SVG generation based on example from: https://xmlgraphics.apache.org/batik/using/swing.html
-XML creation based on example from: http://alvinalexander.com/java/jframe-example
-File line removal from stack overflow
-Dependencies for batik-1.8 and javafx, had trouble getting javafx to work on the lab machines. We added a batik zip file and a javafx jar called jfxrt.jar to our github repo in case you need to configure those to the build path when you test our code.

EclEmma coverage: 56.4%

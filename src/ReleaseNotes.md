DTR-18 Sprint1 Release Notes		2/20/2017


Overview
TripCo takes a csv input file, produces an XML itinerary and SVG map. All files have the same base name with different extensions.
Optional command line arguments:
-m					display milage of each leg on map
-i					display id of each location on map
-n					display name of each location on map
* n and i options cannot be used together


Purpose
TripCo takes a csv input file that contains a list of locations and their corresponding latitude, longitude, name, and id. It then produces an XML file that contains a trip itinerary, with the same base name as the input file. It also produces an svg map of the trip with the same base name.


Issue Summary
All of the sprint1 user stories have been incorporated
Labels can overlap if locations are too close, making the map unreadable at times.


Notes
Haversine great-circle distance formula and code from http://www.movable-type.co.uk/scripts/latlong.html
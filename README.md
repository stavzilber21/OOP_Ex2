## Object Oriented Programming
### Assignment 2

### Ariel University

*General overview*

In this assignment, we were given several interfaces to implement; Nodes, edges and directed weighted graph interfaces.
In order to implement them and to write the required functions, we had to use different data structures to match to our needs.
In addition, we created various classes in order to achieve the best results and the highest efficiency levels.
After completing this stage, we were instructed to build a GUI class in which we finally presented the graphs and their algorithms.

In order to run our visual GUI interface, one must first download the attached JAR file and run it in their CMD, using the java -jar Ex2.jar G1.json command.
Once run, a window will open and fill the screen. On the left-hand side of the screen, under the ‘File’ menu, choose the ‘Load’ button. This will allow the user to choose a JSON file from their library. Once a file is selected, the graph representing it will form on the screen.
All that remains is for the user to explore the different functions we’ve written, a range of manipulations that can be done to the given graph. The list of functions is held under the ‘Algorithms’ button.

For example, the ‘isConnected’ button will print whether the graph is connected or not. The ‘Shortest Path’ button will return the shortest path existing between two nodes chosen by the user. 

When finished, in the ‘File’ menu again, choose ‘Exit’. This will safely terminate the program.

*Our implementation of the interfaces:*

In order to implement the EdgeData class we used two nodes.
- The DirectedWeightedGraph we created two HashMaps. One for the Nodes and the second for the Edges.
We created an MC variable to count the changes made in the graph. The key in the vertices HashMap holds an index integer, and the key in the Edges HashMap is a point where x symbols the source of the edge and y is its destination.

Using the key we can reach the the wanted edge and we will not get a key with 2 edges. We implemented all the class functions using the two HashMaps.
- Then we implemented the DirectedWeightedGraphAlgorithms class. In this class, we used a graph from the DirectedWeightedGraph class.
 We implemented different functions, each one completing a different action on the graph. 
For example, the isConnected function checks if the graph is strongly connected. 
The shortestPathDist function between two nodes finds the shortest (weight wise) route existing between two given nodes. We also wrote a function that finds the center of the graph, and one that solves the TSP problem.
We then ran tests on all the classes with graphs containing 1000, 10000 and 100000 vertices.
Finally, we created a GUI class in order to draw the graph on the screen. 

The GUI class implements the JFrame class. In addition, we created the drawGrah class in order to implement the JPanel class.

Summary: To run the GUI we will run Ex2 and we will be able to run the task files using also the jar file we created.




__Below is the project’s UML diagram, including interfaces, classes, test classes and functions:__

<img width = "640" alt = "UML" src="https://user-images.githubusercontent.com/93096648/145727197-3d5c655b-dbed-486d-8e39-9f041900a06a.png">

__Below is a table of our functions' runtime performances counted in milliseconds:__

<img width = "400" alt = "UML" src="https://user-images.githubusercontent.com/93096648/145837156-4420e0b1-d618-4964-b1ac-1310fde801eb.png">

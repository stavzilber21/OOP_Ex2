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


__Below is the project’s UML diagram, including interfaces, classes, test classes and functions:__

<img width = "640" alt = "UML" src="https://user-images.githubusercontent.com/93096648/145727197-3d5c655b-dbed-486d-8e39-9f041900a06a.png">

__Below is a table of our functions' runtime performances counted in milliseconds:__

<img width = "400" alt = "UML" src="https://user-images.githubusercontent.com/93096648/145837156-4420e0b1-d618-4964-b1ac-1310fde801eb.png">

As shown above, we ran our functions on different graph sizes.

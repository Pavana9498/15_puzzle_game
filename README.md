The 15-Puzzle is a sliding puzzle that consists of a square (4x4) frame with numbers labeled 1-15 in random order leaving 1 empty slot with no label/number. The goal is to slide the numbers so that they are in order of 1, 2, 3, ..., 15.
Note: 0 here represents the 'empty slot' Example: A 15-Puzzle (4x4 Board)
Start State (Random Board):
Goal State (Always same goal):
The following program solves the the 15-Puzzle Board Game problem using multiple search techniques.
The following searches were used: BFS, IDDFS, A-Star, ID A-Star. Once the program has finished executing, the moves, number of nodes expanded, memory and time complexity is printed for each search for comparison. This project has been implemented in Java.
BFS:
File : FifteenPuzzleBFS.java. This file uses the breadth first search algorithm to find the solution for the given configuration if one exists by using queues.
IDDFS:
File: FifteenPuzzleIDDFS.java. This file uses the Iterative deepening depth first search algorithm to find the solution both iteratively and recursively. A-Star:
File: FifteenPuzzleAStar.java. This file uses the A-star search to find the solution to the given initial board configuration. The heuristics used in this search are Manhattan distance and number of tiles misplaced.
ID A-star:
File: FifteenPuzzleIDAStar.java. This file uses the iterative deepening A- star search to find the solution. And the heuristics in this search are Manhattan distance and number of misplaced tiles.

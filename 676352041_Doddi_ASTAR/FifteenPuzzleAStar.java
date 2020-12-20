import java.util.*;
import java.io.*;


class PuzzleNode {
 int[][] config = new int[4][4];
 int exploredLevel = 0;
 int empty_row = -1;
 int empty_column = -1;
 PuzzleNode parent, up, down, left, right;
 char currentMove = '\0';
 long timeTaken = 0L;
 long memory = 0L;
 long noOfNodes = 0L;
}



public class FifteenPuzzleAStar {



 public static void main(String[] args) throws Exception {
  PuzzleNode node = new PuzzleNode();
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  System.out.println("Enter the initial configuration");
  String input = br.readLine();
  String inputArray[] = new String[15];
  int arrayPointer = 0;
  if (input != null) {
   inputArray = input.split(" ");
  }

  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 4; j++) {
    int k = Integer.parseInt(inputArray[arrayPointer++]);
    node.config[i][j] = k;
    if (node.config[i][j] == 0) {
     node.empty_row = i;
     node.empty_column = j;
    }


   }
  }
  boolean isSolution = misplacedTiles(node);
  if (isSolution)
   System.out.println("Solution exists");
  else {
   System.out.println("Can't find a solution");
  }

  System.out.println();
  System.out.println();

 boolean isnewSolution = manHattanDistance(node);
  if (isnewSolution)
   System.out.println("Solution exists");
  else {
   System.out.println("Can't find a solution");
  }
 }



 public static boolean misplacedTiles(PuzzleNode node) {
  System.out.println("In Misplaced Tiles ");
  PuzzleNode solution = new PuzzleNode();
  long startTime = System.currentTimeMillis();
  long timeTaken = 0L;
  int depth = 0;
  int maxDepth = 1000; 
  LinkedList < PuzzleNode > stack = new LinkedList < PuzzleNode > (); 
  stack.add(node);
  PuzzleNode currentNode = new PuzzleNode();
  while (timeTaken < 180000) {
    int maxtiles = 10000;
   timeTaken = System.currentTimeMillis() - startTime;
    for (int i = 0; i < stack.size() ; i++) {     
                PuzzleNode updateNode = stack.get(i);
                if(noOfTilesMisplaced(updateNode) + updateNode.exploredLevel < maxtiles){
                    maxtiles =  updateNode.exploredLevel + noOfTilesMisplaced(updateNode);
                    currentNode = updateNode;
                }
              }
              stack.removeFirstOccurrence(currentNode);

   if (noOfTilesMisplaced(currentNode) == 0) {

    updateMoves(currentNode);
    timeTaken = System.currentTimeMillis() - startTime; 
    long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    solution.memory = (afterUsedMem) / 1024;
    display(solution, timeTaken);

    return true;
   } 

   else 
   {
    TRAVERSECHILD(currentNode);
    solution.noOfNodes++;

    if (currentNode.left != null)
     stack.add(currentNode.left);
    if (currentNode.right != null)
     stack.add(currentNode.right);
    if (currentNode.up != null)
     stack.add(currentNode.up);
    if (currentNode.down != null)
     stack.add(currentNode.down);

   }
   
}
return false;
}


public static boolean manHattanDistance(PuzzleNode node) {
   System.out.println("Manhattan Distance Heuristic");
  PuzzleNode solution = new PuzzleNode();
  long startTime = System.currentTimeMillis();
  long timeTaken = 0L;
  int depth = 0;
  int maxDepth = 1000; 
  LinkedList < PuzzleNode > stack = new LinkedList < PuzzleNode > (); 
  stack.add(node);
  PuzzleNode currentNode = new PuzzleNode();


  while (timeTaken < 180000) {
    int maxtiles = 10000;
   timeTaken = System.currentTimeMillis() - startTime;
    for (int i = 0; i < stack.size() ; i++) { 
                PuzzleNode updateNode = stack.get(i);
                if(retrieveManhattanDistance(updateNode) + updateNode.exploredLevel < maxtiles){
                    maxtiles =  updateNode.exploredLevel + retrieveManhattanDistance(updateNode);
                    currentNode = updateNode;
                }
              }
              stack.removeFirstOccurrence(currentNode);

   if (retrieveManhattanDistance(currentNode) == 0) {

    updateMoves(currentNode);
    timeTaken = System.currentTimeMillis() - startTime; 
    long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    solution.memory = (afterUsedMem) / 1024;
    display(solution, timeTaken);

    return true;
   } 

   else 
   {
    TRAVERSECHILD(currentNode); 
    solution.noOfNodes++;

    if (currentNode.left != null)
     stack.add(currentNode.left);
    if (currentNode.right != null)
     stack.add(currentNode.right);
    if (currentNode.up != null)
     stack.add(currentNode.up);
    if (currentNode.down != null)
     stack.add(currentNode.down);

   }
   
}
return false;
}
  
   public static int retrieveManhattanDistance(PuzzleNode currentNode){
        int manhattanDistance = 0;
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4 ; j++ ) {
                int row_val;
                int column_val;
                int state_value = currentNode.config[i][j];
                
                if(state_value > 0 && state_value < 5){ 
                    row_val = 0;
                    column_val = state_value - 1;
                }
                else if(state_value > 4 && state_value < 9){ 
                    row_val = 1;
                    column_val = state_value - 5;
                }
                else if(state_value > 8 && state_value < 13){ 
                    row_val = 2;
                    column_val = state_value - 9;
                }
                else if(state_value > 12 && state_value < 16){ 
                    row_val = 3;
                    column_val = state_value - 13;
                }
                else if(state_value == 0){ 
                    row_val = 3;
                    column_val = 3;
                }
                else{
                  return 0;
                }

                manhattanDistance = manhattanDistance + Math.abs(i - row_val) + Math.abs(j - column_val);
            }
        }
        return manhattanDistance;
}

  public static int noOfTilesMisplaced(PuzzleNode currentNode){
     int[][] goal = new int[4][4];
  int count = 1;
  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 4; j++) {
    goal[i][j] = count++;
   }
  }
  goal[3][3] = 0;
    int noOfTiles = 0;
    for (int i = 0; i<4; i++){
      for (int j = 0; j < 4; j++){
          if(goal[i][j] != currentNode.config[i][j])
            noOfTiles++;                                       
          }
        }
        return noOfTiles;
  }


 public static void updateMoves(PuzzleNode currentNode) {
  LinkedList < PuzzleNode > movesMade = new LinkedList < PuzzleNode > ();
  String movesmade = "";
  while (currentNode != null) {
   movesMade.addFirst(currentNode);
   currentNode = currentNode.parent; 
  }
  while (!movesMade.isEmpty()) {
   PuzzleNode temp = movesMade.removeFirst();
   movesmade = movesmade + temp.currentMove;
  }
  System.out.println("Moves: " + movesmade);
 }

 public static boolean GOALTEST(int[][] config) {
  int[][] goal = new int[4][4];
  int count = 1;
  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 4; j++) {
    goal[i][j] = count++;
   }
  }
  goal[3][3] = 0;
  return Arrays.deepEquals(config, goal);
 }

 public static void TRAVERSECHILD(PuzzleNode currentNode) {
  int row = currentNode.empty_row;
  int column = currentNode.empty_column;

  if (currentNode.empty_column != 3) {
   currentNode.right = new PuzzleNode();
   PuzzleNode alteredPuzzle = new PuzzleNode();
   currentNode.right.currentMove = 'R';
   currentNode.right.exploredLevel = currentNode.exploredLevel + 1;
   currentNode.right.empty_row = currentNode.empty_row;
   currentNode.right.empty_column = currentNode.empty_column + 1;
   currentNode.right.parent = currentNode;

   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.right.currentMove);
   currentNode.right.config = alteredPuzzle.config;
  }

  if (currentNode.empty_column != 0) {
   currentNode.left = new PuzzleNode();
   PuzzleNode alteredPuzzle = new PuzzleNode();
   currentNode.left.currentMove = 'L';
   currentNode.left.exploredLevel = currentNode.exploredLevel + 1;
   currentNode.left.empty_row = currentNode.empty_row;
   currentNode.left.empty_column = currentNode.empty_column - 1;
   currentNode.left.parent = currentNode;
   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.left.currentMove);
   currentNode.left.config = alteredPuzzle.config;
  }
  if (currentNode.empty_row != 3) {
   currentNode.down = new PuzzleNode();
   PuzzleNode alteredPuzzle = new PuzzleNode();
   currentNode.down.currentMove = 'D';
   currentNode.down.exploredLevel = currentNode.exploredLevel + 1;
   currentNode.down.empty_row = currentNode.empty_row + 1;
   currentNode.down.empty_column = currentNode.empty_column;
   currentNode.down.parent = currentNode;
   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.down.currentMove);
   currentNode.down.config = alteredPuzzle.config;
  }

  if (currentNode.empty_row != 0) {
   currentNode.up = new PuzzleNode();
   PuzzleNode alteredPuzzle = new PuzzleNode();
   currentNode.up.currentMove = 'U';
   currentNode.up.exploredLevel = currentNode.exploredLevel + 1;
   currentNode.up.empty_row = currentNode.empty_row - 1;
   currentNode.up.empty_column = currentNode.empty_column;
   currentNode.up.parent = currentNode;
   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.up.currentMove);
   currentNode.up.config = alteredPuzzle.config;

  }
  
 }


 public static PuzzleNode generateNewState(int[][] currentState, int row, int column, char move) {
  PuzzleNode alteredPuzle = new PuzzleNode();
  for (int i = 0; i < 4; i++)
   alteredPuzle.config[i] = currentState[i].clone();

  if (move == 'L') {
   alteredPuzle.config[row][column] = alteredPuzle.config[row][column - 1];
   alteredPuzle.config[row][column - 1] = 0;
  } else if (move == 'R') {
   alteredPuzle.config[row][column] = alteredPuzle.config[row][column + 1];
   alteredPuzle.config[row][column + 1] = 0;
  } else if (move == 'U') {
   alteredPuzle.config[row][column] = alteredPuzle.config[row - 1][column];
   alteredPuzle.config[row - 1][column] = 0;
  } else if (move == 'D') {
   alteredPuzle.config[row][column] = alteredPuzle.config[row + 1][column];
   alteredPuzle.config[row + 1][column] = 0;
  }
  return alteredPuzle;
 }


 public static void display(PuzzleNode solution, long timeTaken) {
  System.out.println("Time taken " + timeTaken + " mS");
  System.out.println("Expanded nodes " + solution.noOfNodes);
  System.out.println("Memory Used " + solution.memory + " kB");

 }

}
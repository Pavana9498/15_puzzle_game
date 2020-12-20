import java.io.*;
import java.util.*;


class Node {
 int[][] config = new int[4][4];
 int exploredLevel = 0;
 int empty_row = -1;
 int empty_column = -1;
 Node parent, up, down, left, right;
 char currentMove = '\0';
 long timeTaken = 0L;
 long memory = 0L;
 long noOfNodes = 0L;
}

public class IDFSA {
  public static int numberOfNodes = 0;

 public static void main(String[] args) throws Exception {
  Node node = new Node();
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


 boolean solutionExists = idaAstar(node);
  if (!solutionExists)
   System.out.println("Solution does not exist");

  boolean isSolutionExists = idaAstarMisPlaced(node);
  if (!isSolutionExists)
   System.out.println("Solution does not exist");
 }

 public static int noOfTilesMisplaced(Node currentNode){
  System.out.println("");
  System.out.println("");
  System.out.println("Misplaced Tiles Solution");
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



public static int manHattanDistance(Node currentNode){
        int manhattanDistance = 0;
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4 ; j++ ) {
                int row_val;
                int column_val;
                int state_value = currentNode.config[i][j];

                if(state_value >= 1 && state_value <= 4){ 
                    row_val = 0;
                    column_val = state_value - 1;
                }
                else if(state_value >= 5 && state_value <= 8){ 
                    row_val = 1;
                    column_val = state_value - 5;
                }
                else if(state_value >= 9 && state_value <= 12){ 
                    row_val = 2;
                    column_val = state_value - 9;
                }
                else if(state_value >= 13 && state_value <= 15){ 
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

private static boolean idaAstar(Node root) {
Node solution = new Node();
long startTime = System.currentTimeMillis();
long timeTaken = 0L;

List<Node> successPath = new ArrayList<>();

int limit=manHattanDistance(root);
successPath.add(root);

while(true) {

    int t = searchSuccessPath(root,0,limit);
    if(t==0){
     timeTaken = System.currentTimeMillis() - startTime; 
    long afterUsedMem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/2;
    solution.memory = afterUsedMem / 1024;
    display(solution, timeTaken);
    return true;
    }
    else if(t == Integer.MAX_VALUE){
        return false;
    }
    
    limit = t;

}

}

private static boolean idaAstarMisPlaced(Node root) {
Node solution = new Node();
long startTime = System.currentTimeMillis();
long timeTaken = 0L;

List<Node> successPath = new ArrayList<>();

int limit=noOfTilesMisplaced(root);
successPath.add(root);

while(true) {

    int t = searchSuccessPath(root,0,limit);
    if(t==0){
     timeTaken = System.currentTimeMillis() - startTime; 
    long afterUsedMem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/2;
    solution.memory = afterUsedMem / 1024;
    display(solution, timeTaken);
    return true;
    }
    else if(t == Integer.MAX_VALUE){
        return false;
    }
    
    limit = t;

}

}

private static int searchSuccessPath(Node rootNode, int cost, int limit) {
       int totalCost = cost+manHattanDistance(rootNode);
    if(totalCost > limit) { 
        return totalCost;
    }
    if(TEST_GOAL(rootNode.config)) { 
         updateMoves(rootNode);
        return 0;
    }  
    int min = Integer.MAX_VALUE;
    for(Node x: generatePath(rootNode)) { 
        int t= searchSuccessPath(x,cost+x.exploredLevel,limit);      
      if(t==0){    
        return 0;
      }
      if(t<min){
        numberOfNodes++;
        min=t;
      }  
}
return min;



}

private static List<Node> generatePath(Node expandedNode) {
   CHILDNODE(expandedNode);
   List<Node>  minimumThresHoldNode = new ArrayList<>();
   int minimumThresHoldValue =Integer.MAX_VALUE;
   if(expandedNode.left!=null) {
    int leftNodeThreshold = expandedNode.left.exploredLevel + manHattanDistance(expandedNode.left);
        minimumThresHoldNode.add(expandedNode.left);     
   }

    if(expandedNode.right!=null) {
    int rightNodeThreshold = expandedNode.right.exploredLevel + manHattanDistance(expandedNode.right); 
        minimumThresHoldNode.add(expandedNode.right);
   }

   if(expandedNode.up!=null) {
    int upNodeThreshold = expandedNode.up.exploredLevel + manHattanDistance(expandedNode.up);
        minimumThresHoldNode.add(expandedNode.up);

     }
   if(expandedNode.down!=null) {
    int downNodeThreshold = expandedNode.down.exploredLevel + manHattanDistance(expandedNode.down);
        minimumThresHoldNode.add(expandedNode.down);    
   }
   return minimumThresHoldNode;
}

private static boolean TEST_GOAL(int[][] config) {
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

  public static void CHILDNODE(Node currentNode) {
  int row = currentNode.empty_row;
  int column = currentNode.empty_column;

if (currentNode.empty_column != 3) {
   currentNode.right = new Node();
   Node alteredPuzzle = new Node();
   currentNode.right.currentMove = 'R';
   currentNode.right.exploredLevel = currentNode.exploredLevel + 1;
   currentNode.right.empty_row = currentNode.empty_row;
   currentNode.right.empty_column = currentNode.empty_column + 1;
   currentNode.right.parent = currentNode;

   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.right.currentMove);
   currentNode.right.config = alteredPuzzle.config;
  }

  if (currentNode.empty_column != 0) {
   currentNode.left = new Node();
   Node alteredPuzzle = new Node();
   currentNode.left.currentMove = 'L';
   currentNode.left.exploredLevel = currentNode.exploredLevel + 1;
   currentNode.left.empty_row = currentNode.empty_row;
   currentNode.left.empty_column = currentNode.empty_column - 1;
   currentNode.left.parent = currentNode;
   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.left.currentMove);
   currentNode.left.config = alteredPuzzle.config;
  }
    if (currentNode.empty_row != 3) {
   currentNode.down = new Node();
   Node alteredPuzzle = new Node();
   currentNode.down.currentMove = 'D';
   currentNode.down.exploredLevel = currentNode.exploredLevel + 1;
   currentNode.down.empty_row = currentNode.empty_row + 1;
   currentNode.down.empty_column = currentNode.empty_column;
   currentNode.down.parent = currentNode;
   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.down.currentMove);
   currentNode.down.config = alteredPuzzle.config;
  }
  
  if (currentNode.empty_row != 0) {
   currentNode.up = new Node();
   Node alteredPuzzle = new Node();
   currentNode.up.currentMove = 'U';
   currentNode.up.exploredLevel = currentNode.exploredLevel + 1;
   currentNode.up.empty_row = currentNode.empty_row - 1;
   currentNode.up.empty_column = currentNode.empty_column;
   currentNode.up.parent = currentNode;
   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.up.currentMove);
   currentNode.up.config = alteredPuzzle.config;

  }

 }


  public static Node generateNewState(int[][] currentState, int row, int column, char move) {
  Node alteredPuzle = new Node();
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

 public static void display(Node solution, long timeTaken) {
  numberOfNodes = numberOfNodes/4;
  System.out.println("Time taken " + timeTaken + " mS");
  System.out.println("Expanded nodes " + numberOfNodes);
  System.out.println("Memory Used " + solution.memory + " kB");

 }

 public static void updateMoves(Node currentNode) {
  LinkedList <Node> movesMade = new LinkedList <Node> ();
  String movesmade = "";
  while (currentNode != null) {
   movesMade.addFirst(currentNode);
   currentNode = currentNode.parent;
  }
  while (!movesMade.isEmpty()) {
   Node temp = movesMade.removeFirst();
   movesmade = movesmade + temp.currentMove;
  }
  System.out.println("Moves: " + movesmade);
 }


}

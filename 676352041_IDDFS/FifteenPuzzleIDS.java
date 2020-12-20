import java.util.*;
import java.io.*;

class FifteenPuzzle {
 int[][] config = new int[4][4];
 int level = 0;
 int empty_row = -1;
 int empty_column = -1;
 FifteenPuzzle parent, up, down, left, right;
 char currentMove = '\0';
 long timeTaken = 0L;
 long memory = 0L;
 long noOfNodes = 0L;
}



public class FifteenPuzzleIDS {



 public static void main(String[] args) throws Exception {
  FifteenPuzzle node = new FifteenPuzzle();
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  System.out.println("Enter the initial configuration (4*4)");
  String input = br.readLine();
  String inputArray[] = new String[15];
  int arrayPointer = 0;
  if (input != null) {
   inputArray = input.split(" ");
  }

  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 4; j++) {
    int k = Integer.parseInt(inputArray[arrayPointer++]);
    if (k > 15 || k < 0) {
     throw new Exception("Inputs can't be greater than 15 or less than zero");
    }
    node.config[i][j] = k;
    if (node.config[i][j] == 0) {
     node.empty_row = i;
     node.empty_column = j;
    }


   }
  }
  boolean isSolution = ids(node);
  if (isSolution)
   System.out.println("Solution exists");
  else {
   System.out.println("Solution doesn't exist");
  }
 }


/*IDS*/
 public static boolean ids(FifteenPuzzle node) {
  LinkedList < FifteenPuzzle > linkedList = new LinkedList < FifteenPuzzle > ();
  FifteenPuzzle solution = new FifteenPuzzle();
  long startTime = System.currentTimeMillis();
  long timeTaken = 0L;
  int depth = 0;
  int maxDepth = 1000; 
  while (depth < maxDepth){
    LinkedList < FifteenPuzzle > stack = new LinkedList < FifteenPuzzle > (); 
    stack.add(node);

  while (!stack.isEmpty() && timeTaken < 180000) {
   timeTaken = System.currentTimeMillis() - startTime;
   FifteenPuzzle currentNode = stack.removeLast(); 

   if (TESTGOAL(currentNode.config)) {

    updateMoves(currentNode);
    timeTaken = System.currentTimeMillis() - startTime; 
    long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    solution.memory = (afterUsedMem) / 1024;
    display(solution, timeTaken);

    return true;
   } 

   else if(currentNode.level < depth) 
   {
    if (!linkedList.contains(currentNode)) 
    linkedList.add(currentNode);
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

  depth++;

  }

  return false;
 }

 public static void TRAVERSECHILD(FifteenPuzzle currentNode) {
  int row = currentNode.empty_row;
  int column = currentNode.empty_column;


  
  if (currentNode.empty_column != 3) {
   currentNode.right = new FifteenPuzzle();
   FifteenPuzzle alteredPuzzle = new FifteenPuzzle();
   currentNode.right.level = currentNode.level + 1;
   currentNode.right.currentMove = 'R';
   currentNode.right.empty_row = currentNode.empty_row;
   currentNode.right.empty_column = currentNode.empty_column + 1;
   currentNode.right.parent = currentNode;

   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.right.currentMove);
   currentNode.right.config = alteredPuzzle.config;
  }
  if (currentNode.empty_column != 0) {
   currentNode.left = new FifteenPuzzle();
   FifteenPuzzle alteredPuzzle = new FifteenPuzzle();
   currentNode.left.level = currentNode.level + 1;
      currentNode.left.currentMove = 'L';
   currentNode.left.empty_row = currentNode.empty_row;
   currentNode.left.empty_column = currentNode.empty_column - 1;
   currentNode.left.parent = currentNode;
   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.left.currentMove);
   currentNode.left.config = alteredPuzzle.config;
  }
  if (currentNode.empty_row != 3) {
   currentNode.down = new FifteenPuzzle();
   FifteenPuzzle alteredPuzzle = new FifteenPuzzle();
   currentNode.down.level = currentNode.level + 1;
    currentNode.down.currentMove = 'D';
   currentNode.down.empty_row = currentNode.empty_row + 1;
   currentNode.down.empty_column = currentNode.empty_column;
   currentNode.down.parent = currentNode;
   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.down.currentMove);
   currentNode.down.config = alteredPuzzle.config;
  }
  if (currentNode.empty_row != 0) {
   currentNode.up = new FifteenPuzzle();
   FifteenPuzzle alteredPuzzle = new FifteenPuzzle();
   currentNode.up.level = currentNode.level + 1;
      currentNode.up.currentMove = 'U';
   currentNode.up.empty_row = currentNode.empty_row - 1;
   currentNode.up.empty_column = currentNode.empty_column;
   currentNode.up.parent = currentNode;
   alteredPuzzle = generateNewState(currentNode.config, currentNode.empty_row, currentNode.empty_column, currentNode.up.currentMove);
   currentNode.up.config = alteredPuzzle.config;

  }
  
 }

 public static void updateMoves(FifteenPuzzle currentNode) {
  LinkedList < FifteenPuzzle > movesMade = new LinkedList < FifteenPuzzle > ();
  String movesmade = "";
  while (currentNode != null) {
   movesMade.addFirst(currentNode);
   currentNode = currentNode.parent; // traversing from node through the parent of each node 
  }
  while (!movesMade.isEmpty()) {
   FifteenPuzzle temp = movesMade.removeFirst();
   movesmade = movesmade + temp.currentMove;
  }
  System.out.println("Moves: " + movesmade);
 }

 public static FifteenPuzzle generateNewState(int[][] currentState, int row, int column, char move) {
  FifteenPuzzle alteredPuzle = new FifteenPuzzle();
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




 public static boolean TESTGOAL(int[][] config) {
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



 public static void display(FifteenPuzzle solution, long timeTaken) {
  System.out.println("Time taken " + timeTaken + " mS");
  System.out.println("Expanded nodes " + solution.noOfNodes);
  System.out.println("Memory Used " + solution.memory + " kB");

 }

}
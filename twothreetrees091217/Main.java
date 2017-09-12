package twothreetrees091217;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args){
		
		TwoThreeTree tree = new TwoThreeTree();
		
		Scanner in = new Scanner(System.in);
		
		//Part 1: Insert				
		int numInputs = in.nextInt();
		
		for(int i = 0; i < numInputs; i++){		
			twothree.insert(in.next(), in.nextInt(), tree);
		}
		
		//Instruction 2: Retrieve In Range
		int numGets = in.nextInt();
		
		for(int i = 0; i < numGets; i++){
			twothree.printValuesInRange(tree, in.next(), in.next());
		}
	}
}

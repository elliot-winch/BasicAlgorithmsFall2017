package twothreetrees091217;

import java.io.*;

public class Main {
	
	public static void main(String[] args){
		
		TwoThreeTree tree = new TwoThreeTree();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int n = 0;
		
		try{
			n = Integer.parseInt(in.readLine());
		} catch (Exception e){
			System.out.println("Bad input");
			System.exit(1);
		}
		
		for(int i = 0; i < n; i++){
			String[] tmp = new String[0];
			
			try{
				tmp = in.readLine().split(" ");
			} catch (Exception e){
				System.out.println("Bad input");
				System.exit(1);
			}
						
			twothree.insert(tmp[0], Integer.parseInt(tmp[1]), tree);
		}
		
		int m = 0;
		
		try{
			m = Integer.parseInt(in.readLine());
		} catch (Exception e){
			System.out.println("Bad input");
			System.exit(1);
		}
		
		for(int i = 0; i < m; i++){
			String[] tmp = new String[0];
			
			try{
				tmp = in.readLine().split(" ");
			} catch (Exception e){
				System.out.println("Bad input");
				System.exit(1);
			}
						
			int query = Integer.parseInt(tmp[0]);
			
			if(query == 1){
				twothree.incValue(tree, tmp[1], Integer.parseInt(tmp[2]));
			} else if (query == 2){
				System.out.println(twothree.eval(tree, Integer.parseInt(tmp[1])));
			} else {
				System.out.println("Bad input");
				System.exit(1);
			}
		}
		
	}
	
}

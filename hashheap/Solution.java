package hashheap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class Solution {

	public static void main(String[] args){
			
		Hashtable<String, Node> hashtable = new Hashtable<String, Node>();
		PriorityQueue<Node> heap = new PriorityQueue<Node>(new NodeComparator());
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		long n = 0;
		
		try{
			n = Long.parseLong(in.readLine());
		} catch (Exception e){
			System.out.println("Bad input");
			System.exit(1);
		}
		
		for(long i = 0; i < n; i++){
			String[] tmp = new String[0];
			
			try{
				tmp = in.readLine().split(" ");
			} catch (Exception e){
				System.out.println("Bad input");
				System.exit(1);
			}
				
			insert(hashtable, heap, tmp[0], Long.parseLong(tmp[1]));
		}
	
		long m = 0;
		
		try{
			m = Long.parseLong(in.readLine());
		} catch (Exception e){
			System.out.println("Bad input");
			System.exit(1);
		}
		
		for(long i = 0; i < m; i++){
			String[] tmp = new String[0];
			
			try{
				tmp = in.readLine().split(" ");
			} catch (Exception e){
				System.out.println("Bad input");
				System.exit(1);
			}
						
			long query = Long.parseLong(tmp[0]);
			
			if(query == 1){
				update(hashtable, heap, tmp[1], Long.parseLong(tmp[2]));
			} else if (query == 2){
				System.out.println(evaluate(hashtable, heap, Long.parseLong(tmp[1])));
			} else {
				System.out.println("Bad input");
				System.exit(1);
			}
		}
	}
	
	static void insert(Hashtable<String, Node> hashtable, PriorityQueue<Node> heap, String key, long value){
		if(key != null){
			Node n = new Node(key, value);
			
			hashtable.put(key, n);
			heap.add(n);
		}
	}
	
	static void update(Hashtable<String, Node> hashtable, PriorityQueue<Node> heap, String key, long val){
		
		Node n = hashtable.get(key);
		n.setValue(n.getValue() + val);
		
		//Effecitvely updating value in heap
		heap.remove(n);
		heap.add(n);
	}
	
	static long evaluate(Hashtable<String, Node> hashtable, PriorityQueue<Node> heap, long evalNum){
		System.out.println("Eval: " + evalNum);
		while(heap.peek() != null && heap.peek().getValue() < evalNum){
			Node n = heap.remove();
			
			hashtable.remove(n);
			System.out.println("Num removed: " + n.getName() + " " + n.getValue());
		}
		return heap.size();
	}
}


class Node{
	
	private String name;
	private long value;

	public String getName() {
		return name;
	}

	public long getValue() {
		return value;
	}
	
	public void setValue(long val) {
		this.value = val;
	}
	
	public Node(String name, long value){
		this.name = name;
		this.value = value;
	}
}

class NodeComparator implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		return Long.compare(o1.getValue(), o2.getValue());
	}	
}

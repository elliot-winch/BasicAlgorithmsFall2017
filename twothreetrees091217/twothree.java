package twothreetrees091217;


/*
   static void setRange(TwoThreeTree tree, String startKey, String endKey, int increaseAmount){
	   
	   if(startKey.compareTo(endKey) <= 0){

		   setRange("", tree.root, startKey, endKey, tree.height, increaseAmount);
	   } 
	   else {
		   setRange("", tree.root, endKey, startKey, tree.height, increaseAmount);
	   }
   }
   
  private static void setRange(String low, Node node, String startKey, String endKey, int height, int amount){
	   if(height == 0){
		   if(node.guide.compareTo(startKey) >= 0 && node.guide.compareTo(endKey) <= 0){
			   node.value += amount;
		   }
		   return;
	   }
	   
	   InternalNode iN = (InternalNode) node; //downcast
	   
	   if(low.compareTo(startKey) >= 0 && node.guide.compareTo(endKey) <= 0){
		   node.value += amount;
	   } else {
		   setRange(low, iN.child0, startKey, endKey, height - 1, amount);
		   setRange(iN.child0.guide, iN.child1, startKey, endKey, height - 1, amount);
		   if(iN.child2 != null){
			   setRange(iN.child1.guide, iN.child2, startKey, endKey, height - 1, amount);
		   }
	   }
   }
*/

public class twothree {
	
   static int eval(TwoThreeTree tree, int evalNum){
	   return eval(tree.root, evalNum, tree.height);
   }
   
   private static int eval(Node node, int evalNum, int height){
	   
	   if(height == 0){
		   if(total < evalNum){
			   return 0;
		   } else {
			   return 1;
		   }
	   } 
	   
	   InternalNode iN = (InternalNode) node; //downcast
	   
	   if(iN.child2!= null){
		   return eval(iN.child0, evalNum, height - 1, total)
		   + eval(iN.child1, evalNum, height - 1, total)
		   
		   + eval(iN.child2, evalNum, height - 1, total);
	   } else {
		   return eval(iN.child0, evalNum, height - 1, total)
				   + eval(iN.child1, evalNum, height - 1, total);
	   }

   }
	
   static void incValue(TwoThreeTree tree, String key, int newValue){
	   try{
		   twothree.getNode(tree, key).value += newValue;
	   }catch(NullPointerException e){
		   System.err.println(e.getMessage());
	   }
   }
	
   static int getValue(TwoThreeTree tree, String key){
	   return getValue(tree.root, key, tree.height, 0);
   }
	
   private static int getValue(Node node, String key, int height, int total){
	   total += node.value;
	   
	   if(height == 0){
		   if(node.guide.equals(key)){
			   return total;
		   } else {
			   return -1;
		   }
	   }
	   
	   InternalNode iN = (InternalNode) node; //downcast
	   
	   if(iN.child0.guide.compareTo(key) >= 0){
		   return getValue(iN.child0, key, height - 1, total);
	   }else if(iN.child1.guide.compareTo(key) >= 0){
		   return getValue(iN.child1, key, height - 1, total);
	   }else if(iN.child2 != null){
		   return getValue(iN.child2, key, height - 1, total);
	   } else {
		   return -1;
	   }
   }
   
   static Node getNode(TwoThreeTree tree, String key) throws NullPointerException {
	   Node n = getNode(tree.root, key, tree.height);
	   
	   if(n == null){
		   throw new NullPointerException(String.format("Node with key %s does not appear in tree.", key));
	   }
		   
	   return n;
   }
	
   private static Node getNode(Node node, String key, int height){
	   
	   while(height > 0){
		   InternalNode iN = (InternalNode) node; //downcast

		   if(iN.child0.guide.compareTo(key) >= 0){
			   node = iN.child0;
		   }else if(iN.child1.guide.compareTo(key) >= 0){
			   node = iN.child1;
		   }else if(iN.child2 != null){
			   node = iN.child2;
		   } else {
			   return null;
		   }
		   
		   height--;
	   }
	   
	   if(node.guide.equals(key)){
		   return node;
	   } else {
		   return null;
	   }      
   }

   static void insert(String key, int value, TwoThreeTree tree) {
	   // insert a key value pair into tree (overwrite existsing value
	   // if key is already present)

	      int h = tree.height;

	      if (h == -1) {
	          LeafNode newLeaf = new LeafNode();
	          newLeaf.guide = key;
	          newLeaf.value = value;
	          tree.root = newLeaf; 
	          tree.height = 0;
	      }
	      else {
	         WorkSpace ws = doInsert(key, value, tree.root, h);

	         if (ws != null && ws.newNode != null) {
	         // create a new root

	            InternalNode newRoot = new InternalNode();
	            if (ws.offset == 0) {
	               newRoot.child0 = ws.newNode; 
	               newRoot.child1 = tree.root;
	            }
	            else {
	               newRoot.child0 = tree.root; 
	               newRoot.child1 = ws.newNode;
	            }
	            resetGuide(newRoot);
	            tree.root = newRoot;
	            tree.height = h+1;
	         }
	      }
	   }

	   static WorkSpace doInsert(String key, int value, Node p, int h) {
	   // auxiliary recursive routine for insert

	      if (h == 0) {
	         // we're at the leaf level, so compare and 
	         // either update value or insert new leaf

	         LeafNode leaf = (LeafNode) p; //downcast
	         int cmp = key.compareTo(leaf.guide);

	         if (cmp == 0) {
	            leaf.value = value; 
	            return null;
	         }

	         // create new leaf node and insert into tree
	         LeafNode newLeaf = new LeafNode();
	         newLeaf.guide = key; 
	         newLeaf.value = value;

	         int offset = (cmp < 0) ? 0 : 1;
	         // offset == 0 => newLeaf inserted as left sibling
	         // offset == 1 => newLeaf inserted as right sibling

	         WorkSpace ws = new WorkSpace();
	         ws.newNode = newLeaf;
	         ws.offset = offset;
	         ws.scratch = new Node[4];

	         return ws;
	      }
	      else {
	         InternalNode q = (InternalNode) p; // downcast
	         int pos;
	         WorkSpace ws;
	         
	         q.child0.value += q.value;
			   q.child1.value += q.value;
			   
			   if(q.child2 != null){
				   q.child2.value += q.value;
			   }
			   
			   q.value = 0;
	         
	         if (key.compareTo(q.child0.guide) <= 0) {
	            pos = 0; 
	            ws = doInsert(key, value, q.child0, h-1);
	         }
	         else if (key.compareTo(q.child1.guide) <= 0 || q.child2 == null) {
	            pos = 1;
	            ws = doInsert(key, value, q.child1, h-1);
	         }
	         else {
	            pos = 2; 
	            ws = doInsert(key, value, q.child2, h-1);
	         }

	         if (ws != null) {
	            if (ws.newNode != null) {
	               // make ws.newNode child # pos + ws.offset of q

	               int sz = copyOutChildren(q, ws.scratch);
	               insertNode(ws.scratch, ws.newNode, sz, pos + ws.offset);
	               if (sz == 2) {
	                  ws.newNode = null;
	                  ws.guideChanged = resetChildren(q, ws.scratch, 0, 3);
	               }
	               else {
	            	   
	                  ws.newNode = new InternalNode();
	                  ws.offset = 1;
	                  resetChildren(q, ws.scratch, 0, 2);
	                  resetChildren((InternalNode) ws.newNode, ws.scratch, 2, 2);
	               }
	            }
	            else if (ws.guideChanged) {
	               ws.guideChanged = resetGuide(q);
	            }
	         }

	         return ws;
	      }
	   }


	   static int copyOutChildren(InternalNode q, Node[] x) {
	   // copy children of q into x, and return # of children

	      int sz = 2;
	      x[0] = q.child0; x[1] = q.child1;
	      if (q.child2 != null) {
	         x[2] = q.child2; 
	         sz = 3;
	      }
	      return sz;
	   }

	   static void insertNode(Node[] x, Node p, int sz, int pos) {
	   // insert p in x[0..sz) at position pos,
	   // moving existing extries to the right

	      for (int i = sz; i > pos; i--)
	         x[i] = x[i-1];

	      x[pos] = p;
	   }

	   static boolean resetGuide(InternalNode q) {
	   // reset q.guide, and return true if it changes.

	      String oldGuide = q.guide;
	      if (q.child2 != null)
	         q.guide = q.child2.guide;
	      else
	         q.guide = q.child1.guide;

	      return q.guide != oldGuide;
	   }


	   static boolean resetChildren(InternalNode q, Node[] x, int pos, int sz) {
	   // reset q's children to x[pos..pos+sz), where sz is 2 or 3.
	   // also resets guide, and returns the result of that

	      q.child0 = x[pos]; 
	      q.child1 = x[pos+1];

	      if (sz == 3) 
	         q.child2 = x[pos+2];
	      else
	         q.child2 = null;

	      return resetGuide(q);
	   }
	}


	class Node {
	   String guide;
	   int value;
	   // guide points to max key in subtree rooted at node
	}

	class InternalNode extends Node {
	   Node child0, child1, child2;
	   // child0 and child1 are always non-null
	   // child2 is null iff node has only 2 children
	}

	class LeafNode extends Node {
	   // guide points to the key
	}

	class TwoThreeTree {
	   Node root;
	   int height;

	   TwoThreeTree() {
	      root = null;
	      height = -1;
	   }
	}

	class WorkSpace {
	// this class is used to hold return values for the recursive doInsert
	// routine

	   Node newNode;
	   int offset;
	   boolean guideChanged;
	   Node[] scratch;
	}
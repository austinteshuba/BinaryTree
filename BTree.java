//Austin Teshuba
//BTree.java
//This is a replica of the binary tree data structure.
//it includes methods to add, delete, display, find, count leaves, find the depth of a node, find the height of the tree
//determine if a node is an ancestor of another node, and check if the tree is balanced.

public class BTree{
	private BNode root;//the root is a node
	private final int IN = 0;//stored constants for display method
	private final int PRE = 1;
	private final int POST = 2;

	public BTree(){//initializer
		root=null;
	}
	public String toString(){//returns a string describing the tree
		return stringify(root);//calls the recursive method
	}
	private String stringify(BNode branch){//returns a string describing the tree
		if(branch==null){//if the branch is null, return nothing
			return "";
		}
		else{
			return stringify(branch.getLeft()) + branch.getVal()+" "+ stringify(branch.getRight());// otherwise, return the string for the left side + the value + the string of the right side. 
		}
	}
	
	
	public BNode find(int v){//this method finds a node and returns it
		return find(v,root);//call recursive method
	}
	private BNode find(int v,BNode branch){//this recursively finds the node in O(log n) time.
		if(branch==null || branch.getVal()==v){//if the branch is null or matches the wanted value, return it
			return branch;
		}
		else{//otherwise
			if(v<branch.getVal()){//if the value is less than the branch, check to the left
				return find(v,branch.getLeft());
			}
			else{//if the value is more than the branch, check to the right.
				return find(v,branch.getRight());
			}
		}
	}
	private int depthFind(int v, BNode branch, int iter) {//this finds the depth of a given integer's node in the tree
		if (branch==null) {//if the branch is null (reached the end of the tree), the integer is not in the tree
			return -1;//-1 == not in tree
		}
		if(branch.getVal()==v){//if the branch matches the value, return the count
			return iter;
		} else {
			if(v<branch.getVal()){//if the value is less than the branch
				return depthFind(v, branch.getLeft(), iter+1);//check to the left of the node and add one to the counter
			}
			else{//if the value is greater than the branch
				return depthFind(v, branch.getRight(), iter+1);//check to the right of the node and add one to the counter
			}
		}
	}
	public int depth(int v) {//this will find the depth of a given integer
		return depthFind(v,root,1);//call the recursive function, looking for the passed in int, starting at the root with a depth of 1
	}
	
	private String disp(BNode branch, int cons) {//this recursively constructs a string describing the tree's nodes
		if (branch==null) {//if the branch is null, return nothing
			return "";
		} else {
			if (cons==IN) {//if the constant says to do it in order
				return disp(branch.getLeft(), IN) + branch.getVal() + " " + disp(branch.getRight(), IN);//left + value + right
			} else if (cons==PRE) {//if the constant says to do it in pre-order
				return branch.getVal() + " " +  disp(branch.getLeft(), PRE) +  disp(branch.getRight(), PRE);//value + left + right
			} else if (cons==POST){//if the constant says to do it in post-order
				return disp(branch.getLeft(), POST) + disp(branch.getRight(), POST) + branch.getVal() + " ";//left + right + value.
			} else {//not a valid constant, return that. 
				return "Invalid Constant";
			}
		}
	}
	public void display() {//this will display the tree's nodes in a print format. by default, it will display nodes in order
		System.out.println(disp(root, IN));
	}
	private void display(int cons) {//this will display the tree's nodes either in order, in PRE order, or POST order depending on the constant. 
		if (cons!=IN && cons!=PRE && cons!=POST) {//if the constant passed in is not valid, return void and print invalid.
			System.out.println("Invalid constant");
			return;
		}
		System.out.println(disp(root,cons));//print out the string created in the recursive function
	}
	
	private int leave(BNode branch) {
		if (branch==null) {//if the branch is null, then return 0
			return 0;
		} else if (branch.getLeft()==null && branch.getRight()==null) {//if you have found a leaf, return one
			return 1;
		} else {//not at a leaf or null point?
			return leave(branch.getLeft()) + leave(branch.getRight());//the total amount of leaves is the amount on the left and right.
		}
	}
	public int leaves() {//this calculates how many leaves there are
		return leave(root);
	}
	
	
	private int height(BNode branch) {//this gets the height of the tree/branch in question
		if(branch==null) {//if the branch is null return 0
			return 0;
		} else if (branch.getLeft()==null && branch.getRight()==null) {//if its a leaf, return one
			return 1;
		} else {//otherwise
			int a = height(branch.getLeft()) + 1;//a is the height of the left
			int b = height(branch.getRight()) + 1;//b is the height of the right
			//System.out.println((a>b) ? a : b);
			return (a>b) ? a : b;//return whatever is largest between the height of left and right. 
		}
	}
	public int height() {//this gets the height of the tree
		return height(root);//call recursive function. check height from root. 
	}
	public boolean isAncestor(int first, int second) {//this checks if the first integer is an ancestor of the second one.
		if(first==root.getVal() && second!=root.getVal()) {//if the first is just the root, the it's always true
			return true;
		} else if (first==second) {//if they are equal to each other, it is false
			return false;
		} else {//otherwise
			
			return find(second, find(first))!=null;//if you can find the second, using the first node as the starting point, it is true. otherwise, false. 
		}
	}
	
	
	
	public void add(int v){//this adds and integer to the tree
		if(root==null){//if the root is null, the added integer is now the root. 
			root=new BNode(v);
		}
		else{//otherwise, call the recursive function
			add(v,root);
		}
	}
	private void add(int v,BNode branch){//this will add a single integer to the binary tree
		if(v<branch.getVal()){//if the value is less than the branch, go to the left
			if(branch.getLeft()==null){//if the left is null, set the left to the integer as a node
				branch.setLeft(new BNode(v));
			}
			else{//if there is still a left branch, recurse and check based off the left branch
				add(v,branch.getLeft());
			}
		}
		else if(v>branch.getVal()){//if the value is greater than the branch, go to the right
			if(branch.getRight()==null){//if the right branch is null, set the right to the integer as a node
				branch.setRight(new BNode(v));
			}
			else{//if there is still a right branch, recurse and check based off the right branch
				add(v,branch.getRight());
			}
		}
	}
	
	private void graft (BNode branch, BNode comparison) {//THIS CAN ONLY WORK WITH DELETE. DONT USE IT ANYWHERE ELSE.
		if (branch==null || comparison == null) {//if the branch is null or the comparison is, just stop right now
			return;
		} 
		
		if (branch.getVal() > comparison.getVal()) {//if the branch's first node is larger than the comparison, go to the right
			//paste to the right
			if (comparison.getRight()==null) {//if there is no right branch, graft to the right
				comparison.setLeft(branch);
			} else {//if there is a right branch, recurse and compare with the right
				graft(branch, comparison.getRight());
			}
		} else if (branch.getVal() < comparison.getVal()) {//if the branch's first node is smaller than the comparison, go to the left
			if (comparison.getLeft()==null) {//if there is no left branch, graft to the left
				comparison.setLeft(branch);
			} else {//if there is a left branch, recurse and compare with the left
				graft(branch, comparison.getLeft());
			}
		}
	}
	private void findPair (int v, BNode branch, BNode prevBranch) {
		if(branch==null || branch.getVal()==v){//if the branch is just null, return the branch. otherwise, we have a hit!
			if (branch==null) {//of the branch is null, stop
				return;
			}
			if (prevBranch==null) {//if the previous branch is null, theyre deleting the root
				//oh no they're trying to delete the root.
				root = branch.getRight();//set the root to the right branch
				graft(branch.getLeft(), root);//graft the left branch onto the right one 
				return;//exit scope
			} else if (branch.getVal() > prevBranch.getVal()) {//you are deleting something to the right of the previous branch
				
				prevBranch.setRight(branch.getRight());//set the right to the branch's right branch
				graft(branch.getLeft(), prevBranch);//graft the left part of the deleted node to the previous branch.
				
			} else {//deleting something to the left of the previous branch
				prevBranch.setLeft(branch.getLeft());//set the left to the branch's left branch
				graft(branch.getRight(), prevBranch);//graft the right part of the deleted node to the previous branch
				//graft(branch.getRight(), root);
			}
			return;//exit scope
		}
		else{//if it isnt a hit
			if(v<branch.getVal()){//if the value is less than the branch, recurse with the left branch
				findPair(v,branch.getLeft(), branch);
			}
			else{//if the value is right of the branch, recurse with the right branch
				findPair(v,branch.getRight(), branch);
			}
		}
	}
	
	public void delete (int v) {
		findPair(v, root, null);//this does all of the logic
	}
	
	//////THIS IS ONE DEFINITION OF A BALANCED TREE
	/*
	private boolean isBal(BNode branch) {//this is O(n^2). see if we can do better.
		if (height(branch.getRight())+1 == height(branch.getLeft()) || height(branch.getRight()) == height(branch.getLeft()) || height(branch.getRight())-1 == height(branch.getLeft())) {
			if (branch.getRight()!=null && branch.getLeft()!=null) {
				return isBal(branch.getRight()) && isBal(branch.getLeft());
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	*/
	/*
	public boolean isBalanced() {
		if (root==null) {
			return true;//it's technically true as it has nothing in the tree.
		}
		return isBal(root);
	}
	*/
	
	//HERE IS ANOTHER DEFINITION
	public boolean isBalanced() {
		if (root==null) {//if the tree is empty, it's balanced. 
			return true;
		}
		
		int heightLeft = height(root.getLeft());//get height of right and left
		int heightRight = height(root.getRight());
		
		if (Math.abs(heightLeft-heightRight) <=1) {//if the left and right have a difference in height less than one, return true.
			return true;
		}
		return false;//otherwise, return false
	}
	
	public void add(BTree tree) {//this will add on another BTree to the current tree. 
		add(tree.root);//add starting with the root.
	}
	private void add (BNode branch) {//only use with overloaded add
		if (branch==null) {//if the branch is null, exit scope
			return;
		} else {//if the branch isn't null, add the value in the node, and the branches to the right and left of the node. 
			add(branch.getVal());
			add(branch.getRight());
			add(branch.getLeft());
		}
	}
	
	
}
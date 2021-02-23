package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 * 
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {

	/*
	 * 
	 * YOUR CODE HERE
	 * 
	 */

	private BinaryTreeNode<Key, Value> leftChild;
	private BinaryTreeNode<Key, Value> rightChild;
	private BinaryTreeNode<Key, Value> parent;
	private boolean dummy; 

	
	public BinaryTreeNode(Key k, Value v) {
		super(k, v);

		/*
		 * 
		 * This constructor is needed for the autograder. You can fill the rest to your
		 * liking. YOUR CODE AFTER THIS:
		 * 
		 */

		this.parent = null;
		this.leftChild = new BinaryTreeNode<Key, Value>();
		this.rightChild = new BinaryTreeNode<Key, Value>();
		this.leftChild.setParent(this);
		this.rightChild.setParent(this);
		this.setRightChild(rightChild);
		this.setLeftChild(leftChild);
		this.dummy = false;

	}
	
	
	public BinaryTreeNode() {
		setKey(null);
		setValue(null);
		this.leftChild = null;
		this.rightChild = null;
		this.dummy = true;
		

	}
	

	public boolean isDummy() {
		return this.dummy;
	}


	public void setDummy(boolean dummy) {
		this.dummy = dummy;
	}

	
	public BinaryTreeNode<Key, Value> getLeftChild() {
					
		return this.leftChild;
	}

	
	public void setLeftChild(BinaryTreeNode<Key, Value> leftChild) {
		this.leftChild = leftChild;
	}

	
	public BinaryTreeNode<Key, Value> getRightChild() {
		return rightChild;
	}

	
	public void setRightChild(BinaryTreeNode<Key, Value> rightChild) {
		this.rightChild = rightChild;
	}

	
	public BinaryTreeNode<Key, Value> getParent() {
	
		return this.parent;
	}

	
	public void setParent(BinaryTreeNode<Key, Value> parent) {
		this.parent = parent;
	}
	

	public boolean isRoot(BinaryTreeNode<Key, Value> n) {

		return n.getParent() == null;

	}

	
	public boolean isLeftChild(BinaryTreeNode<Key, Value> n) {

		if (n == null)
			return false;

		return (!isRoot(n) && n == n.getParent().getLeftChild());

	}

	
	public boolean isRightChild(BinaryTreeNode<Key, Value> n) {

		if (n == null)
			return false;

		return (!isRoot(n) && n == n.getParent().getRightChild());

	}

	

}

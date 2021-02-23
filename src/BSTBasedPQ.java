package code;


import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value>
		implements iAdaptablePriorityQueue<Key, Value> {

	/*
	 * 
	 * YOUR CODE BELOW THIS
	 * 
	 */


	public BSTBasedPQ() {
		super();

	}
	

	@Override
	public void insert(Key k, Value v) {
		// TODO Auto-generated method stub

		super.put(k, v);

	}

	
	public int size() {

		return super.size();
	}

	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return super.isEmpty();
	}

	
	@Override
	public Entry<Key, Value> pop() {
		// TODO Auto-generated method stub

		if (isEmpty())
			return null;

		BinaryTreeNode<Key, Value> min = getRoot();

		while (true) {

			if (min.isDummy()) {

				break;
			}

			min = min.getLeftChild();

		}
		
		min = min.getParent();
		Key k = min.getKey();
		Value v = remove(min.getKey());
		return new Entry<Key, Value>(k, v);

	}



	@Override
	public Entry<Key, Value> top() {
		// TODO Auto-generated method stub

		if (isEmpty())
			return null;

		BinaryTreeNode<Key, Value> min = getRoot();

		while (true) {

			if (min.getLeftChild().isDummy()) {

				Key k = min.getKey();
				Value v = min.getValue();
				return new Entry<Key, Value>(k, v);
			}

			min = min.getLeftChild();

		}

	}

	
	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		// TODO Auto-generated method stub

		Key tmp = null;
		BinaryTreeNode<Key, Value> node = findNode(getRoot(), entry.getKey());

		if (node == null)
			return null;

		if (getComparator().compare(entry.getKey(), node.getKey()) == 0 && node.getValue().equals(entry.getValue())) {
			tmp = entry.getKey();
			remove(entry.getKey());
			put(k, entry.getValue());
			return tmp;

		}

		return tmp;
	}

	
	@Override
	public Key replaceKey(Value v, Key k) {
		// TODO Auto-generated method stub

		Key tmp = null;
		if (isEmpty())
			return null;

		for (BinaryTreeNode<Key, Value> node : super.getNodesInOrder()) {

			if (node.getValue().equals(v)) {

				tmp = node.getKey();
				remove(node.getKey());
				put(k, v);
				return tmp;

			}

		}

		return tmp;
	}

	
	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		// TODO Auto-generated method stub
		Value val = null;
		BinaryTreeNode<Key, Value> node = findNode(getRoot(), entry.getKey());

		if (node == null)
			return null;

		if (getComparator().compare(entry.getKey(), node.getKey()) == 0 && node.getValue().equals(entry.getValue())) {
			val = entry.getValue();
			node.setValue(v);
			return val;
		}

		return val;

	}

}

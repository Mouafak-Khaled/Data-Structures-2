package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import given.DefaultComparator;
import given.iBinarySearchTree;
import given.iMap;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {

	/*
	 * 
	 * YOUR CODE BELOW THIS
	 * 
	 */

	private BinaryTreeNode<Key, Value> root;
	private Comparator<Key> C;
	private int size;
	private ArrayList<BinaryTreeNode<Key, Value>> binaryTreeNodes;

	public BinarySearchTree() {

		this.root = new BinaryTreeNode<Key, Value>();
		this.C = (Comparator<Key>) new DefaultComparator<Integer>();
		this.size = 0;

	}

	public void setRoot(BinaryTreeNode<Key, Value> root) {
		this.root = root;

	}

	@Override
	public Value get(Key k) {

		return getValue(k);

	}

	@Override
	public Value put(Key k, Value v) {

		BinaryTreeNode<Key, Value> newNode = new BinaryTreeNode<Key, Value>(k, v);
		BinaryTreeNode<Key, Value> current = getRoot();

		while (true) {

			if (isEmpty()) {

				setRoot(newNode);
				newNode.setParent(null);
				size++;
				return null;

			} else if (current.isDummy()) {

				size++;
				break;

			} else if (!current.isDummy() && getComparator().compare(k, current.getKey()) == 0) {

				Value tmp = current.getValue();
				current.setValue(v);
				return tmp;

			} else if (!current.isDummy() && getComparator().compare(k, current.getKey()) > 0) {

				current = current.getRightChild();

			} else {

				current = current.getLeftChild();
			}
		}

		if (isLeftChild(current)) {

			current.getParent().setLeftChild(newNode);
			newNode.setParent(current.getParent());

		} else if (isRightChild(current)) {

			current.getParent().setRightChild(newNode);
			newNode.setParent(current.getParent());
		}

		return null;

	}

	@Override
	public Value remove(Key k) {

		BinaryTreeNode<Key, Value> node = getNode(k);

		if (node == null) {
			return null;

		} else {

			size--;

			if (isExternal(node.getLeftChild()) && isExternal(node.getRightChild())) {

				Value tmp = node.getValue();
				node.setDummy(true);
				return tmp;
			}

			if (isInternal(node.getLeftChild()) && isExternal(node.getRightChild())) {

				Value tmp = node.getValue();

				if (isRoot(node)) {

					node.getLeftChild().setParent(node.getParent());
					setRoot(node.getLeftChild());
					node.setDummy(true);

				} else if (isLeftChild(node)) {

					node.getParent().setLeftChild(node.getLeftChild());
					node.getLeftChild().setParent(node.getParent());
					node.setDummy(true);

				} else {
					node.getParent().setRightChild(node.getLeftChild());
					node.getLeftChild().setParent(node.getParent());
					node.setDummy(true);

				}

				return tmp;

			}

			if (isInternal(node.getRightChild()) && isExternal(node.getLeftChild())) {

				Value tmp = node.getValue();
				if (isRoot(node)) {

					node.getRightChild().setParent(node.getParent());
					setRoot(node.getRightChild());
					node.setDummy(true);

				} else if (isLeftChild(node)) {

					node.getParent().setLeftChild(node.getRightChild());
					node.getRightChild().setParent(node.getParent());
					node.setDummy(true);

				} else {

					node.getParent().setRightChild(node.getRightChild());
					node.getRightChild().setParent(node.getParent());
					node.setDummy(true);

				}

				return tmp;

			} else {

				Value tmp = node.getValue();
				BinaryTreeNode<Key, Value> predecessor = findPredecessor(k);

				node.setKey(predecessor.getKey());
				node.setValue(predecessor.getValue());

				if (predecessor.getLeftChild().isDummy())
					predecessor.setDummy(true);

				else if (isInternal(predecessor.getLeftChild()) && predecessor == node.getLeftChild()) {

					predecessor.getParent().setLeftChild(predecessor.getLeftChild());
					predecessor.getLeftChild().setParent(predecessor.getParent());

				} else {

					predecessor.getParent().setRightChild(predecessor.getLeftChild());
					predecessor.getLeftChild().setParent(predecessor.getParent());

				}

				return tmp;

			}

		}
		
		

	}

	
	// a helper method to find the predecessor of a node
	public BinaryTreeNode<Key, Value> findPredecessor(Key k) {

		BinaryTreeNode<Key, Value> tmp = null;
		BinaryTreeNode<Key, Value> node = getNode(k);

		if (isExternal(node.getLeftChild())) {

			return tmp;

		} else {

			tmp = node.getLeftChild();

			while (!isExternal(tmp.getRightChild())) {

				tmp = tmp.getRightChild();

			}
		}
		return tmp;
	}

	
	@Override
	public Iterable<Key> keySet() {

		ArrayList<Key> keys = new ArrayList<Key>();

		for (BinaryTreeNode<Key, Value> node : getNodesInOrder()) {

			keys.add(node.getKey());
		}

		return keys;

	}

	
	@Override
	public int size() {

		return this.size;
	}

	
	@Override
	public boolean isEmpty() {

		return size() == 0;
	}

	
	@Override
	public BinaryTreeNode<Key, Value> getRoot() {

		return this.root;
	}

	
	@Override
	public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {

		return node.getParent();
	}

	
	
	@Override
	public boolean isInternal(BinaryTreeNode<Key, Value> node) {

		return !isExternal(node);

	}

	
	
	@Override
	public boolean isExternal(BinaryTreeNode<Key, Value> node) {

		if (isEmpty() || node == null)
			return true;

		return node.isDummy();
	}

	
	@Override
	public boolean isRoot(BinaryTreeNode<Key, Value> node) {

		return node.isRoot(node);
	}

	
	
	@Override
	public BinaryTreeNode<Key, Value> getNode(Key k) {

		BinaryTreeNode<Key, Value> tmp = null;

		for (BinaryTreeNode<Key, Value> node : getNodesInOrder()) {

			if (C.compare(k, node.getKey()) == 0) {

				tmp = node;
				break;
			}
		}

		return tmp;
	}

	
	@Override
	public Value getValue(Key k) {

		BinaryTreeNode<Key, Value> node = findNode(getRoot(), k);

		if (isExternal(node))
			return null;

		Value val = node.getValue();
		return val;

	}

	
	
	public BinaryTreeNode<Key, Value> findNode(BinaryTreeNode<Key, Value> node, Key k) {

		if (isExternal(node)) {

			return null;

		} else if (getComparator().compare(k, node.getKey()) == 0) {

			return node;

		} else if (getComparator().compare(k, node.getKey()) > 0) {

			node = findNode(node.getRightChild(), k);
		} else {

			node = findNode(node.getLeftChild(), k);

		}

		return node;

	}

	
	@Override
	public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {

		return node.getLeftChild();
	}

	
	@Override
	public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {

		return node.getRightChild();
	}

	
	@Override
	public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {

		if (isRoot(node) || isExternal(node)) {
			return null;

		} else if (isLeftChild(node)) {

			return node.getParent().getRightChild();
		} else {

			return node.getParent().getLeftChild();
		}

	}

	
	@Override
	public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {

		return node.isLeftChild(node);
	}

	
	@Override
	public boolean isRightChild(BinaryTreeNode<Key, Value> node) {

		return node.isRightChild(node);
	}

	
	@Override
	public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {

		binaryTreeNodes = new ArrayList<BinaryTreeNode<Key, Value>>();

		if (isEmpty())
			return binaryTreeNodes;

		traverseInOrder(getRoot());
		return binaryTreeNodes;

	}

	
	@Override
	public void setComparator(Comparator<Key> C) {

		this.C = C;

	}

	
	@Override
	public Comparator<Key> getComparator() {

		return this.C;

	}

	
	@Override
	public BinaryTreeNode<Key, Value> ceiling(Key k) {

		BinaryTreeNode<Key, Value> tmp = null;
		for (BinaryTreeNode<Key, Value> node : getNodesInOrder()) {

			if (C.compare(k, node.getKey()) <= 0) {

				tmp = node;
				break;
			}

		}
		return tmp;

	}

	
	@Override
	public BinaryTreeNode<Key, Value> floor(Key k) {

		BinaryTreeNode<Key, Value> tmp = null;

		for (BinaryTreeNode<Key, Value> node : getNodesInOrder()) {

			if (C.compare(node.getKey(), k) >= 0) {

				break;
			}

			tmp = node;

		}
		return tmp;
	}

	
	public void traverseInOrder(BinaryTreeNode<Key, Value> node) {

		if (isInternal(node)) {

			traverseInOrder(node.getLeftChild());

			binaryTreeNodes.add(node);

			traverseInOrder(node.getRightChild());

		}

	}

	
	
}

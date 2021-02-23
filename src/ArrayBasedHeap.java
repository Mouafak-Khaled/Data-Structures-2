package code;

import java.util.ArrayList;
import java.util.Comparator;
import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 * 
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	// Use this arraylist to store the nodes of the heap.
	// This is required for the autograder.
	// It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but
	// then you do not have to deal with dynamic resizing
	protected ArrayList<Entry<Key, Value>> nodes;
	private Comparator<Key> C;

	/*
	 * 
	 * YOUR CODE BELOW THIS
	 * 
	 */

	public ArrayBasedHeap(Comparator<Key> comp) {

		this.C = comp;
		this.nodes = new ArrayList<Entry<Key, Value>>();

	}

	
	public ArrayBasedHeap() {

		this.nodes = new ArrayList<Entry<Key, Value>>();
	}

	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return nodes.size();
	}

	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return nodes.isEmpty();
	}

	
	@Override
	public void setComparator(Comparator<Key> C) {
		// TODO Auto-generated method stub
		this.C = C;

	}

	
	@Override
	public Comparator<Key> getComparator() {
		// TODO Auto-generated method stub
		return this.C;
	}

	
	@Override
	public void insert(Key k, Value v) {
		// TODO Auto-generated method stub

		Entry<Key, Value> newEntry = new Entry<Key, Value>(k, v);
		nodes.add(newEntry);
		upHeap(nodes.indexOf(newEntry));

	}

	
	public void upHeap(int index) {

		while (index > 0) {

			int p = getParent(index);

			if (getComparator().compare(nodes.get(index).getKey(), nodes.get(p).getKey()) >= 0)
				break;

			swap(index, p);

			index = p;

		}

	}

	
	public void downHeap(int index) {
		
		while (hasLeftChild(index)) {

			int li = leftChildIndex(index);
			int smallChild = li;

			if (hasRightChild(index)) {

				int ri = rightChildIndex(index);

				if (li != -1 && getComparator().compare(nodes.get(li).getKey(), nodes.get(ri).getKey()) > 0)
					smallChild = ri;
			}

			if (getComparator().compare(nodes.get(smallChild).getKey(), nodes.get(index).getKey()) >= 0)
				break;

			swap(index, smallChild);
			index = smallChild;
		}

	}
	
	
	public int getParent(int index) {

		if (index <= 0 || index >= nodes.size())
			return -1;

		return (index - 1) / 2;

	}

	
	public boolean hasLeftChild(int index) {

		boolean hasLeft = leftChildIndex(index) < nodes.size() && leftChildIndex(index) > 0;
		return hasLeft;

	}

	
	public boolean hasRightChild(int index) {

		boolean hasRight = rightChildIndex(index) < nodes.size() && rightChildIndex(index) > 0;
		return hasRight;
	}

	
	public int leftChildIndex(int index) {

		int i = (2 * index + 1);
		return i;

	}

	
	public int rightChildIndex(int index) {

		int i = (2 * index + 2);
		return i;

	}

	
	public void swap(int index, int j) {

		Entry<Key, Value> tmp = nodes.get(index);
		nodes.set(index, nodes.get(j));
		nodes.set(j, tmp);

	}

	
	@Override
	public Entry<Key, Value> pop() {

		if (isEmpty())
			return null;
		Entry<Key, Value> min = nodes.get(0);
		swap(0, nodes.size() - 1);
		nodes.remove(nodes.size() - 1);
		downHeap(0);

		return min;
	}

	
	@Override
	public Entry<Key, Value> top() {
		// TODO Auto-generated method stub

		if (isEmpty())
			return null;

		return nodes.get(0);
	}

	
	@Override
	public Value remove(Key k) {

		Value tmp = null;
		int index = -1;
		int i = 0;

		if (isEmpty())
			return null;

		for (Entry<Key, Value> en : nodes) {
			if (getComparator().compare(k, en.getKey()) == 0) {
				index = nodes.indexOf(en);
				tmp = en.getValue();
				break;
			}
		}

		i = getParent(index);
		if (index == (nodes.size() - 1)) {
			nodes.remove(index);

		} else if (index == -1)
			return null;

		else {

			swap(index, nodes.size() - 1);
			nodes.remove(nodes.size() - 1);

			if (index > 0 && getComparator().compare(nodes.get(index).getKey(), nodes.get(i).getKey()) < 0)
				upHeap(index);

			else
				downHeap(index);

		}

		return tmp;

	}

	
	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {

		Key tmp = null;

		if (isEmpty())
			return null;

		int index = -1;
		int i = 0;
		for (Entry<Key, Value> en : nodes) {
			if (getComparator().compare(entry.getKey(), en.getKey()) == 0 && en.getValue().equals(entry.getValue())) {
				tmp = en.getKey();
				en.setKey(k);
				index = nodes.indexOf(en);
				break;
			}
		}

		i = getParent(index);

		if (index == -1)
			return null;

		else if (index > 0 && getComparator().compare(nodes.get(index).getKey(), nodes.get(i).getKey()) < 0)
			upHeap(index);

		else
			downHeap(index);

		return tmp;

	}

	
	@Override
	public Key replaceKey(Value v, Key k) {

		Key tmp = null;

		if (isEmpty())
			return null;

		int index = -1;
		int i = 0;

		for (Entry<Key, Value> en : nodes) {
			if (en.getValue().equals(v)) {
				tmp = en.getKey();
				en.setKey(k);
				index = nodes.indexOf(en);
				break;

			}
		}

		i = getParent(index);

		if (index == -1)
			return null;

		else if (index > 0 && getComparator().compare(nodes.get(index).getKey(), nodes.get(i).getKey()) < 0)
			upHeap(index);

		else
			downHeap(index);

		return tmp;

	}
	

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {

		Value tmp = null;

		if (isEmpty())
			return null;

		for (Entry<Key, Value> en : nodes) {
			if (getComparator().compare(entry.getKey(), en.getKey()) == 0 && en.getValue().equals(entry.getValue())) {
				tmp = en.getValue();
				en.setValue(v);
				break;

			}
		}

		return tmp;
	}

	
}

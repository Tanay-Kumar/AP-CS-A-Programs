/**
 *	SinglyLinkedList class represents a singly linked list data structure.
 *	It stores a collection of elements in a linear sequence, where each 
 * 	element is connected to the next element via a reference (pointer).
 * 	SinglyLinkedList supports various operations for manipulating the list,
 * 	such as adding elements, removing elements, and searching for elements.
 * 	The list allows elements of a generic type that implements the Comparable 
 * 	interface.
 * 
 *
 *
 *	@author	Tanay Kumar
 *	@since	May 6th, 2024
 */
 
import java.util.NoSuchElementException;

public class SinglyLinkedList<E extends Comparable<E>>
{
	/* Fields */
	private ListNode<E> head, tail;		// head and tail pointers to list
	private int size;					// number of elements in the list
	
	/* No-args Constructors */
	public SinglyLinkedList()
	{
		// Initialize the new list's head, tail, and size
		head = null;
		tail = null;
		size = 0;
	}
	
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList)
	{
		// Initialize the new list's head, tail, and size
		head = null;
		tail = null;
		size = 0;
		
		// Check if the old list is not null
		if (oldList != null)
		{
			// Iterate through the nodes of the old list
			ListNode<E> current = oldList.head;
			
			// Add each node's value to the new list
			while(current != null)
			{
				add(current.getValue());
				current = current.getNext();
			}
		}
	}
	
	/**	Clears the list of elements */
	public void clear()
	{
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * 	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj)
	{
		// Create a new node with the given object
		ListNode<E> node = new ListNode<E>(obj);
		
		// If the list is empty, set both head and tail to the new node
		if (head == null)
		{
			head = node;
			tail = node;
		}
		
		// Otherwise, add the new node after the current tail and update tail
		else
		{
			tail.setNext(node);
			tail = node;
		}
		
		size++; // Increment the size of the list
		return true;
	}
	
	/**
	 * 	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj)
	{
		// If the index is out of bounds, throw NoSuchElementException
		if (index < 0 || index > size)
		{
			throw new NoSuchElementException();
		}

		// Create a new node with the given object
		ListNode<E> node = new ListNode<E>(obj);
		
		// If adding at the beginning of the list
		if (index == 0)
		{
			node.setNext(head);
			head = node;
			
			// Update tail if the list was empty
			if (tail == null)
			{
				tail = node;
			}
		}
		
		// If adding at the end of the list
		else if (index == size)
		{
			tail.setNext(node);
			tail = node;
		}
		
		// If adding in the middle of the list
		else
		{
			ListNode<E> prev = head;
			
			// Traverse to the node before the specified index
			for (int i = 0; i < index - 1; i++)
			{
				prev = prev.getNext();
			}
			
			node.setNext(prev.getNext());
			prev.setNext(node);
		}

		size++; // Increment the size of the list
		
		return true;
	}
	
	/**	@return the number of elements in this list */
	public int size()
	{
		return size;
	}
	
	/**
	 * 	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index)
	{
		// If the index is out of bounds, throw NoSuchElementException
		if(index < 0 || index >= size)
		{
			throw new NoSuchElementException();
		}

		ListNode<E> node = head;
		
		// Traverse to the specified index
		for(int i = 0; i < index; i++)
		{
			node = node.getNext();
		}

		return node;
	}
	
	/**
	 * 	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj)
	{
		// If the index is out of bounds, throw NoSuchElementException
		if(index < 0 || index >= size)
		{
			throw new NoSuchElementException();
		}
	
		ListNode<E> node = head;
		
		// Traverse to the specified index
		for(int i = 0; i < index; i++)
		{
			// If the node is null, the index is invalid, so throw NoSuchElementException
			if (node == null)
			{
				throw new NoSuchElementException();
			}
			
			node = node.getNext();
		}
		
		// Store the old value before replacing it with the new one
		E old = node.getValue();
		node.setValue(obj);
		
		return old;
	}

	/**
	 * 	Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index)
	{
		E val;
		
		// If the list is empty or if the index is out of bounds, 
		// throw NoSuchElementException
		if (isEmpty() || index < 0 || index >= size)
		{
			throw new NoSuchElementException();
		}

		// If removing the first element
		if (index == 0)
		{
			val = head.getValue();
			head = head.getNext();
			
			// Update tail if head becomes null (list becomes empty)
			if (head == null)
			{
				tail = null;
			}
		}
		
		// If removing an element from the middle or end of the list
		else
		{
			ListNode<E> prev = head;
			
			// Traverse to the node before the specified index
			for(int i = 0; i < index - 1; i++)
			{
				prev = prev.getNext();
			}

			val = prev.getNext().getValue();
			prev.setNext(prev.getNext().getNext());
			
			// Update tail if removing the last element
			if (index == size - 1)
			{
				tail = prev;
			}
		}
		
		size--; // Decrement the size of the list
		
		return val;
	}

	/**
	 *	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the
	 * @return true if the list is empty; false otherwise
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}
  
	/**
	 *	Return true if the list contains the specified object
	 *	@param obj		the object to search for
	 *	@return			true if the list contains the specified object; false otherwise
	 */
	public boolean contains(E value)
	{
		ListNode<E> curr = head;
		
		// Traverse the list to find the specified object
		while(curr != null)
		{
			if(curr.getValue().equals(value))
			{
				return true;
			}

			curr = curr.getNext();
		}
		
		return false;
	}

	/**
	 *	Return the index of the specified object
	 *	@param obj		the object to search for
	 *	@return			the index of the specified object; -1 if not found
	 */
	public int indexOf(E obj)
	{
		int index = 0;
		ListNode<E> curr = head;
		
		while(curr != null)
		{
			// Traverse the list to find the index of the specified object
			if(curr.getValue().equals(obj))
			{
				return index;
			}
			
			curr = curr.getNext();
			index++;
		}
		
		return -1; // Return -1 if the object is not found in the list
	}
	
	/**	Prints the list of elements */
	public void printList()
	{
		ListNode<E> ptr = head;
		while(ptr != null)
		{
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}
}

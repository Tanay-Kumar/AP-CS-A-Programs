import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Binary Tree for State Objects
 *
 * @author Tanay Kumar
 * @version May 30th, 2024
 */
public class BinaryTree<E extends Comparable<E>>
{
	private final String DEFAULT_FILE_NAME = "states2.txt"; // Default input file
	private Scanner keyboard; // Scanner for reading the file
    private TreeNode<State> root; // Root node of the binary tre
	
	/**
     * Constructs an empty binary tree with no root.
     */
    public BinaryTree() 
    {
        root = null; // Initialize the tree as empty
    }

    /**
     * Loads data from a text file into the binary tree. Assumes data is formatted
     * correctly in the specified file.
     */
    public void loadData()
    {
        // Open the file for reading
        Scanner input = FileUtils.openToRead(DEFAULT_FILE_NAME);

        // Read through the file line by line
        while(input.hasNextLine())
        {            
            // Read the data for a single state
            String name = input.next();
            String abbreviation = input.next();
            int population = input.nextInt();
            int area = input.nextInt();
            int reps = input.nextInt();
            String capital = input.next();
            int month = input.nextInt();
            int day = input.nextInt();
            int year = input.nextInt();
            
            // Create a new State object from read data
            State state = new State(name, abbreviation, population, area, 
									reps, capital, month, day, year);
            
            // Insert the newly created state into the binary tree
            insert(state);
        }
        
        // Close the input file after reading
        input.close();
        System.out.println("Loading file " + DEFAULT_FILE_NAME + "\n");
    }
    
    /**
     * Inserts a new state into the binary tree. If the tree is empty, the new state
     * becomes the root; otherwise, it inserts the state in the correct position to
     * maintain the binary search tree property.
     * 
     * @param next 			State to be inserted
     */
    public void insert(State next) 
    {
		// If the tree is empty, create a new TreeNode with the state and make it the root
        if (root == null)
        {
            TreeNode<State> node = new TreeNode<State>(next);
            root = node;
        }
        
        // If the tree is not empty, use a recursive method to find the correct position
        else
        {
            addRecursive(next, root);
        }
    }

	/**
	 * Prints the binary tree as a list in ascending order by state name.
	 */
	public void printList()
	{
		// Call the recursive method to print nodes in order
		printInOrder(root);
		System.out.println(); // Print a newline for clean output separation
	}

	/**
	 * Recursive helper method to print the tree in inorder traversal.
	 * This method visits nodes in the following order: left child, current node, right child.
	 * 
	 * @param node the current node being visited
	 */
	public void printInOrder(TreeNode<State> node)
	{
		if (node != null)
		{
			// Recursively print the left subtree first
			printInOrder(node.getLeft());
			
			// Print the value of the current node followed by a space
			System.out.print(node.getValue() + " ");
			
			// Recursively print the right subtree
			printInOrder(node.getRight());
		}
	}
	
	/**
	 * Prints  the binary tree in preorder traversal
	 */
	public void printPreorder()
	{
		// Start the recursive preorder printing from the root
		printPreorderRecurs(root);
		// Print a newline at the end for better output formatting
		System.out.println();
	}

	/**
	 * Recursive helper method to print the tree in preorder traversal.
	 * Preorder traversal involves visiting the root before its children.
	 *
	 * @param node 		the current node being visited
	 */
	private void printPreorderRecurs(TreeNode<State> node)
	{
		// Check if the current node is not null
		if (node != null)
		{
			// Print the value of the current node followed by a space
			System.out.print(node.getValue() + " ");
			
			// Recursively print the left subtree
			printPreorderRecurs(node.getLeft());
			
			// Recursively print the right subtree
			printPreorderRecurs(node.getRight());
		}
	}
	
	/**
	 * Print the binary tree using postorder traversal.
	 */
	public void printPostorder()
	{
		// Start postorder traversal from the root
		printPostorderRecurs(root);
		
		// Print a newline after completing the traversal
		System.out.println();
	}

	/**
	 * Recursively prints the tree in postorder.
	 * In postorder traversal, the nodes are visited in the following order:
	 * left subtree, right subtree, and then the node itself.
	 * 
	 * @param node 			the current node being visited
	 */
	public void printPostorderRecurs(TreeNode<State> node)
	{
		// Check if the current node is not null
		if (node != null) 
		{
			// Recursively visit the left child
			printPostorderRecurs(node.getLeft());
			
			// Recursively visit the right child
			printPostorderRecurs(node.getRight());
			
			// Process the current node (print its value)
			System.out.print(node.getValue() + " ");
		}
	}

	/**
	 * Prompts user for State name to find, then starts the search process.
	 */
	public void testFind()
	{
		System.out.println("\n\nTesting search algorithm");
		search();
	}

	/**
	 * Searches the tree for a State specified by the user.
	 * If the State is not found, notifies the user.
	 */
	public void search()
	{
		// Prompt user for a state name, allowing case-insensitive quit option
		String stateName = Prompt.getString("\nEnter state name to search for (Q to quit)");
		stateName = stateName.toLowerCase();
		
		if(stateName.equals("q"))
		{
			System.out.println(); // Just print a newline for clean output
			return;
		}
		
		// Start the search for the state
		State state = find(root, stateName);
		
		if(state != null)
		{
			// Print the found state and its details
			System.out.println("\n" + state);
			
			search(); // Continue searching
		}
		
		else
		{
			// Inform the user that no state with the given name exists
			System.out.println("Name = " + stateName + "  No such state name");
			
			search(); // Continue searching
		}
	}

	/**
	 * Recursive method to find a State node in the binary tree.
	 * 
	 * @param node 		the current node being visited
	 * @param name 		the name of the state to find
	 * @return 			the State object if found, otherwise null
	 */
	public State find(TreeNode<State> node, String name)
	{
		// Base case: if node is null, state is not found
		if(node == null)
		{
			return null;
		}
		
		// Compare the provided name with the name of the state at the current node
		int compare = name.compareTo(node.getValue().getName().toLowerCase());
		
		// Name is lexicographically less, search left
		if(compare < 0)
		{
			return find(node.getLeft(), name); 
		}
		
		// Name is lexicographically greater, search right
		else if(compare > 0)
		{
			return find(node.getRight(), name); 
		}
		
		 // Names are equal, state found
		else
		{
			return node.getValue();
		}
	}

	/**
	 * Initiates a testing sequence for deleting a state from the binary tree.
	 * This method is optional and not included in grading.
	 */
	public void testDelete()
	{
		System.out.println("\n\nTesting delete algorithm");
		promptDelete();
	}

	/**
	 * Prompts the user for a state name to delete from the binary tree.
	 * Allows the user to exit the prompt by entering 'Q'.
	 */
	public void promptDelete()
	{
		// Prompt user to enter a state name or 'Q' to quit
		String stateName = Prompt.getString("\nEnter state name to delete for (Q to quit)");
		stateName = stateName.toLowerCase(); // Convert input to lower case for case-insensitivity
		
		// Exit if user enters 'Q'
		if(stateName.equals("q"))
		{
			System.out.println();
			return; 
		} 
		
		// Attempt to find the state in the binary tree
		State state = find(root, stateName);
		
		// If the state is found, remove it from the tree
		if (state != null)
		{
			remove(root, state);
			System.out.println("\nDeleted " + stateName);
			promptDelete(); // Re-prompt after deletion
		}
		
		// If no state matches the input, inform the user and re-prompt
		else
		{
			System.out.println("\nName = " + stateName + "  No such state name");
			promptDelete();
		}
	}
	
	/**
	 * Recursively removes a value from the binary tree starting from the 
	 * given node. Handles different cases of removal based on the node's 
	 * children.
	 * 
	 * @param node 		the root of the subtree where the value should be removed
	 * @param value 	the value to remove from the subtree
	 * @return 			TreeNode that reconnects the subtree correctly after removal
	 */
	public TreeNode<State> remove(TreeNode<State> node, State value)
	{
		// Base case: if the subtree is empty, nothing to remove
		if (node == null) 
		{
			return null;
		}

		// Compare the value to be removed with the current node's value
		int compare = value.compareTo(node.getValue());

		// Value is less than current node's value; continue in the left subtree
		if (compare < 0) 
		{
			
			node.setLeft(remove(node.getLeft(), value));
		} 
		
		// Value is greater than current node's value; continue in the right subtree
		else if (compare > 0) 
		{
			
			node.setRight(remove(node.getRight(), value));
		} 
		
		// Found the node to remove (value matches the node's value)
		else 
		{
			// Node is a leaf node, remove it by returning null
			if (node.getLeft() == null && node.getRight() == null) 
			{
				node = null;
			}
			 
			// Node has only a left child, replace node with its left child
			else if (node.getRight() == null) 
			{
				node = node.getLeft();
			} 
			
			// Node has only a right child, replace node with its right child
			else if (node.getLeft() == null) 
			{
				node = node.getRight();
			} 
			
			// Node has both left and right children
			else 
			{
				// Find the minimum node in the right subtree to replace the current node
				TreeNode<State> minNode = findMin(node.getRight());
				
				// Replace the current node's value with the found minimum value
				node.setValue(minNode.getValue());
				
				// Remove the minimum node from the right subtree
				node.setRight(remove(node.getRight(), minNode.getValue()));
			}
		}

		// Return the node to reconnect with its parent, if any
		return node;
	}
	
	/**
	 * Finds the minimum node starting from the given node. Assumes that
	 * the smallest value is located in the leftmost node.
	 * 
	 * @param node 			the starting node to find the minimum
	 * @return 				the node with the minimum value in the subtree
	 */
	public TreeNode<State> findMin(TreeNode<State> node)
	{
		// The minimum value in a Binary Tree is the leftmost node
		if(node.getLeft() == null) 
		{
			// No more left child, this node is the minimum
			return node;
		}

		// Recursively find the minimum in the left subtree
		return findMin(node.getLeft());
    }
	
	/**
	 * Finds the number of nodes starting from the root of the tree.
	 * 
	 * @return 		the total number of nodes in the binary tree.
	 */
	public int size()
	{
		// Delegates to a helper method to count nodes recursively
		return countNodes(root);
	}

	/**
	 * Recursively counts all the nodes in the tree from a given node.
	 * 
	 * @param node 		the current node from which to count nodes
	 * @return 			the count of nodes in the subtree rooted at 'node'
	 */
	public int countNodes(TreeNode<State> node)
	{
		// If the current node is null, return 0 - no nodes to count
		if (node == null)
		{
			return 0;
		}

		// Recursively count the nodes in the left subtree
		int leftCount = countNodes(node.getLeft());
		
		// Recursively count the nodes in the right subtree
		int rightCount = countNodes(node.getRight());

		// The total count is 1 (for the current node) plus the counts of left and right subtrees
		return 1 + leftCount + rightCount;
	}

	/**
	 * Clears the tree of all nodes, effectively deleting all data.
	 */
	public void clear()
	{
		// Inform user that the tree is being cleared
		System.out.println("Tree cleared\n");
		
		// Set root to null, removing all references to the nodes in the tree
		root = null;
	}

	/**
	 * Prompts the user for the level of the tree to print.
	 * The top level (root node) is level 0.
	 */
	public void printLevel()
	{
		System.out.println("\n\nTesting printLevel algorithm");
		
		// Start the interactive prompt for printing a specific tree level
		promptLevel();
	}

	/**
	 * Prompts the user for the level of the tree to print 
	 * and prints that level. The top level (root node) is level 0.
	 */
	public void promptLevel()
	{
		// Prompt the user to enter a level number, with -1 as the option to quit
		int origLevel = Prompt.getInt("\nEnter level value to print (-1 to quit)");
		int curLevel = 0;
		
		// If the user chooses to quit, exit the method
		if (origLevel == -1)
		{
			System.out.println("\n");
			return;
		}
		// Display the level that will be printed
		System.out.println("\nLevel   " + origLevel);
		
		// Call recursive method to print nodes at the specified level
		printLevelRecurs(root, origLevel, curLevel);
		System.out.println();
		
		// Recursive call to continue prompting for levels until the user decides to quit
		promptLevel();
	}

	/**
	 * Recursively traverses the tree to find and print the names of all 
	 * states at a specified level.
	 * 
	 * @param node 			current state node of level
	 * @param ogLvl 		the destination level/depth to print
	 * @param curLel 		the current level/depth during traversal
	 */
	public void printLevelRecurs(TreeNode<State> node, int ogLvl, int curLvl)
	{
		// If there are no more nodes along this path, return without printing
		if (node == null)
		{
			return;
		}
		
		// If the current level matches the target level, print the state name
		else if(curLvl == ogLvl)
		{
			System.out.print(node.getValue().getName() + "   ");
		}
		
		// Recursively explore the left and right subtrees at the next level
		else
		{
			printLevelRecurs(node.getLeft(), ogLvl, curLvl + 1);
			printLevelRecurs(node.getRight(), ogLvl, curLvl + 1);
		}
	}
	
	/**
	 * Prints the highest level of the tree (with root being level 0).
	 * If the tree is empty, it outputs a message indicating that.
	 */
	public void testDepth()
	{
		// If the tree has no nodes (root is null), print "Tree empty"
		if(root == null)
		{
			System.out.println("Tree empty\n");
		}
		
		// Calculate the depth of the tree and adjust by subtracting 1 since root is level 0
		else
		{
			System.out.printf("Depth of tree = %d\n\n", getDepth(root) - 1);
		}
	}

	/**
	 * Recursively calculates the maximum depth of the binary tree starting from the given node.
	 * The depth is defined as the number of nodes along the longest path from the given node
	 * down to the farthest leaf node.
	 * 
	 * @param node The node from which depth is to be calculated.
	 * @return the depth of the tree. Returns 0 if the node is null, indicating no depth.
	 */
	public int getDepth(TreeNode<State> node)
	{
		// If the current node is null, return 0 - base case for recursion
		if (node == null)
		{
			return 0;
		}

		// Recursively get the depth of the left subtree
		int leftDepth = getDepth(node.getLeft());
		
		// Recursively get the depth of the right subtree
		int rightDepth = getDepth(node.getRight());

		// Return the greater of the two depths plus one for the current node
		return Math.max(leftDepth, rightDepth) + 1;
	}
    
	/**
	 * Returns a balanced version of this binary tree.
	 * A balanced tree is one where the depth of two subtrees of every node 
	 * never differ by more than one.
	 *
	 * @return 			the balanced binary tree
	 */
	public BinaryTree<State> makeBalancedTree()
	{
		// Create a new binary tree to hold the balanced version
		BinaryTree<State> balancedTree = new BinaryTree<State>();
		
		// Create a list to store the elements of the tree in inorder traversal
		List<State> values = new ArrayList<State>();
		
		// Fill the list with elements from the tree in inorder fashion
		storeInorder(root, values);
		
		// Build a balanced tree from the sorted list of values and assign its root to the new tree
		balancedTree.root = buildBalancedTree(values, 0, values.size() - 1);

		// Return the newly created balanced tree
		return balancedTree;
	}

	/**
	 * Recursively stores the values of the tree in inorder traversal.
	 * Inorder traversal visits the left subtree, the root, and then the 
	 * right subtree.
	 *
	 * @param node 			the current node being visited
	 * @param values 		the list to store the values
	 */
	public void storeInorder(TreeNode<State> node, List<State> values)
	{
		// Check if the current node is not null
		if (node != null)
		{
			// Recursively store values from the left subtree
			storeInorder(node.getLeft(), values);
			
			// Add the current node's value to the list
			values.add(node.getValue());
			
			// Recursively store values from the right subtree
			storeInorder(node.getRight(), values);
		}
	}

	/**
	 * Recursively builds a balanced binary tree from a sorted list of 
	 * values. This method uses the divide and conquer technique by choosing 
	 * the middle element as the root.
	 *
	 * @param values 		the sorted list of values
	 * @param start 		the start index of the current sublist
	 * @param end 			the end index of the current sublist
	 * @return 				the root node of the balanced subtree
	 */
	public TreeNode<State> buildBalancedTree(List<State> values, int start, int end)
	{
		if (start <= end)
		{
			// Calculate the middle index of the current sublist
			int mid = (start + end) / 2;
			
			// Create a new node with the middle value
			TreeNode<State> node = new TreeNode<State>(values.get(mid));

			// Recursively build the left subtree using the left half of the current sublist
			node.setLeft(buildBalancedTree(values, start, mid - 1));
			
			// Recursively build the right subtree using the right half of the current sublist
			node.setRight(buildBalancedTree(values, mid + 1, end));

			// Return the node now connected to its subtrees
			return node;
		}
		
		// If the start index is greater than the end index, return null (base case)
		return null;
	}
	
	/**
	 * Adds a node with the specified value to the binary tree.
	 * If the tree is empty, the new node becomes the root.
	 * Otherwise, the value is added using a recursive approach by default.
	 * Uncomment the iterative approach to use it instead.
	 *
	 * @param value 		the value to be added to the tree
	 */
	public void add(State value)
	{
		// Check if the tree is currently empty
		if(root == null)
		{
			// Create a new node with the given value
			TreeNode<State> node = new TreeNode<State>(value);
	
			// Set this new node as the root of the tree
			root = node;
		}
		
		else
		{
			// Start the recursive addition process from the root
			addRecursive(value, root); // Recursive adding
			
			//~ addIterative(value); // Uncomment to use iterative adding instead
		}
	}
	
	/**
	 * Recursively adds a value to the binary tree. The method places the new value
	 * in the correct position based on the comparison result: values less than
	 * the current node go to the left, greater values go to the right.
	 *
	 * @param value 	the value to add to the tree
	 * @param node 		the current node being examined, starting from the root
	 */
	public void addRecursive(State value, TreeNode<State> node)
	{
		// Compare the new value with the value of the current node
		if (value.compareTo(node.getValue()) > 0)
		{
			// If new value is greater, explore the right subtree
			if (node.getRight() != null)
			{
				// If right child exists, recurse on the right child
				addRecursive(value, node.getRight());
			}
			
			else
			{
				// If no right child, insert new node here
				TreeNode<State> temp = new TreeNode<State>(value);
				node.setRight(temp);
			}
		}
		
		else
		{
			// If new value is less or equal, explore the left subtree
			if (node.getLeft() != null)
			{
				// If left child exists, recurse on the left child
				addRecursive(value, node.getLeft());
			}
			
			else
			{
				// If no left child, insert new node here
				TreeNode<State> temp = new TreeNode<State>(value);
				node.setLeft(temp);
			}
		}
	}
	
	/**
	 * Iteratively adds a value to the binary tree. The method finds the correct
	 * position for the new node by traversing the tree from the root, moving left
	 * or right depending on the comparison of the value with the current node.
	 * The process continues until the correct leaf position is found.
	 *
	 * @param value 	the value to add to the tree
	 */
	public void addIterative(State value)
	{
		// Create a new node for the value
		TreeNode<State> node = new TreeNode<State>(value);
		
		// Start at the root of the tree
		TreeNode<State> current = root;
		
		// Flag to indicate completion of the insertion process
		boolean hasFinished = false;
		
		while(!hasFinished)
		{
			// Compare the new value with the value of the current node
			if (value.compareTo(current.getValue()) > 0)
			{
				// If new value is greater, move to the right subtree
				if (current.getRight() != null)
				{
					// If right child exists, move to the right child
					current = current.getRight();
				}
				
				else
				{
					// If no right child, insert new node here
					current.setRight(node);
					hasFinished = true; // Mark insertion as complete
				}
			}
			
			else
			{
				// If new value is less or equal, move to the left subtree
				if (current.getLeft() != null)
				{
					// If left child exists, move to the left child
					current = current.getLeft();
				}
				
				else
				{
					// If no left child, insert new node here
					current.setLeft(node);
					hasFinished = true; // Mark insertion as complete
				}
			}
		}
	}
}

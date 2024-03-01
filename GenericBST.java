// Samuel Voor
// COP 3503, Spring 2023
// 5971335

// ====================
// GenericBST: BST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provided for you to modify as part of
// Programming Assignment #2.


import java.io.*;
import java.util.*;

// T is the name of the new Generic
// extends Comparable<T> allows access to methods of Comparable
// creating a node class to represent a node in the BST with generic type T
class Node<T extends Comparable<T>>
{	// replacing int with T, making Node generic
	T data;				
	Node<T> left, right;

	Node(T data)		
	{
		this.data = data;
	}
}

public class GenericBST<T extends Comparable<T>>
{	// root references other roots using generic type
	private Node<T> root;		
	// datatype of data is being changed to generic
	public void insert(T data)
	{	// calling a helper method which defines root
		root = insert(root, data);
	}
	
	// If there is a root, and the data in left is greater than right, then a node is inserted
	// If data on the right is greater then node is inserted on the right
	private Node<T> insert(Node<T> root, T data)
	{	// base case, no root
		if (root == null)				
		{	
			return new Node<T>(data);
		}	// using compareTo method as sunstitute for inequalities
		else if (data.compareTo(root.data) < 0)
		{										
			root.left = insert(root.left, data);
		}
		else if (data.compareTo(root.data) > 0) 
		{// go right if value is greater than the root amd left if it's less than
			root.right = insert(root.right, data);
		}

		return root;
	}
	// method deletes a node
	public void delete(T data)
	{
		root = delete(root, data);
	}
	// delete method for lines 69 - 80 work in a nearly identical manner as the insert method 
	// except that nodes are deleted instead of being inserted
	private Node<T> delete(Node<T> root, T data)
	{	// base case, no root, no node to delete
		if (root == null)
		{
			return null;					
		}
		else if (data.compareTo(root.data) < 0)
		{	// left if value less than root
			root.left = delete(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{	// right if value is greater than the root
			root.right = delete(root.right, data);
		}
		else
		{	// delete node if it is a leaf or only has one child
			// base case: return null if root has no children
			if (root.left == null && root.right == null)
			{
				return null;
			}	// return right child if no left and left if no right
			else if (root.left == null)
			{
				return root.right;
			}
			else if (root.right == null)
			{
				return root.left;
			}
			else
			{	// delete node with two children by replacing it with max node in left subtree
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// keeps track of current node as tree is traversed
	// returns root data of node as long as you dont hit the end of BST
	private T findMax(Node<T> root)
	{
		while (root.right != null)
		{
			root = root.right;	
		}

		return root.data;
	}
	// method returns whether or not data is contained within the root
	public boolean contains(T data)
	{
		return contains(root, data);
	}

	private boolean contains(Node<T> root, T data)
	{	// base case: no root, no data is contained
		if (root == null)
		{
			return false;
		}	// data less than current nodes data check left subtree
			// data greater, check right subtree
		else if (data.compareTo(root.data) < 0)
		{									   
			return contains(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);	
											    
		}
		else
		{	// if conditions are met then the data has been located return true
			return true;
		}
	}
	// prints "In-order Traversal"
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	private void inorder(Node<T> root)
	{	// base case, no nodes no need to sort BST
		if (root == null)
			return;
		// print out inorder traversal
		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}
	// prints out preorder traversal
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);	
		System.out.println();
	}
	// if root is null return nothing, if not print preorder traversal of all
	// left and right nodes
	private void preorder(Node<T> root)
	{
		if (root == null)
			return;
		// print current nodes data and traverse down the left and right subtree respectively
		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	// prints all values in left subtree then right subtree then root in postorder
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

 	// helper method for postorder traversal, it performs traversal starting at the root
  	// if current node is NULL it returns
  	// prints data of current child at the end
	private void postorder(Node<T> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	public static void main(String [] args)
	{	// instance of GenericBST class with Integer as the data type
		GenericBST<Integer> myTree = new GenericBST<>();
		// inserts 5 random nodes, using random() number method
		for (int i = 0; i < 5; i++)
		{
			int r = (int)(Math.random() * 150) + 1;
			System.out.println("Inserting " + r + "...");
			myTree.insert(r);
		}
		// calling inorder, preorder, and postorder traversal methods
		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
	}

	// wasnt as hard to figure out as the first one
	public static double difficultyRating()
	{
		return 3.0;
	}
	// took a lil while
	public static double hoursSpent()
	{
		return 10.0;
	}
}


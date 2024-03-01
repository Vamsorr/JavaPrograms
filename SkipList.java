
// Samuel Voor
// 3971335
// COP 3503 Spring 2023, 3pm Tues - Thurs
// Program #4 probabilistic data structures (Skip List)
// Dr. Szumlanski

import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;
  
class Node<T extends Comparable<T>>
{
    private T data;
    private int frequency;
    private ArrayList<Node<T>> next;
    
    Node(int height)
    {
        // Create an ArrayList to store the next nodes at each level
        this.next = new ArrayList<Node<T>>(height);

        // Initialize each next reference to null
        for (int i = 0; i < height; i++) 
        {
            this.next.add(null);
        }
    }
            
    Node(T data, int height)
    {
        this.data = data;
        this.frequency = 1;

        // create arraylist to store the next nodes at eachh level
        this.next = new ArrayList<Node<T>>(height);

        // initialize each next reference to null
        for (int i = 0; i < height; i++)
        {
            this.next.add(null);
        }
    }
    
    public T value()
    {
        // An O(1) method that returns the value stored at this node.
        return this.data;
    }

    public int height()
    {            
        // Return the size of the next ArrayList,
        // which corresponds to the height of this node
        return this.next.size();
    }

    public Node<T> next(int level)
    {
        // if level is outside the valid range of levels for the node return null
        if (level < 0 || level >= this.next.size())
        {
            return null;
        }
        // return the node at its given level
        return this.next.get(level);
    }
        
    public void setNext(int level, Node<T> node)
    {
        // Set the next reference at the given level within this node to node.
        if (level >= 0 && level < this.next.size())
        {
            this.next.set(level, node);
        }
    }

    public int frequency()
    {
        // Returns the frequency of this node.
        return this.frequency;
    }

    public void incrementFrequency()
    {
        // Increments the frequency of this node by 1.
        this.frequency++;
    }

    public void grow()
    {
        // adds null reference to end of list, growing the node by one level
        // node grows by one level, which adds an additional reference
        this.next.add(null);
    }

    public void maybeGrow()
    {
        // Math.random generates random number 0 - 0.999, theres roughly a 50% chance
        // that the random number will be < 0.5, if so then grow node by 1.
        if(Math.random() < 0.5)
        {
            this.grow();
        }
    }

    public void trim(int height)
    {
        // keep removing top reference until
        // its height is equal to its given height
        while (this.next.size() > height)
        {
            this.next.remove(this.next.size() - 1);
        }
    }
}
public class SkipList<T extends Comparable<T>>
{
    private Node<T> head;
    private int height;
    private T data;

    SkipList()
    {        
        // sets the height to 1 to account for head node
        this.height = 1;

        // creates head node with height of 1 and null data
        this.head = new Node<T>(null, 1);
    }

    SkipList(int height)
    {
        // ensures that the height is 1 or more, that way we dont
        // try and create an empty list
        if (height < 1)
            {
                this.height = 1;
            }
        else
            {
                this.height = height;
            }
        // specifies the height of the Node's tower of next references
        // node's tower of next references reaches the bottom layer of the SkipList
        // allowing for efficient searching and insertion.
        this.head = new Node<T>(null, this.height);
    }

    public int size()
    {
        // tracks size
        int count = 0;

        // start count from the first node after head
        Node<T> current = head.next(0);

        // traverse silly SkipList and track num nodes
        while (current != null)
        {
            count ++;
            current = current.next(0);
        }
        return count;
    }

    public int height()
    {
        // returns current height
        return head.height();
    }

    public Node<T> head()
    {
        // returns head of skip list
        return this.head;
    
    }

    public void insert(T data)
    {
        Node<T> currentNode = head;
        // creating an arraylist of height copies of null
        ArrayList<Node<T>> update = new ArrayList<>(Collections.nCopies(height, null));
    
        // initialize update array with null values
        for (int i = 0; i < height; i++)
        {
            update.add(head);
        }
    
        // traverse list and update the update array
        for (int i = height - 1; i >= 0; i--)
        {
            while (currentNode.next(i) != null && 
            currentNode.next(i).value().compareTo(data) < 0)
                {
                    currentNode = currentNode.next(i);
                }
            update.set(i, currentNode);
        }
    
        // if the value already exists in the skip list, insert before first occurence
        if (currentNode.next(0) != null && currentNode.next(0).value().equals(data))
        {
            currentNode.next(0).incrementFrequency();
        }
        // otherwise insert new node
        else
        {   // generates random height for new node
            // and creates new node with generated height
            int nodeHeight = generateRandomHeight();
            Node<T> newNode = new Node<>(data, nodeHeight);
    
            // if newnode height is taller than current height
            // grow skip list to match the height
            if (nodeHeight > height)
            {
                height = nodeHeight;
                growSkipList();
            }
    
            // set next references and previous nodes,
            // based on update array
            for(int i = 0; i < nodeHeight; i++)
            {
                newNode.setNext(i, update.get(i).next(i));
                update.get(i).setNext(i, newNode);
            }
        }
    }

    // helper method called upon by the insert method, which generates
    // a random height
    private int generateRandomHeight()
    {
        int nodeHeight = 1;
        while (Math.random() < 0.5 && nodeHeight < height + 1)
        {
            nodeHeight++;
        }
        return nodeHeight;
    }
    // grows skip list, adds new levels
    private void growSkipList()
    {   // get max height allowed for the skip list based on its current size
        int maxHeight = getMaxHeight(size() + 1);
        
        // if the height of the head nodes tower of references is already
        // greater than or equal to the max height allowed there is no need to grow skip list
        if (height >= maxHeight)
        {
            return;
        }

        // the head nodes tower of references is grown by
        // adding a new null reference to the end
        head.grow();
        // current node variable set to the node at height - 2 level in skip list
        // this ensures that if the skip list has a height of 1, it will be set to null
        Node<T> currentNode = head.next(head.height() - 2);

        // continue adding levels to skip list if maxHeight is not exceeded
        while (currentNode != null)
        {
            if (currentNode.height() >= maxHeight)
            {
                break;
            }
            // probability of adding a new level decreases exponentially as the level increases
            if (Math.random() < 0.5)
            {
                // 50% chance level is grown by adding a null reference
                currentNode.grow(); 
            } 
            else
            {   // 50% chance no more levels are added and loop breaks
                break;
            }
            // ensures all nodes at or below second to last level of skip list
            // can grow their height
            currentNode = currentNode.next(head.height() - 2);
        }
        // updates height of SkipList after growing it by one level, but cant exceed maxHeight
        height = Math.min(height + 1, maxHeight);
    }

    public void insert(T data, int height)
    {
        Node<T> currentNode = head;
        ArrayList<Node<T>> update = new ArrayList<>(height);
    
        // initialize update array with null values
        for (int i = 0; i < height; i++)
        {
            update.add(null);
        }
    
        // traverse list and update the update array
        for (int i = height - 1; i >= 0; i--)
        {
            while (currentNode.next(i) != null && 
            currentNode.next(i).value().compareTo(data) < 0)
            {
                currentNode = currentNode.next(i);
            }
            update.set(i, currentNode);
        }
    
        // insert new node with specified height
        Node<T> newNode = new Node<>(data, height);
    
        // grow the skip list if necessary
        if (height > this.height)
        {
            growSkipList();
            this.height = height;
        }
    
        // set the next references of the new node and the previous nodes
        for (int i = 0; i < height; i++)
        {
            newNode.setNext(i, update.get(i).next(i));
            update.get(i).setNext(i, newNode);
        }
    }

    public void delete(T data)
    {
        Node<T> currentNode = head;
        ArrayList<Node<T>> update = new ArrayList<>(height);

        // initialize update array with null values
        for (int i = 0; i < height; i++)
        {
            update.add(null);
        }

        // traverse list and update the update array
        for (int i = height - 1; i >= 0; i--)
        {
            while (currentNode.next(i) != null && 
            currentNode.next(i).value().compareTo(data) < 0)
            {
                currentNode = currentNode.next(i);
            }
            update.set(i, currentNode);
        }

        // if the value exists in the skip list, delete the first occurrence
        if (currentNode.next(0) != null && currentNode.next(0).value().equals(data))
        {
            Node<T> nodeToDelete = currentNode.next(0);

            // update next references of previous nodes to bypass node to be deleted
            for (int i = 0; i < nodeToDelete.height(); i++)
            {
                if (update.get(i) != null && update.get(i).next(i) == nodeToDelete)
                {
                    update.get(i).setNext(i, nodeToDelete.next(i));
                }
            }

            // call trimSkipList to update the height of the skip list
            trimSkipList();
        }
    }
    
    public boolean contains(T data)
    {
        Node<T> currentNode = head;
    
        // traverse list and update currentNode
        for (int i = height - 1; i >= 0; i--)
        {
            while (currentNode.next(i) != null && 
            currentNode.next(i).value().compareTo(data) < 0)
            {
                currentNode = currentNode.next(i);
            }
        }
    
        // return true if the value exists in the skip list
        if (currentNode.next(0) != null && currentNode.next(0).value().equals(data))
        {
            return true;
        }
    
        return false;
    }

    public Node<T> get(T data)
    {
        Node<T> currentNode = head;
    
        // traverse list and update currentNode
        for (int i = height - 1; i >= 0; i--)
        {
            while (currentNode.next(i) != null &&
            currentNode.next(i).value().compareTo(data) < 0)
            {
                currentNode = currentNode.next(i);
            }
        }
    
        // return the node that contains the given value, if it exists in the skip list
        if (currentNode.next(0) != null && currentNode.next(0).value().equals(data))
        {
            return currentNode.next(0);
        }
        // if value not found return null
        return null;
    }
    
    private static int getMaxHeight(int n)
    {
        // Returns the max height of a skip list with n nodes.
        return (int) Math.ceil(Math.log(n) / Math.log(2));
    }


    private void trimSkipList()
    {
        // Calculate the new height of the skip list based on the number of nodes
        int newSize = size();
        int newHeight = getMaxHeight(newSize);
    
        // Keep track of the minimum and maximum heights of all nodes in the skip list
        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;

        // start at first node after head node
        Node<T> currentNode = head.next(0);

        // traverse the skip list and update the minHeight and maxHeight variables
        while (currentNode != null)
        {
            int nodeHeight = currentNode.height();
            if (nodeHeight < minHeight)
            {
                minHeight = nodeHeight;
            }
            if (nodeHeight > maxHeight)
            {
                maxHeight = nodeHeight;
            }
            currentNode = currentNode.next(0);
        }
    
            // Trim only nodes whose height is greater than the maximum height
            currentNode = head.next(0);
            while (currentNode != null)
            {
                int nodeHeight = currentNode.height();
                if (nodeHeight > maxHeight)
                {
                    currentNode.trim(maxHeight);
                }
                currentNode = currentNode.next(0);
            }
    
            // Update the height of the skip list
            height = maxHeight;
    }
    

    public static double difficultyRating()
    {
        // took me a week to write this code and another week to debug it and pass
        // half the test cases, i spent even more days afterwards trying in vain but i
        // just couldnt figure it out and get it to pass all the test cases
        return 5.0;
    }
    
    public static double hoursSpent()
    {
        // spent more than a work week on this assignment, couldnt figure it out all the way
        // after a while all the extra hours just didnt even help
        return 45;
    }
}
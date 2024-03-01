public class TwoFourTree
{
        private class TwoFourTreeItem
        {
            int values = 1;                         // 1. always exists. values is the numer of keys in a node
            int value1 = 0;                        // 2. exists iff the node is a 3 node 
            int value2 = 0;                         //    or 4 node
            int value3 = 0;                          // 3. exists iff the node is a 4 node
                                                
            boolean isLeaf = true;

            TwoFourTreeItem parent = null;          // 1. parent exists iff the node is not a root
            TwoFourTreeItem leftChild = null;       // 2. left and right child exists iff the node
            TwoFourTreeItem rightChild = null;      //    is non leaf
            TwoFourTreeItem centerChild = null;     // 3. center child exists iff the node is a 
            TwoFourTreeItem centerRightChild = null;//    non-leaf 3 node
            TwoFourTreeItem centerLeftChild = null; // 4. center left and center right children exist
                                                    //    iff the node is a non-leaf 4-node
                                                
              // Is a two node if there is one key and and has 2 children
              public boolean isTwoNode()
              {
                if (values == 1 && leftChild != null && rightChild != null)
                  return true;
                
                else
                  return false;
              }

              // true if there are 2 keys and three children
              public boolean isThreeNode()
              {
                if (values == 2 && leftChild != null && rightChild != null)
                  return true;
                
                else
                  return false;
              }

              // true if there are 3 keys and 4 children
              public boolean isFourNode()
              {
                if (values == 3 && leftChild != null && rightChild != null
                 && centerLeftChild != null && centerRightChild != null)
                  return true;

                else
                  return false;
              }

              // true if parent is null, has 1, 2 or 3 keys, and has the
              // appropriate number of children
              public boolean isRoot()
              { 

                if (parent == null && isTwoNode())
                  return true;

                else if (parent == null && isThreeNode())
                  return true;

                else if (parent == null && isFourNode())
                  return true;

                else
                  return false;
              }

              // initialize object with a single value
              public TwoFourTreeItem(int value1)
              {
                this.value1 = value1;
                this.values = 1;
              }

              public TwoFourTreeItem(int value1, int value2)
              {
                this.value1 = value1;
                this.value2 = value2;
                this.values = 2;
              }

              public TwoFourTreeItem(int value1, int value2, int value3)
              {
                this.value1 = value1;
                this.value2 = value2;
                this.value3 = value3;
                this.values = 3;
              }

              private void printIndents(int indent)
              {
                for (int i = 0; i < indent; i++)
                   System.out.println("  "); 
              }

              public void printInOrder(int indent)
              {
                if (!isLeaf) leftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value1);
                if (isThreeNode())
                {
                  if (!isLeaf)
                    centerChild.printInOrder(indent + 1);
                    printIndents(indent);
                    System.out.printf("%d\n", value2);
                }

                else if (isFourNode())
                {
                  if (!isLeaf)
                    centerLeftChild.printInOrder( + 1);
                  
                  printIndents(indent);
                  System.out.printf("%d\n", value3);
                }
                if(!isLeaf)
                  rightChild.printInOrder( + 1);
              }
        }

  TwoFourTreeItem root = null;

  // takes in an integer value and inserts it into tree
  public boolean addValue(int value) 
  {
    TwoFourTreeItem node = insertValue(root, value);
    if (node != null) 
    {
        root = node;
        return true;
    }

    else 
        return false;
}

private TwoFourTreeItem insertValue(TwoFourTreeItem node, int value) 
{
    // if tree is empty, insert root
    if (root == null) 
    {
        return new TwoFourTreeItem(value);
    }
    // If not a leaf node, traverse down a correct path
    else if (!node.isLeaf) 
    {
        if (value < node.value1) 
        {
            node.leftChild = insertValue(node.leftChild, value);
        } else if (node.isTwoNode() || (node.isThreeNode() && value < node.value2)) 
        {
            node.centerChild = insertValue(node.centerChild, value);
        } else if (node.isThreeNode()) 
        {
            node.rightChild = insertValue(node.rightChild, value);
        } else if (node.isFourNode()) 
        {
            if (value < node.value2) 
            {
                node.centerLeftChild = insertValue(node.centerLeftChild, value);
            } else if (value < node.value3) 
            {
                node.centerRightChild = insertValue(node.centerRightChild, value);
            } else 
            {
                node.rightChild = insertValue(node.rightChild, value);
            }
        }
        return node;
    }
    // insert only at leaf nodes
    else if (node.isLeaf) 
    {
        // split four node before inserting
        if (node.isFourNode()) 
        {
            // Assuming you have a splitNode function that handles node splitting and returns the new node after split.
            node = splitNode(node);
            return insertValue(root, value); // Using root here as we might have a new root after splitting.
        }
        // Node is leaf node and 2 node, insert values in proper spot and update their key count
        else if (node.isTwoNode()) 
        {
            if (value < node.value1) 
            {
                int temp = node.value1;
                node.value1 = value;
                node.value2 = temp;
            } else 
            {
                node.value2 = value;
            }
            node.values++;
        }
        // Node is 3 node and leaf, insert values in proper spot and update the count of the node
        else if (node.isThreeNode()) 
        {
            if (value < node.value1) 
            {
                int temp1 = node.value1;
                int temp2 = node.value2;
                node.value1 = value;
                node.value2 = temp1;
                node.value3 = temp2;
            } else if (value > node.value1 && value < node.value2) 
            {
                int temp = node.value2;
                node.value2 = value;
                node.value3 = temp;
            } else 
            {
                node.value3 = value;
            }
            node.values++;
        }
        return node;
    }
    return null; // return null if no node was inserted.
}

  

  // takes in four node to split, and returns its parent
  private TwoFourTreeItem splitNode(TwoFourTreeItem node)
  {

    // if non TwoFourTreeItem is passed:
    if (node == null)
      throw new IllegalArgumentException("pass in non-null por favor");

    if (node.isFourNode())

    {
      // if there is only the root in the tree
      if (node.isRoot())
      {
        // create new parent/root from split
        TwoFourTreeItem newRoot = new TwoFourTreeItem(node.value2);
        newRoot.values = 1;

        // creating newLeft child if the nodes left child isn't already null
        TwoFourTreeItem newLeft;
        if (node.leftChild != null)
        {
          newLeft = node.leftChild;
          newLeft.value1 = node.value1;
          newLeft.values = 1;
        }

        else
        {  
          newLeft = new TwoFourTreeItem(node.value1);
          newLeft.values = 1;
        }
        TwoFourTreeItem newRight;
        if (node.rightChild != null)
        {
          newRight = node.rightChild;
          newRight.value3 = node.value3;
          newRight.values = 1;
        }

        else
        { 
          newRight = new TwoFourTreeItem(node.value3);
          newRight.values = 1;
        }

        // adjusting parent pointers
        updateParent(newRoot, newLeft, newRight, node);

        // adjusting child pointers
        adjustChildPointers(newRoot, newLeft, newRight, node);
      
        
        // delete old node
        deleteValue(node.value1);
        deleteValue(node.value2);
        deleteValue(node.value3);
        deleteValue(node.values);


        return newRoot;
      }

      // if not root
      else
      {
        // creates new parent from split
        TwoFourTreeItem newParent = new TwoFourTreeItem(node.value2);

        // create new left and right children from split
        TwoFourTreeItem newLeft;
        if (node.leftChild != null)
        {
          newLeft = node.leftChild;
          newLeft.value1 = node.value1;
          newLeft.values = 1;
        }

        else 
          newLeft = new TwoFourTreeItem(node.value1);
          newLeft.values = 1;


        TwoFourTreeItem newRight;
        if (node.rightChild != null)
        {
          newRight = node.rightChild;
          newRight.value1 = node.value3;
          newRight.values = 1;
        }

        else 
          newRight = new TwoFourTreeItem(node.value3);
          


        // adjust parent pointers for the newly created nodes
        updateParent(newParent, newLeft, newRight, node);

        // adjust child pointers resulting from split
        adjustChildPointers(newParent, newLeft, newRight, node);

        // link new parent to old parent
        newParent.parent = node.parent;

        // update old parent's child links
        if (node.parent.leftChild == node)
          node.parent.leftChild = newParent;

        else if (node.parent.centerChild == node)
          node.parent.centerChild = newParent;

        else if (node.parent.rightChild == node)
          node.parent.rightChild = newParent;
      
        // delete old node
        deleteValue(node.value1);
        deleteValue(node.value2);
        deleteValue(node.value3);
        deleteValue(node.values);


        // if parent is a four node split it!
        if (newParent.parent.isFourNode())
          splitNode(newParent.parent);

        return newParent;
      }

    } 
      return null;
  }

  // updates a nodes parent pointers. takes in newParent, the old node and the left/right children resulting from the split
  private void updateParent(TwoFourTreeItem newParent, TwoFourTreeItem leftChild,
   TwoFourTreeItem rightChild, TwoFourTreeItem oldNode)
  {
    // Link new parent to old parent
    newParent.parent = oldNode.parent;

    // link children to new parent
    leftChild.parent = newParent;
    rightChild.parent = newParent;

    // if old node had a parent update its child pointers
    if (oldNode.parent != null)
    {
      if (oldNode.parent.leftChild == oldNode)
        oldNode.parent.leftChild = newParent;

      else if (oldNode.parent.rightChild == oldNode)
        oldNode.parent.rightChild = newParent;

      else if (oldNode.parent.centerChild == oldNode)
        oldNode.parent.centerChild = newParent;

      else if (oldNode.parent.centerRightChild == oldNode)
        oldNode.parent.centerRightChild = newParent;

      else if (oldNode.parent.centerLeftChild == oldNode)
        oldNode.parent.centerLeftChild = newParent;
    }

    // if oldNode did not have a parent then it's obvie the heckin root
    else
      root = newParent;
  }

  // updates a nodes children pointers. takes in newParent, the old node and the left/right children resulting from the split
  private void adjustChildPointers(TwoFourTreeItem newParent, TwoFourTreeItem leftChild, TwoFourTreeItem rightChild, TwoFourTreeItem oldNode)
  {
    // Assign new leftChild nodes, as a result of the split
    newParent.leftChild = leftChild;
    newParent.rightChild = rightChild;

    // if the node being split had children distribute them to new children
    if (oldNode.leftChild != null && oldNode.rightChild != null)
    {
      leftChild.leftChild = oldNode.leftChild;
      leftChild.rightChild = oldNode.centerLeftChild;
      rightChild.leftChild = oldNode.centerRightChild;
      rightChild.rightChild = oldNode.rightChild;

      // update parent pointers of these children
      oldNode.leftChild.parent = leftChild;
      oldNode.centerLeftChild.parent = leftChild;
      oldNode.centerRightChild.parent = rightChild;
      oldNode.rightChild.parent = rightChild;
    }
  }





  // public facing search method, is true if tree hasValue
  public boolean hasValue(int value)
  {
    return search(root, value);
  }

  // searches tree for a value
  private boolean search(TwoFourTreeItem node, int value)
  {
    if (node == null)
      return false;

    // searching a two node
    else if (node.isTwoNode())
    {
      if (node.value1 == value)
        return true;

      else if (node.value1 > value)
        return search(node.leftChild, value);

      else
        return search(node.rightChild, value); 
    }

    // searching a three node
    else if (node.isThreeNode())
    {
      if (node.value1 == value || node.value2 == value)
        return true;

      else if (value < node.value1)
        return search(node.leftChild, value);

      else if (value > node.value1 && value < node.value2)
        return search(node.centerChild, value);

      else
        return search(node.rightChild, value);
    }

    // searching a four node
    else if (node.isFourNode())
    {
      if (value == node.value1 || value == node.value2 || value == node.value3)
        return true;

      else if (value < node.value1)
        return search(node.leftChild, value);

      else if (value > node.value1 && value < node.value2)
        return search(node.centerLeftChild, value);

      else if (value > node.value2 && value < node.value3)
        return search(node.centerRightChild, value);

      else
        return search(node.rightChild, value);
    }

    // if node is a leaf and != to value then return false
    else if(node.isLeaf)
    {
      if (value == node.value1)
        return true;

      else if (value == node.value2)
        return true;

      else
        return false;
    }

    else
      return false;
  }

  // method is used to delete a value
  // takes a value to delete as input, and returns a boolean indicating whether it was a failure or not
  public boolean deleteValue(int value) 
  {
    // if root is null, tree is empty return false
    if (root == null) 
        return false;
    
    // call delete helper on root node with given value
    delete(root, value);

    // if root node ends up with no values and its not a leaf node
    // replace the root with its left child
    if (root != null && root.values == 0) 
        root = root.isLeaf ? null : root.leftChild;

    // deletion was successful
    return true;
}

// this method deletes a value from a node and its descendants in a 2-4 tree and
// it takes a node and an integer value to delete as input
private void delete(TwoFourTreeItem node, int value) 
{
    // if the node is leaf remove the value from it
    if (node.isLeaf) 
    {
        for (int i = 0; i < node.values; i++) 
        {
            if (node.value1 == value) 
            {
                removeValue(node, i);
                return;
            }
        }
        return;
    }
    
    // if node is not leaf, find the child that may contain the value.
    int childIndex = 0;
    while (childIndex < node.values) 
    {
        if (value < getValue(node, childIndex)) 
        {
            break;
        }
        childIndex++;
    }

    // if value is in the node, replace it with the largest value in the left subtree and delete that value from the subtree
    // otherwise delete the value from the proper child
    if (childIndex != node.values) 
    {
        TwoFourTreeItem predNode = getChild(node, childIndex);
        int predValue = findMaxValue(predNode);
        setValue(node, childIndex, predValue);
        delete(predNode, predValue);

    } 
    
    else 
        delete(getChild(node, childIndex), value);

    // if the child ends up with no values fix it

    if (getChild(node, childIndex).values < 1) 
    {
        // if the child has a left sibling with at least two values move one value from the sibling to the child
        if (childIndex > 0 && getChild(node, childIndex - 1).values > 1) 
        {
            shiftRight(node, childIndex);
        }

        // otherwise if child has a right sibling with at least two values, move one value from the sibling to the child.
        else if (childIndex != node.values && getChild(node, childIndex + 1).values > 1) 
            shiftLeft(node, childIndex);

        // otherwise merge the child with either its left or right sibling
        else if (childIndex != node.values) 
            merge(node, childIndex); 
        
        else 
            merge(node, childIndex - 1);
    }
}

// this method retrieves the value at a specific index in a node
// and takes a node and an index as input, and returns the value at that index.
private int getValue(TwoFourTreeItem node, int index) 
{
    switch (index) 
    {
        case 0: return node.value1;
        case 1: return node.value2;
        case 2: return node.value3;
    }

    throw new RuntimeException("Invalid value index");
}

//  method sets the value at a specific index in a node
// and then takes a node, an index and a value as input
private void setValue(TwoFourTreeItem node, int index, int value) 
{
    switch (index) 
    {
        case 0: node.value1 = value; break;
        case 1: node.value2 = value; break;
        case 2: node.value3 = value; break;

        default: throw new RuntimeException("Invalid value index");
    }
}

// method retrieves the child at a specific index in a node
// and takes a node and a index as input then returns the child node at that index
private TwoFourTreeItem getChild(TwoFourTreeItem node, int index) 
{
    switch (index) 
    {
        case 0: return node.leftChild;
        case 1: return node.centerChild;
        case 2: return node.rightChild;
    }

    throw new RuntimeException("Invalid child index");
}

// method removes the value at a specific index in a node
// and takes a node and an index as input
private void removeValue(TwoFourTreeItem node, int index) 
{
    // Shift all values after the index to the left, overwriting the value at the index.
    for (int i = index + 1; i < node.values; i++) 
        setValue(node, i - 1, getValue(node, i));

    // decrement number of keys in node
    node.values--;
}

// method finds the maximum value in a node and its descendants
// and takes a node as input, and returns the maximum value
private int findMaxValue(TwoFourTreeItem node) 
{
    //  maximum value is the rightmost value in the rightmost leaf, so keep going right until a leaf is reached
    while (!node.isLeaf) 
    {
        node = getChild(node, node.values);
    }
    return getValue(node, node.values - 1);
}

// method moves a value from a nodes left child to the node, and a value from the node to its right child
// also takes a node and an index as input
private void shiftRight(TwoFourTreeItem node, int index) 
{
    TwoFourTreeItem child = getChild(node, index);
    TwoFourTreeItem leftChild = getChild(node, index - 1);
    child.values++;

    // shift all values in the right child to the right
    for (int i = child.values - 1; i > 0; i--)
        setValue(child, i, getValue(child, i - 1));
    
    // move value from the parent to the right child, and a value from the left child to the parent
    setValue(child, 0, getValue(node, index - 1));
    setValue(node, index - 1, getValue(leftChild, leftChild.values - 1));
    leftChild.values--;
}

//  method moves a value from a nodes right child to the node, and a value from the node to its left child
// takes a node and an index as input
private void shiftLeft(TwoFourTreeItem node, int index) 
{
    TwoFourTreeItem child = getChild(node, index);
    TwoFourTreeItem rightChild = getChild(node, index + 1);

    // move value from the parent to the left child, and a value from the right child to the parent
    setValue(child, child.values, getValue(node, index));
    setValue(node, index, getValue(rightChild, 0));
    child.values++;

    // shift all values in the right child to the left.
    for (int i = 0; i < rightChild.values - 1; i++) 
        setValue(rightChild, i, getValue(rightChild, i + 1));

    rightChild.values--;
}

  // method merges a node with its right sibling, moving a value from the parent to the new merged node
  // and takes a node and an index as input
  private void merge(TwoFourTreeItem node, int index) 
  {
    TwoFourTreeItem child = getChild(node, index);
    TwoFourTreeItem rightChild = getChild(node, index + 1);
    
    // move a value from the parent to the left child
    setValue(child, child.values, getValue(node, index));
    
    // move all values from the right child to the left child.
    for (int i = 0; i < rightChild.values; i++) 
        setValue(child, child.values + 1 + i, getValue(rightChild, i));
    
    
    child.values += 1 + rightChild.values;
    
    // if the nodes are not leaves then move the children from the right child to the left child.
    if (!child.isLeaf) 
    {
        switch (index) 
        {
            case 0:
                child.centerChild = rightChild.leftChild;
                node.centerChild = node.rightChild;
                node.rightChild = null;
                break;

            case 1:
                child.rightChild = rightChild.leftChild;
                node.rightChild = null;
                break;

            default:
                throw new RuntimeException("Invalid index for merge operation");
        }
    }
    
    // Remove a value from the parent.
    for (int i = index + 1; i < node.values; i++) 
        setValue(node, i - 1, getValue(node, i));
    
    node.values--;
  }


  public void printInOrder()
  {
    if(root != null) root.printInOrder(0);
  }

  public TwoFourTree()
  {
    root = null;
  }

}
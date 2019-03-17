/*
Illia Shershun
CS202 Spring

2-3 Tree class. Each node in the tree contains 2 or 3 eLists organized by the events keywords. That is - each list contains certain number of events that share a common keyword by which even can be
searched. Each node might have 2 or 3 pointers to other nodes. Tree is organized by the eLists keywords.
*/

public class Tree
{
    private tNode Root;

    /*
    Default constructor
    */
    Tree()
    {
        Root = null;
    }

    /*
    Function to insert new Event with a certain keyword into the tree
    */
    public void insert(String newKeyword, Event newEvent)
    {
        //Case 1: Tree is empty
        //->Create new tNode with newEvent and newKeyword (will create new eList in the node). Make Root "point" to the new tNode
        if(Root == null)
        {
            Root = new tNode(newEvent, newKeyword);
            return;
        }

        //Case 2: Tree already contains eList with newKeyword.
        //->Simply insert newEvent into already existing list
        eList tempList = findList(Root, newKeyword);
        if(tempList != null)
        {
            tempList.addEvent(newEvent);
            return;
        }

        //Case 3: Tree doesn't have an eList with newKeyword.
        //->Find appropriate tNode for the keyword (leaf)
        //->Insert newEvent with newKeyword into the node (it will create new eList in the node, with a list keyword = newKeyword)
        //->If insertion caused tNode to become 3-node -> call splitNode function on the leaf.
        //->This will cause recursive splitting (if needed) on the way back to re-balance the tree
        tNode leaf = getLeaf(Root, newKeyword);
        leaf.insert(newKeyword, newEvent);
        if(leaf.dataCount() == 3)
            splitNode(leaf);
    }

    /*
    Helper (recursive) function that returns eList from the Tree that identified by the wanted keyword
    */
    private eList findList(tNode root, String keyword)
    {
        //Case 1: Traversed all the way and didn't find appropriate list.
        //->Return null, signaling that list "wanted" list doesn't exist
        if(root == null)
            return null;

        //Case 2: Traversed to the leaf
        if(root.isLeaf())
        {
            //Case 2a: tNode's smallData keyword = wanted keyword.
            if(keyword.compareTo(root.getSmallKeyword()) == 0)
            {
                return root.getSmall();
            }
            //Case 2b: tNode's largeData keyword = wanted keyword
            else if(root.dataCount() == 2 && (keyword.compareTo(root.getLargeKeyword())) == 0)
            {
                return root.getLarge();
            }
            //Case 2c: tNode doesn't contain eList with a wanted keyword
            else
                return null;
        }

        //Case 3a: Wanted keyword is smaller than smallData's keyword
        if(keyword.compareTo(root.getSmallKeyword()) < -1)
            return findList(root.getLeft(), keyword);
        //Case 3b: Wanted keyword = smallData keyword
        if(keyword.compareTo(root.getSmallKeyword()) == 0)
            return root.getSmall();
        //Case 3c: tNode only contains smallData OR wanted keyword is smaller than largeData's keyword
        if(root.dataCount() == 1 || keyword.compareTo(root.getLargeKeyword()) < -1)
            return findList(root.getMiddle(), keyword);
        //Case 3d: Wanted keyword = largeData keyword
        if(keyword.compareTo(root.getLargeKeyword()) == 0)
            return root.getLarge();
        //Case 3e: Wanted keyword is larger than largeData's keyword
        return findList(root.getRight(), keyword);
    }

    /*
    Public findList function to find list with events that correspond with user search
    */
    public void findList(String keyword)
    {
        eList temp = findList(Root, keyword);
        if(temp == null)
            System.out.println("Error! Events with [" + keyword + "] could not be found.");
        else
            temp.displayWithKeyword();
    }

    /*
    Helper (recursive) function that finds appropriate leaf in the tree into which Event with a certain
    keyword will be inserted
    */
    private tNode getLeaf(tNode root, String keyword)
    {
        //Case 1: Current tNode is either null or a leaf
        if(root == null || root.isLeaf())
            return root;

        //Case 2a: Searched keyword is smaller than smallData's keyword
        if(keyword.compareTo(root.getSmallKeyword()) < -1)
            return getLeaf(root.getLeft(), keyword);
        //Case 2b: Searched keyword is smaller than largeData's keyword OR largeData doesn't exist
        if(root.dataCount() == 1 || keyword.compareTo(root.getLargeKeyword()) < -1)
            return getLeaf(root.getMiddle(), keyword);
        //Case 2c: Searched keyword is larger than largeData's keyword
        return getLeaf(root.getRight(), keyword);
    }

    /*
    Helper (recursive) function to split the tNode's on the way "up" after insertion
    */
    private void splitNode(tNode root)
    {
        //Define new tNode toSplit
        tNode toSplit;

        //Case 1: Root need to be split
        //->Create new tNode and make Root and toSplit point to it
        if(root == Root)
        {
            Root = toSplit = new tNode();
        }
        //Case 2: Some other tNode needs to be split
        //->make toSplit point to the parent of the tNode about to be split
        else
        {
            toSplit = root.getParent();
        }

        //tNode that needs to be split calls splitNode function with toSplit as an argument (tNode into which middle value will be "pushed up")
        root.splitNode(toSplit);
        //After splitting we "push" middle value up into toSplit
        toSplit.insert(root.getLarge());
        //If, after insertion, toSplit contains 3 datas -> call splitNode again, on toSplit (recursive step)
        if(toSplit.dataCount() == 3)
            splitNode(toSplit);
    }

    /*
    Wrapper function to display the tree
    */
    public void display()
    {
        if(Root == null)
            System.out.println("Tree is empty! Input first.");
        else {
            System.out.println("Number of nodes: " + nodeCount(Root) + "\n");
            displayInorder(Root);
        }
    }

    /*
    Helper (recursive) function that displays tree in order
    */
    private void displayInorder(tNode root)
    {
        //Traversed till the leaf: display leaf's eLists and return
        if(root.isLeaf())
        {
            if(root == Root)
                System.out.print("\n[Root]-> ");
            root.displayInorder();
            return;
        }

        //First, traverse left sub-tree
        displayInorder(root.getLeft());
        //At this point we arrived back to the Root, so this is a little helper display
        System.out.print("\n[Root]-> ");
        root.getSmall().displayWithKeyword();
        //Second, traverse middle sub-tree
        displayInorder(root.getMiddle());
        //If this is a 2 data-node, we have 3 pointers: traverse right sub-tree
        if(root.dataCount() == 2)
        {
            System.out.print("\n[Root]-> ");
            root.getLarge().displayWithKeyword();
            displayInorder(root.getRight());
        }
    }

    /*
    Helper (recursive) function that returns number of tNodes in the tree
    */
    private int nodeCount(tNode root)
    {
        int count = 1;
        if(root == null)
            return 0;
        else
        {
            count = count + nodeCount(root.getLeft());
            count = count + nodeCount(root.getMiddle());
            count = count + nodeCount(root.getRight());
            return count;
        }
    }

    /*
    Function that "deletes" the tree
    */
    public void removeTree()
    {
        Root = null;
    }
}

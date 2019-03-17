/*
Illia Shershun
CS202 Spring

Node for the 2-3 Tree: in ideal case stores information about 1 or 2 data members and 2 or 3 "pointers"
to the other nodes, as well as reference to the parent node. In special cases there is need in the temporary
middle data and fourth "pointer". If such case will happen, node will we split, but those fields are neccessary
to input data in.
In our case each node stores 3 eLists, and 5 "pointers".
*/

import java.util.ArrayList;
import java.util.Collections;

public class tNode
{
    private eList smallData;
    private eList largeData;
    private eList tempData;
    private tNode left;
    private tNode middle;
    private tNode right;
    private tNode temp;
    private tNode parent;

    /*
    Default tNode constructor:
    1)Sets all of the lists to null
    2)Sets all the pointers to null
    */
    tNode()
    {
        smallData = largeData = tempData = null;
        left = middle = right = temp = parent = null;
    }

    /*
    tNode constructor with 2 arguments: Event and String:
    1)Create smallData (previously just defined) and add passed Event into it. Also, set
    lists keyword to the passed String.
    2)Set 2 other lists to null
    3)Sets all pointers to null
    */
    tNode(Event newEvent, String keyword)
    {
        smallData = new eList();
        smallData.addEvent(newEvent);
        smallData.setKeyword(keyword);
        largeData = tempData = null;
        left = middle = right = temp = parent = null;
    }

    /*
    tNode constructor which takes eList as an argument
    1)Create smallData (previously just defined) from passed eList. That is - call eList copy constructor
    2)Sets 2 other lists to null
    3)Sets all pointers to null
    */
    tNode(eList newList)
    {
        smallData = new eList(newList);
        largeData = tempData = null;
        left = middle = right = temp = parent = null;
    }

    /*
    tNode constructor which takes 2 eLists as arguments
    1)Creates smallData and largeData (previously just defined) from passed lists: calls copy constructors
    2)Sets tempList to null
    3)Sets all pointers to null
    */
    tNode(eList list1, eList list2)
    {
        smallData = new eList(list1);
        largeData = new eList(list2);
        tempData = null;
        left = middle = right = temp = parent = null;
    }

    /*
    Function that returns smallData (eList stored as a smallData) - list with the "smallest" keyword
    */
    public eList getSmall()
    {
        return smallData;
    }

    /*
    Function that returns keyword associated with the smallData (eList stores as a smallData).
    */
    public String getSmallKeyword()
    {
        return smallData.getKeyword();
    }

    /*
    Function that returns largeData (eList stored as a largeData) - list with the "largest" keyword
    */
    public eList getLarge()
    {
        return largeData;
    }

    /*
    Function that returns keyword associated with the largeData (eList stores as a largeData)
    */
    public String getLargeKeyword()
    {
        return largeData.getKeyword();
    }

    /*
    Function that returns tNode "to the left" of this tNode
    */
    public tNode getLeft()
    {
        return this.left;
    }

    /*
    Function that sets this left pointer to the passed tNode
    */
    public void setLeft(tNode newLeft)
    {
        this.left = newLeft;
    }

    /*
    Function that returns tNode "to the right" of this tNode
    */
    public tNode getRight()
    {
        return this.right;
    }

    /*
    Function that sets this right pointer to the passed tNode
    */
    public void setRight(tNode newRight)
    {
        this.right = newRight;
    }

    /*
    Function that returns tNode "in the middle" of this tNode
    */
    public tNode getMiddle()
    {
        return this.middle;
    }

    /*
    Function that sets this middle pointer to the passed tNode
    */
    public void setMiddle(tNode newMiddle)
    {
        this.middle = newMiddle;
    }

    /*
    Function that returns temporary fourth pointer of this tNode
    */
    public tNode getTempChild()
    {
        return this.temp;
    }

    /*
    Function that sets temporary pointer to the passed tNode
    */
    public void setTempChild(tNode newTemp)
    {
        this.temp = newTemp;
    }

    /*
    Function that returns parent tNode of this
    */
    public tNode getParent()
    {
        return this.parent;
    }

    /*
    Function that sets parent of this tNode
    */
    public void setParent(tNode newParent)
    {
        this.parent = newParent;
    }

    /*
    Boolean function that checks if this node is a leaf: this node has no left, middle, or right pointers.
    Returns true of this tNode is a leaf. Returns false otherwise.
    */
    public boolean isLeaf()
    {
        return (left == null && right == null && middle == null);
    }

    /*
    Returns number of datas in the node:
    */
    public int dataCount()
    {
        if(smallData == null)
            return 0;
        if(largeData == null)
            return 1;
        if(tempData == null)
            return 2;
        return 3;
    }

    /*
    Function to insert new Event with a keyword into the node
    */
    public void insert(String newKeyword, Event newEvent)
    {
        //Case 1: Node is empty, smallData == null:
        //->Create smallData and insert new Event into it. Set smallData keyword
        if(smallData == null)
        {
            smallData = new eList();
            smallData.addEvent(newEvent);
            smallData.setKeyword(newKeyword);
        }
        //Case 2: Node contains one item (smallData)
        else if(largeData == null)
        {
            //Case 2a: Event to be inserted has "larger" keyword than smallData's -> insert into largeData
            //->Create largeData and insert newEvent into it. Set largeData keyword
            if(newKeyword.compareTo(smallData.getKeyword()) > 0)
            {
                largeData = new eList();
                largeData.addEvent(newEvent);
                largeData.setKeyword(newKeyword);
            }
            //Case 2b: Event to be inserted has a "smaller" keyword than smallData's -> insert into smallData:
            //swap smallData info into largeData and insert into smallData
            //->Create largeData with the smallData (call copyConstructor with smallData)
            //->Create smallData and insert newEvent into it. Set smallData keyword
            else
            {
                largeData = new eList(smallData);
                largeData.setKeyword(smallData.getKeyword());
                smallData = new eList();
                smallData.addEvent(newEvent);
                smallData.setKeyword(newKeyword);
            }
        }
        //Case 4: Node contains both smallData and largeData. We will insert into tempData
        //Then, we will sort 3 lists by their keywords, and "re-create" small, large, and temp data's in correct order
        else
        {
            //Create array list and add keywords into it
            ArrayList<String> tempArray = new ArrayList<>();
            tempArray.add(smallData.getKeyword());
            tempArray.add(largeData.getKeyword());
            tempArray.add(newKeyword);

            //Create temp datas, which will be used not to lose original
            //lists when "re-creating".
            eList tempSmall = smallData;
            eList tempLarge = largeData;
            eList tempMid = new eList();
            tempMid.addEvent(newEvent);
            tempMid.setKeyword(newKeyword);

            //Sort array list alphabetically.
            Collections.sort(tempArray);

            //Create new small, large, and temp datas with correct lists (Lists with "smallest" to "largest" keywords)
            smallData = new eList(pickCorrect(tempArray.get(0), tempSmall, tempLarge, tempMid));
            largeData = new eList(pickCorrect(tempArray.get(1), tempSmall, tempLarge, tempMid));
            tempData = new eList(pickCorrect(tempArray.get(2), tempSmall, tempLarge, tempMid));
        }
    }

    /*
    Helper function that returns the correct eList by matching its keyword with a wanted keyword (target)
    */
    private eList pickCorrect(String target, eList tS, eList tL, eList tM)
    {
        if(tS.getKeyword().compareTo(target) == 0)
            return tS;
        else if(tL.getKeyword().compareTo(target) == 0)
            return tL;
        else
            return tM;
    }

    /*
    Function that splits the node (by pushing its "middle" aka large value into its parent: in this case -> toSplit).
    Fnction is called when tNode contains 3 datas: small, large, and temp (tNode can contain 2 datas at max)
    */
    public void splitNode(tNode toSplit)
    {
        //Create new tNodes with leftMost data(smallData) and rightMost data(tempData). Middle (largeData) will be "pushed up"
        tNode newLeft = new tNode(smallData);
        tNode newRight = new tNode(tempData);
        //Set new tNode's parent to be the toSplit
        newLeft.setParent(toSplit);
        newRight.setParent(toSplit);

        //Case 1: tNode which called the function is not a leaf AND this is the case where there are 4 pointers!
        if(!this.isLeaf())
        {
            newLeft.setLeft(this.getLeft());
            newLeft.setMiddle(this.getMiddle());
            newRight.setLeft(this.getRight());
            newRight.setMiddle(this.getTempChild());
        }

        //Case 2: tNode which called the function is to the left of the toSplit (its parent)
        if(this == toSplit.getLeft())
        {
            //Case 2a: toSplit (parent) only contains smallData [Therefore only contains 2 pointers - usually left and middle].
            if(toSplit.dataCount() == 1)
            {
                toSplit.setRight(toSplit.getMiddle());
                toSplit.setMiddle(newRight);
                toSplit.setLeft(newLeft);
            }
            //Case 2b: toSplit (parent) contains both smallData and largeData
            //This step will make toSplit have 4 pointers.
            else if(toSplit.dataCount() == 2)
            {
                toSplit.setTempChild(toSplit.getRight());
                toSplit.setRight(toSplit.getMiddle());
                toSplit.setMiddle(newRight);
                toSplit.setLeft(newLeft);
            }
        }
        //Case 3: tNode which called the function is in the middle of the toSplit (its parent)
        else if(this == toSplit.getMiddle())
        {
            //Case 3a: toSplit (parent) only contains smallData [Therefore only contains 2 pointers - usually left and middle].
            if(toSplit.dataCount() == 1)
            {
                toSplit.setRight(newRight);
                toSplit.setMiddle(newLeft);
            }
            //Case 3b: toSplit (parent) contains both smallData and largeData
            //This step will make toSplit have 4 pointers.
            else if(toSplit.dataCount() == 2)
            {
                toSplit.setTempChild(toSplit.getRight());
                toSplit.setRight(newRight);
                toSplit.setMiddle(newLeft);
            }
        }
        //Case 4: tNode which called this function is to the right of the toSplit (its parent)
        //This step will make toSplit have 4 pointers
        else if(this == toSplit.getRight())
        {
            toSplit.setTempChild(newRight);
            toSplit.setRight(newLeft);
        }
        //Case 5: tNode which called this function is a leaf.
        else
        {
            toSplit.setLeft(newLeft);
            toSplit.setMiddle(newRight);
        }
    }

    /*
    Function to insert new eList into the tNode
    */
    public void insert(eList newList)
    {
        //Case 1: tNode is empty
        if(smallData == null)
        {
            smallData = new eList(newList);
        }
        //Case 2: tNode only contains smallData
        else if(largeData == null)
        {
            //Case 2a: newList keyword is larger than smallData keyword. insert into largeData
            if(newList.getKeyword().compareTo(smallData.getKeyword()) > 0)
            {
                largeData = new eList(newList);
            }
            //Case 2b: newList keyword is smaller than smallData keyword. Swap smallData into largeData and
            //insert newList into smallData
            else
            {
                largeData = new eList(smallData);
                smallData = new eList(newList);
            }
        }
        //Case 3: tNode contains both smallData and largeData. Insertion will create a 3-tNode (tNode containing 3 datas)
        else
        {
            //Create array list that will store keywords of each list
            ArrayList<String> tempArray = new ArrayList<>();

            //Add keywords of each list into array list
            tempArray.add(smallData.getKeyword());
            tempArray.add(largeData.getKeyword());
            tempArray.add(newList.getKeyword());

            //Store temp lists for future use
            eList tempSmall = smallData;
            eList tempLarge = largeData;
            eList tempMid = newList;

            //Sort array list alphabetically
            Collections.sort(tempArray);

            //Insert correct eLists into correct datas
            smallData = new eList(pickCorrect(tempArray.get(0), tempSmall, tempLarge, tempMid));
            largeData = new eList(pickCorrect(tempArray.get(1), tempSmall, tempLarge, tempMid));
            tempData = new eList(pickCorrect(tempArray.get(2), tempSmall, tempLarge, tempMid));
        }
    }

    /*
    Function that displays tNode's list in order (smallData first, then largeData, given it is not empty)
    */
    public void displayInorder()
    {
        smallData.displayWithKeyword();
        if(largeData != null)
            largeData.displayWithKeyword();
    }
}

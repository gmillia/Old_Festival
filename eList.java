/*
Illia Shershun
CS202 Spring

Class that stores list of events. Will be used in a tree to store events with same keywords associated with them
*/

public class eList
{
    private eNode head;
    private String keyword;

    /*
    Default eList constructor
    */
    eList()
    {
        head = null;
        keyword = new String();
    }

    /*
    eList copy constructor
    */
    eList(eList newElist)
    {
        head = null;
        keyword = new String(newElist.keyword);
        for(eNode start = newElist.head; start != null; start = start.getNext())
        {
            eNode toInsert = new eNode(start);
            addEvent(toInsert);
        }
    }

    /*
    Helper function to check if list is empty: returns true if list is empty
    */
    public boolean isEmpty()
    {
        return (head==null);
    }

    /*
    Returns keyword which is shared among all events in the list:
    Since list contains all the events with particular keyword, we have a specific field keyword, which will be returned
    */
    public String getKeyword()
    {
        return this.keyword;
    }

    /*
    Set keyword this list will be associated with
    */
    public void setKeyword(String newKeyword)
    {
        this.keyword = newKeyword;
    }

    /*
    Function to add event to the list: calls recursive add
    */
    public void addEvent(Event newEvent)
    {
        eNode newNode = new eNode(newEvent);
        if(this.head == null)
            head = newNode;
        else
            recursiveInsert(this.head, newNode);
    }

    /*
    Function to add eNode to the list: calls recursive add
    */
    public void addEvent(eNode newEvent)
    {
        eNode newNode = new eNode(newEvent);
        if(this.head == null)
            this.head = newNode;
        else
            recursiveInsert(this.head, newNode);
    }

    /*
    Helper function to add new eNode to the list (recursive)
    */
    private void recursiveInsert(eNode head_ref, eNode toInsert)
    {
        if(head_ref.getNext() == null)
            head_ref.setNext(toInsert);
        else
            recursiveInsert(head_ref.getNext(), toInsert);
    }

    /*
    Wrapper function to display nodes info: calls recursive display
    */
    public void display()
    {
        recursiveDisplay(head);
    }

    /*
    Wrapper function for a more cleaner display: displays keyword of the the list and JUST name of the events.
    Then it calls recursive display
    */
    public void displayWithKeyword()
    {
        System.out.print("[Key]: " + keyword + "\n[Events]: ");
        displayWithKeyword(head);
        System.out.println("\n-----------------------");
    }

    /*
    Recursive display that shows just the names of the events (eNodes)
    */
    private void displayWithKeyword(eNode head_ref)
    {
        if(head_ref != null)
        {
            System.out.print(head_ref.getName() + ", ");
            displayWithKeyword(head_ref.getNext());
        }
    }

    /*
    Helper recursive display to display full events info
    */
    private void recursiveDisplay(eNode head_ref)
    {
        if(head_ref != null)
        {
            head_ref.display();
            recursiveDisplay(head_ref.getNext());
        }

    }
}

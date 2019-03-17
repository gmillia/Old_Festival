/*
Illia Shershun
CS202 Spring

Child class of the event. That is, every node in the linked list will be an event on itself.
*/

public class eNode extends Event
{
    private eNode next;

    /*
    Default eNode constructor
    */
    eNode()
    {
        this.next = null;
    }

    /*
    eNode constructor with argument:
    1)Kick-starts base class copy constructor
    2)Sets next to null
    */
    eNode(Event newEvent)
    {
        super(newEvent);
        this.next = null;
    }

    /*
    Function to set next to the passed eNode
    */
    public void setNext(eNode newNext)
    {
        this.next = newNext;
    }

    /*
    Returns eNode that this node points to (next)
    */
    public eNode getNext()
    {
        return this.next;
    }
}

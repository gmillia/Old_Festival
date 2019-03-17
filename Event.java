/*
Illia Shershun
CS202 Spring

Class that stores information about each individual event: events name and keywords associated with the event.
*/

public class Event
{
    private String name;
    private String keywords[];
    private int numberOfKeywords;

    /*
    Default constructor
    */
    Event()
    {
        name = new String();
        keywords = new String[10];
        numberOfKeywords = 0;
    }

    /*
    Copy constructor
    */
    Event(Event newEvent)
    {
        this.setName(newEvent.name);
        keywords = new String[10];
        String temp[] = newEvent.keywords;
        int number = newEvent.numberOfKeywords;
        for(int i = 0; i < number; ++i)
            setKeyword(temp[i]);
    }

    Event(String eventName, String[] newKeywords, int arrayLength)
    {
        setName(eventName);
        numberOfKeywords = 0;
        keywords = new String[10];
        for(int i = 0; i < arrayLength; ++i)
            setKeyword(newKeywords[i]);
    }

    /*
    Function to get the name of the event
    */
    public String getName()
    {
        return this.name;
    }

    /*
    Function to set the name of the event
    */
    public void setName(String newName)
    {
        this.name = newName;
    }

    /*
    Function to add new keyword to the event
    */
    public void setKeyword(String newKeyword)
    {
        if(numberOfKeywords > 10)
            return;
        else
        {
            keywords[numberOfKeywords] = newKeyword;
            numberOfKeywords++;
        }
    }

    /*
    Function returns array of all of the keywords associated with the event
    */
    public String[] getKeywords()
    {
        String tempKeywords[] = new String[keywords.length];
        for(int i = 0; i < numberOfKeywords; ++i)
            tempKeywords[i] = keywords[i];

        return tempKeywords;
    }

    /*
    Function returns number of events associated with event
    */
    public int getNumberOfKeywords()
    {
        return numberOfKeywords;
    }

    /*
    Function to display information about the event
    */
    public void display()
    {
        System.out.print("\nEvent name: " + this.name + "\nEvent keywords: ");
        for(int i = 0; i < numberOfKeywords; ++i)
            System.out.print(keywords[i] + ", ");
    }

    public void insertMyself(Tree tree)
    {
        for(int i = 0; i < numberOfKeywords; ++i)
            tree.insert(keywords[i], this);
    }
}

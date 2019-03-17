public class Outside extends Event
{
    private String location;

    Outside()
    {
        location = new String();
    }

    Outside(String eventName, String eventLocation, String[] keywords, int arrayLength)
    {
        super(eventName, keywords, arrayLength);
        setLocation(eventLocation);
    }

    public void setLocation(String newLocation)
    {
        this.location = newLocation;
    }

    public void display()
    {
        System.out.print("\nEvent name: " + this.getName() + "\nEvent location: " + this.location + "\nEvent keywords: ");
        String[] temp = this.getKeywords();
        for(int i = 0; i < this.getNumberOfKeywords(); ++i)
            System.out.print(temp[i] + ", ");
    }
}

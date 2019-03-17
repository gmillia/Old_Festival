import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Main
{
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException
    {
        char comm = '\0';

        Main main = new Main();
        Tree tree = new Tree();
        main.readData(tree);

        main.showMenu(tree);
        comm = main.readCommand();

        while(comm != 'q')
        {
            main.processMenu(tree, comm);
            main.showMenu(tree);
            comm = main.readCommand();
        }
    }

    /*
    Function to read initial data from external data file into the tree
    */
    private void readData(Tree tree) throws FileNotFoundException
    {
        String fileName = new String();
        fileName = "src/Events.txt";

        Scanner read = new Scanner(new File(fileName));

        while(read.hasNextLine())
        {
            Event newEvent;
            String line = read.nextLine();
            String[] details = line.split(": |, |; ");
            String keyArray[] = new String[10];

            String eventName = details[1];
            String eventLocation = details[3];

            int i = 5;
            while(i < details.length)
            {
                keyArray[i-5] = details[i];
                ++i;
            }

            if(eventLocation.compareTo("Inside") == 0)
            {
                newEvent = new Inside(eventName, eventLocation, keyArray, i-5);
            }

            else if(eventLocation.compareTo("Outside") == 0)
            {
                newEvent = new Outside(eventName, eventLocation, keyArray, i-5);
            }
            else
                newEvent = new Event(eventName, keyArray, i-5);

            newEvent.insertMyself(tree);
        }
    }

    /*
    Helper function to display menu to the user
    */
    private void showMenu(Tree tree)
    {
        System.out.println("----------------------MENU-------------------");
        System.out.println("1.Press 1 to display existing tree: ");
        System.out.println("2.Press 2 to delete the tree: ");
        System.out.println("3.Press 3 to add new Event: ");
        System.out.println("4.Press 4 to retrieve all events for particular #hashtag (keyword): ");
        System.out.println("Press Q/q to quit: ");
    }

    /*
    Helper function to process user choice
    */
    private void processMenu(Tree tree, char command)
    {
        switch(command)
        {
            case '1': tree.display();
                break;
            case '2': tree.removeTree();
                break;
            case '3': newEvent(tree);
                break;
            case '4': hashtagHelper(tree);
                break;
            default:
            {
                System.out.println("Error! Illegal command.");
                break;
            }
        }
    }

    /*
    Helper function to read in info about new event
    */
    private void newEvent(Tree tree)
    {
        Event newEvent;
        String eventName;
        String newKeyword;
        String eventLocation;
        String keyArray[] = new String[10];
        int count = 0;
        char cmd = '\0';

        System.out.print("Events name: ");
        eventName = input.nextLine();
        System.out.print("Events location (Inside/Outside/else): ");
        eventLocation = input.nextLine();

        while(count < 10 && cmd != 'n' && cmd != 'N')
        {
            System.out.print(eventName + " hashtag (keyword): ");
            newKeyword = input.nextLine();
            keyArray[count] = newKeyword;

            System.out.print("Add another hashtag (keyword)?(Y/N): ");
            cmd = input.next().charAt(0);
            input.nextLine();
            count++;
        }

        if(eventLocation.compareTo("Inside") == 0)
        {
            newEvent = new Inside(eventName, eventLocation, keyArray, count);
        }

        else if(eventLocation.compareTo("Outside") == 0)
        {
            newEvent = new Outside(eventName, eventLocation, keyArray, count);
        }
        else
            newEvent = new Event(eventName, keyArray, count);

        newEvent.insertMyself(tree);
    }

    /*
    Helper function to find and display Events with user-defined keyword
    */
    private void hashtagHelper(Tree tree)
    {
        String searchWord;

        System.out.print("Hashtag (keyword): ");
        searchWord = input.nextLine();

        tree.findList(searchWord);
    }

    /*
    Helper function to read user input for the menu
    */
    private char readCommand()
    {
        char out;
        System.out.print("Your choice: ");
        out = input.next().charAt(0);
        input.nextLine();
        return out;
    }
}

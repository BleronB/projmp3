import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie chosen = results.get(choice - 1);

    displayMovieInfo(chosen);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortSResults(ArrayList<String> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      String temp = listToSort.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
  }

  private void searchCast()
  {
    System.out.print("enter a cast: ");
    String search = scanner.nextLine();

    search = search.toLowerCase();

    ArrayList<String> results = new ArrayList<String>();

    for (int i = 0; i < movies.size(); i++)
    {
      String[] movieCast = movies.get(i).getCast().split("\\|");

      for (int j = 0; j < movieCast.length; j++)
      {
        if (movieCast[j].toLowerCase().indexOf(search) != -1 && results.indexOf(movieCast[j]) == -1)
        {
          results.add(movieCast[j]);
        }
      }
    }
    sortSResults(results);

    for (int i = 0; i < results.size(); i++)
    {
      String person = results.get(i);

      int choice = i + 1;

      System.out.println(choice + ". " + person);
    }

    System.out.println("Which cast do you want to explore");
    System.out.print("enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String selectedPerson = results.get(choice - 1);

    ArrayList<Movie> theirMovies = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++) {
      Movie movie = movies.get(i);
      if (movie.getCast().contains(selectedPerson)) {
        theirMovies.add(movie);
      }
    }

    sortResults(theirMovies);

    for (int i = 0; i < theirMovies.size(); i++)
    {
      String title = theirMovies.get(i).getTitle();

      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you iike to study");
    System.out.print("Enter number: ");

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = theirMovies.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n press enter to menu");
    scanner.nextLine();
  }

  private void searchKeywords()
  {
    System.out.print("enter a keyword: ");
    String search = scanner.nextLine();
    search = search.toLowerCase();
    ArrayList<Movie> results = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++)
    {
      String movieWord = movies.get(i).getKeywords();
      movieWord = movieWord.toLowerCase();

      if (movieWord.indexOf(search) != -1)
      {
        results.add(movies.get(i));
      }
    }
    sortResults(results);

    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("what movie would you want to explore?");
    System.out.print("enter digits: ");
    int num = scanner.nextInt();
    scanner.nextLine();
    Movie chosen= results.get(num - 1);

    displayMovieInfo(chosen);

    System.out.println("press enter to go to menu");
    scanner.nextLine();
  }

  private void listGenres()
  {
    ArrayList<String> allGenres = new ArrayList<String>();

    for (int i = 0; i < movies.size(); i++)
    {
      String[] genres = movies.get(i).getGenres().split("\\|");

      for (String genre : genres)
      {
        if (allGenres.indexOf(genre) == -1)
        {
          allGenres.add(genre);
        }
      }
    }

    sortSResults(allGenres);

    for (int i = 0; i < allGenres.size(); i++)
    {
      String genre = allGenres.get(i);
      int choiceNum = i + 1;
      System.out.println(choiceNum + ", " + genre);
    }

    System.out.println("Which genre would you like to explore?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String selectedGenre = allGenres.get(choice - 1);

    ArrayList<Movie> results = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++)
    {
      String genre = movies.get(i).getGenres();
      if (genre.indexOf(selectedGenre) != -1)
      {
        results.add(movies.get(i));
      }


    }
    sortResults(results);

    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      int choiceNum = i + 1;

      System.out.println(choiceNum + ", " + title);
    }

    System.out.println("pick a movie to study");
    System.out.print("Enter digits: ");

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("press enter to go to the main menu");
    scanner.nextLine();
  }

  private void listHighestRated()
  {
    /* TASK 6: IMPLEMENT ME! */
  }
  
  private void listHighestRevenue()
  {
    /* TASK 6: IMPLEMENT ME! */
  }

  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String str = bufferedReader.readLine();
      movies = new ArrayList<Movie>();

      while ((str = bufferedReader.readLine()) != null)
      {
        String[] movieFromCSV = str.split(",");
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagLine = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtine = Integer.parseInt((movieFromCSV[6]));
        String genres = movieFromCSV[7];
        double rate = Double.parseDouble((movieFromCSV[8]));
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);
        Movie nextMovie = new Movie(title, cast,director,tagLine,keywords,overview, runtine,genres,rate, year, revenue);
        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch (IOException exception)
    {
      System.out.println("not available " + exception.getMessage());
    }
  }
  
  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}
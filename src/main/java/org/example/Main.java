import core.data.*;
import org.knowm.xchart.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//use java f

public class Main {

  // Displays runtime to average score
  private static void displayRuntimeToAvgScore(ArrayList<Movie> movies) {
    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Average Score vs. Runtime").xAxisTitle("Runtime (minutes)").yAxisTitle("Average Score").build();
    List<Integer> xData = new ArrayList<Integer>();
    List<Double> yData = new ArrayList<Double>();
    int max = 0;
    for (Movie movie : movies) {
      if (movie.getRuntime() > max) {
        max = movie.getRuntime();
      }
    }
    int[] counts = new int[(max / 10) + 1];
    double[] sums = new double[(max / 10) + 1];
    for (Movie movie : movies) {
      int index = movie.getRuntime() / 10;
      counts[index]++;
      sums[index] += movie.getAverageScore();
    }
    for (int i = 0; i < max / 10; i++) {
      xData.add(i * 10);
      yData.add(sums[i] / counts[i]);
    }
    chart.addSeries("Movies", xData, yData);

    new SwingWrapper<CategoryChart>(chart).displayChart();
  }


  // Displays genre to average score
  public static void displayGenreToAvgScore(ArrayList<Movie> movies) {
    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Average Score vs. Genre").xAxisTitle("Genre").yAxisTitle("Average Score").build();
    List<String> xData = new ArrayList<String>();
    List<Double> yData = new ArrayList<Double>();
    // list of genres to iterate through
    ArrayList<String> genres = new ArrayList<String>();
    for (Movie movie : movies) {
      for (String genre : movie.getGenres()) {
        if (!genres.contains(genre)) {
          genres.add(genre);
        }
      }
    }

    for (String genre : genres) {
      double sum = 0;
      int count = 0;
      for (Movie movie : movies) {
        if (movie.getGenres().contains(genre)) {
          sum += movie.getAverageScore();
          count++;
        }
      }
      xData.add(genre);
      yData.add(sum / count);
    }

    // display bars from lowest to highest average score using bubble sort
    for (int i = 0; i < yData.size(); i++) {
      for (int j = i + 1; j < yData.size(); j++) {
        if (yData.get(i) > yData.get(j)) {
          String temp = xData.get(i);
          xData.set(i, xData.get(j));
          xData.set(j, temp);
          double temp2 = yData.get(i);
          yData.set(i, yData.get(j));
          yData.set(j, temp2);
        }
      }
    }

    chart.addSeries("Movies", xData, yData);

    new SwingWrapper<CategoryChart>(chart).displayChart();
  }

  // Displays top 10 most common words in movie titles
  public static void displayTop10Words(ArrayList<Movie> movies) {
    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Top 10 Most Common Words in Movie Titles").xAxisTitle("Word").yAxisTitle("Frequency").build();
    List<String> xData = new ArrayList<String>();
    List<Integer> yData = new ArrayList<Integer>();

    for (Movie movie : movies) {
      String[] titleWords = movie.getTitle().split(" ");
      for (String word : titleWords) {
        if (!xData.contains(word.toUpperCase())) {
          xData.add(word.toUpperCase());
          yData.add(1);
        } else {
          int index = xData.indexOf(word.toUpperCase());
          yData.set(index, yData.get(index) + 1);
        }
      }
    }

    // display bars from lowest to highest frequency using bubble sort
    for (int i = 0; i < yData.size(); i++) {
      for (int j = i + 1; j < yData.size(); j++) {
        if (yData.get(i) > yData.get(j)) {
          String temp = xData.get(i);
          xData.set(i, xData.get(j));
          xData.set(j, temp);
          int temp2 = yData.get(i);
          yData.set(i, yData.get(j));
          yData.set(j, temp2);
        }
      }
    }

    chart.addSeries("Movies", xData.subList(xData.size() - 10, xData.size()), yData.subList(yData.size() - 10, yData.size()));

    new SwingWrapper<CategoryChart>(chart).displayChart();
  }

  // Age rating distribution
  public static void displayAgeRatingDistribution(ArrayList<Movie> movies) {
      CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Age Rating Distribution").xAxisTitle("Age Rating").yAxisTitle("Frequency").build();
      List<String> xData = new ArrayList<String>();
      List<Integer> yData = new ArrayList<Integer>();

      for (Movie movie : movies) {
        String ageRating = movie.getAgeRating();

        if (ageRating.equals("")) {
          continue;
        }

        if (!xData.contains(ageRating)) {
          xData.add(ageRating);
          yData.add(1);
        } else {
          int index = xData.indexOf(ageRating);
          yData.set(index, yData.get(index) + 1);
        }
      }

      // display bars from lowest to highest frequency using bubble sort
      for (int i = 0; i < yData.size(); i++) {
        for (int j = i + 1; j < yData.size(); j++) {
          if (yData.get(i) > yData.get(j)) {
            String temp = xData.get(i);
            xData.set(i, xData.get(j));
            xData.set(j, temp);
            int temp2 = yData.get(i);
            yData.set(i, yData.get(j));
            yData.set(j, temp2);
          }
        }
      }

      chart.addSeries("Movies", xData, yData);

      new SwingWrapper<CategoryChart>(chart).displayChart();
  }

  public static void main(String[] args) {

    DataSource ds = DataSource.connectAs("CSV", "./titles.csv");
    ds.load();

    ArrayList<Movie> movies = new ArrayList<Movie>();

    String[] ids = ds.fetchStringArray("id");
    String[] titles = ds.fetchStringArray("title");
    String[] type = ds.fetchStringArray("type");
    String[] descriptions = ds.fetchStringArray("description");
    int[] releaseYears = ds.fetchIntArray("release_year");
    String[] ageRatings = ds.fetchStringArray("age_certification");
    int[] runtimes = ds.fetchIntArray("runtime");
    String[] genres = ds.fetchStringArray("genres");
    double[] imdb_score = ds.fetchDoubleArray("imdb_score");
    double[] tmdb_score = ds.fetchDoubleArray("tmdb_score");

    // Creating movie objects
    for (int i = 0; i < ids.length; i++) {
      String[] genreArray = genres[i].split(", ");
      ArrayList<String> genreList = new ArrayList<String>();
      for (String genre : genreArray) {
        //strip whitespace, quotes, and brackets since csv file has inconsistent formatting for genres
        genre = genre.strip();
        genre = genre.replace("[", "");
        genre = genre.replace("]", "");
        genre = genre.replace("'", "");

        if (genre.equals("")) {
          continue;
        }

        genreList.add(genre);
      }

      if (type[i].equals("MOVIE")) {
        Movie movie = new Movie(ids[i], titles[i], descriptions[i], releaseYears[i], ageRatings[i], runtimes[i], genreList, imdb_score[i], tmdb_score[i]);
        movies.add(movie);
      }
    }

    // Displaying the runtime to average score chart
    displayRuntimeToAvgScore(movies);

    // Displaying the genre to average score chart
    displayGenreToAvgScore(movies);

    // Displaying the top 10 most common words in movie titles
    displayTop10Words(movies);

    // Displaying the age rating distribution
    displayAgeRatingDistribution(movies);
  }
}
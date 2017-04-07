package tigertouch;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

import static tigertouch.Animal.Category.AMPHIBIANS;

public class Main {
  static String animalsFilepath = "Animals.json";
  static int numAnimals = 20;
  static List<Animal> animals = new ArrayList<>();
  static Animal currentAnimal;
  static double currX = 0;
  static double currY = 0;
  static double speed = 0;
  static double lastChange = System.currentTimeMillis();
  static int[][] grid;

  public static void main(String[] args) {


    try {
      String fileString = readFile(animalsFilepath, Charset.defaultCharset());
      JSONArray jsonArray = new JSONArray(fileString);
      for (int i = 0; i < jsonArray.length(); i++){
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        String name = jsonObject.getString("name");
        Animal.Category category = Animal.Category.valueOf(jsonObject.getString("category"));
        String soundFile = jsonObject.getString("soundFile");
        String imageFile = jsonObject.getString("imageFile");
        String hapticFile = jsonObject.getString("hapticFile");
        Animal animal = new Animal(name, category, soundFile, imageFile, hapticFile);
        animals.add(animal);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    currentAnimal = animals.get(0);


    grid = processImage(currentAnimal.imageFile);
//    Picture picture = processImage(currentAnimal.hapticFile);
    int width = grid.length;
    int height = grid[0].length;
//    int width = picture.width();
//    int height = picture.height();

    // Create Canvas Area
    StdDraw.setCanvasSize(600, 600);
    StdDraw.setXscale(0, width);
    StdDraw.setYscale(0, height);
//    picture.show();
    /*for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        int lightVal = grid[i][j];
        StdDraw.setPenColor(lightVal, lightVal, lightVal);
        StdDraw.filledSquare(i, j, 1);
      }
    }*/
    StdDraw.picture(0, 0, currentAnimal.hapticFile);
    StdDraw.show();
    StdDraw.setPenColor(Color.black);
    StdDraw.text(300, 50, "Instructions: Move mouse around screen while holding down left mouse to simulate.");

    // Begin Simulation Loop
    while(true){
      if(currX != StdDraw.mouseX() || currY != StdDraw.mouseY() || StdDraw.isKeyPressed(32) || StdDraw.mousePressed()) {
        double distance = Math.sqrt(Math.pow(StdDraw.mouseX()-currX,2)+Math.pow(StdDraw.mouseY()-currY,2));
        double time = System.currentTimeMillis()-lastChange;
        speed = distance / time;
        userInput();
      }
    }
  }

  public static void userInput() {
    StdDraw.clear();
    StdDraw.picture(0, 0, currentAnimal.hapticFile);
    StdDraw.setPenColor(Color.green);
    StdDraw.circle(StdDraw.mouseX(), StdDraw.mouseY(),40);
    currX = StdDraw.mouseX();
    currY = StdDraw.mouseY();
    lastChange = System.currentTimeMillis();

    if (StdDraw.mousePressed()) {
      int mouseX = (int)StdDraw.mouseX();
      int mouseY = (int)StdDraw.mouseY();
      System.out.println("Finger position: "+ mouseX+","+ mouseY);
      System.out.println("Roughness: " + getRoughness(mouseX, mouseY, 5, grid));
      //If space is pressed, apply additional force
      if (StdDraw.isKeyPressed(32)) {
        System.out.println("More Force too");
        //updateDisplay(currX, currY, speed);
      }
      //updateDisplay(currX, currY, speed);
    }
  }

  /**
   * Gets the roughness of an area by location and size.
   * @param x x location of the grid
   * @param y y location of the grid
   * @param radius radius of the area to calculate roughness for
   * @return the average difference in values of the given area
   */
  public static double getRoughness(int x, int y, int radius, int[][] grid) {
    double sum = 0;
    int numValues = 0;
    for (int i = x - radius; i < x + radius; i++) {
      for (int j = y - (radius - Math.abs((x - i))); j < y + (radius - Math.abs((x - i))); j++) {
        if (i > 0 && j > 0 && i < grid.length - 1 && j < grid[0].length - 1) {
          sum += grid[i][j] - grid[i - 1][j];
          sum += grid[i][j] - grid[i + 1][j];
          sum += grid[i][j] - grid[i][j - 1];
          sum += grid[i][j] - grid[i][j + 1];
          sum += grid[i][j] - grid[i - 1][j - 1];
          sum += grid[i][j] - grid[i - 1][j + 1];
          sum += grid[i][j] - grid[i + 1][j - 1];
          sum += grid[i][j] - grid[i + 1][j + 1];
        }
        sum += grid[i][j];
        numValues++;
      }
    }

    return sum/numValues;
  }


  /**
   * Gets an image from a filepath and returns a 2D array of it's light values;
   * @param filename location of the file
   * @return 2D array of doubles representing brightness
   */
  public static int[][] processImage(String filename) {
    int width = 1200;
    int height = 1200;
    Picture picture = new Picture(filename);

    int oldW = picture.width();
    int oldH = picture.height();
    Picture newPic = new Picture(width, height);

    //scale down
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int scaledX = (int) (((double) x / width) * oldW);
        int scaledY = (int) (((double) y / height) * oldH);
        newPic.set(x, y, picture.get(scaledX, scaledY));
      }
    }

    int[][] grid = new int[newPic.width()][newPic.height()];
    // convert to grayscale
    for (int i = 0; i < newPic.width(); i++) {
      for (int j = 0; j < newPic.height(); j++) {
        int l = (int) Luminance.lum(newPic.get(i, j));
        grid[i][j] = l;
        newPic.set(i, j, new Color(l, l, l));
      }
    }

    String newFileName = "haptic/" + filename.replace("image/", "");
    newPic.save(newFileName);
    currentAnimal.hapticFile = newFileName;
    return grid;
  }


  static String readFile(String path, Charset encoding) throws IOException  {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }
  public static void selectAnimal(int i) {
    currentAnimal = animals.get(i);
  }
}

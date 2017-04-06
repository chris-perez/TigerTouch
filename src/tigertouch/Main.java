package tigertouch;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static tigertouch.Animal.Category.AMPHIBIANS;

public class Main {
  static int numAnimals = 20;
  static List<Animal> animals = new ArrayList<>();
  static Animal currentAnimal;
  static double currX = 0;
  static double currY = 0;
  static double speed = 0;
  static double lastChange = System.currentTimeMillis();

  public static void main(String[] args) {
    for (int i = 0; i < numAnimals; i++){
      String name = "";
      Animal.Category category = AMPHIBIANS;
      String soundFile = "";
      String imageFile = "";
      String hapticFile = "";
      Animal animal = new Animal(name, category, soundFile, imageFile, hapticFile);
      animals.add(animal);
    }

    // Create Canvas Area
    StdDraw.setCanvasSize(600, 600);
    StdDraw.setXscale(0, 600);
    StdDraw.setYscale(0, 600);
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
    StdDraw.circle(StdDraw.mouseX(), StdDraw.mouseY(),40);
    currX = StdDraw.mouseX();
    currY = StdDraw.mouseY();
    lastChange = System.currentTimeMillis();

    if (StdDraw.mousePressed()) {
      System.out.println("Finger position: "+ StdDraw.mouseX()+","+ StdDraw.mouseY());
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
  public static double getRoughness(int x, int y, int radius) {
    double[][] grid = new double[100][100];

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

  public static void selectAnimal(int i) {
    currentAnimal = animals.get(i);
  }
}

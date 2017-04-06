package tigertouch;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static tigertouch.Animal.Category.AMPHIBIANS;

public class Main {
  static int numAnimals = 20;
  static List<Animal> animals = new ArrayList<>();
  static Animal currentAnimal;

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
  }

  public static double getTouchValue(int x, int y) {
    int radius = 4;
    double[][] grid = new double[100][100];

    double sum = 0;
    int numValues = 0;
    for (int i = x - radius; i < x + radius; i++) {
      for (int j = y - (radius - Math.abs((x - i))); j < y + (radius - Math.abs((x - i))); j++) {
        sum += grid[x][y];
        numValues++;
      }
    }

    return sum/numValues;
  }

  public static void selectAnimal(int i) {
    currentAnimal = animals.get(i);
  }
}

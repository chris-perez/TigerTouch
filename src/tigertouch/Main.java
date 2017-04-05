package tigertouch;
import java.util.ArrayList;
import java.util.List;
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

  public static void selectAnimal(int i) {
    currentAnimal = animals.get(i);
  }
}

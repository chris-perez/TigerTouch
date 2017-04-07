package tigertouch;

public class Animal {
  public String name;
  public String imageFile;
  public String hapticFile;
  public Category category;
  public double frictionCoefficient;

  public Animal(String nameIn, Category categoryIn, String imageFileIn, String hapticFileIn, double frictionCoefficient) {
    name = nameIn;
    imageFile = imageFileIn;
    hapticFile = hapticFileIn;
    category = categoryIn;
    this.frictionCoefficient = frictionCoefficient;
  }

  public enum Category{
    INVERTEBRATES,REPTILES,AMPHIBIANS,FISH,BIRDS,MAMMALS
  }

/*public enum Category {
  INVERTEBRATES("invertebrates"), REPTILES("reptiles"), AMPHIBIANS("amphibians"), FISH(""), BIRDS("fish"), MAMMALS("mammals");
  private String name;

  Category(String name) {
    this.name = name;
  }

}*/
}

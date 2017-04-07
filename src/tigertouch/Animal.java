package tigertouch;

public class Animal {
  public String name;
  public String soundFile;
  public String imageFile;
  public String hapticFile;
  public Category category;

  public Animal(String nameIn, Category categoryIn, String soundFileIn, String imageFileIn, String hapticFileIn) {
    name = nameIn;
    soundFile = soundFileIn;
    imageFile = imageFileIn;
    hapticFile = hapticFileIn;
    category = categoryIn;
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

package co.icreated.portal.bean;

public class IdNameDto {

  int id = 0;
  String name;



  public IdNameDto(int id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public IdNameDto() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }



}

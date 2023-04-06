package copying;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ComplexObject implements Serializable
{
   private String name;
   private int age;
   private List<String> hobbies;

   // use @JsonManagedReference to mark the children field
   @JsonManagedReference
   private List<ComplexObject> children;

   // use @JsonBackReference to mark the parent field
   @JsonBackReference
   private ComplexObject parent;

   public ComplexObject() {
   }

   public ComplexObject(String name, int age, List<String> hobbies, ComplexObject parent, List<ComplexObject> children) {
      this.name = name;
      this.age = age;
      this.hobbies = hobbies;
      this.parent = parent;
      this.children = children;
   }

   public String getName() {
      return name;
   }

   public int getAge() {
      return age;
   }

   public List<String> getHobbies() {
      return hobbies;
   }

   public ComplexObject getParent() {
      return parent;
   }

   public List<ComplexObject> getChildren() {
      return children;
   }

   public void setParent(ComplexObject parent) {
      this.parent = parent;
   }

   public void setChildren(List<ComplexObject> children) {
      this.children = children;
   }
}

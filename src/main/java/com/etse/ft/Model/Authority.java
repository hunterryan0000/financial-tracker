package com.etse.ft.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Model class representing an Authority, or a user role within the application.
 *
 * Contains the name of the role. Note that this begins with "ROLE_" in all caps.
 */
@Entity // Marks this class as a JPA entity
public class Authority {

   @Id
   private String name;

   public Authority() {
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Authority(String name) {
      this.name = name;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Authority authority = (Authority) o;
      return name.equals(authority.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name);
   }

   @Override
   public String toString() {
      return "Authority{" +
         "name=" + name +
         '}';
   }
}

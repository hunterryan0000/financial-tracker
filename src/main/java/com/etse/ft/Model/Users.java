package com.etse.ft.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Model class representing an application user.
 *
 * Contains information about the user - their id, username, address information,
 * password (hashed) and authorities (user roles).
 */
@Entity
@Table(name = "users")
public class Users {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "user_id")
   private Long id;
   @Column(name = "username", unique = true, nullable = false)
   private String username;
   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "address")
   private String address;

   @Column(name = "city")
   private String city;

   @Column(name = "state_code")
   private String stateCode;

   @Column(name = "zip")
   private String ZIP;

   @JsonIgnore
   @Column(name = "password_hash", nullable = false)
   private String password;

   @JsonIgnore
   @Column(name = "activated")
   @Transient
   private boolean activated = true;

   @ManyToMany(fetch = FetchType.LAZY) // Use LAZY fetching strategy by default
   @JoinTable(
           name = "users_authorities", // Define the join table that maps the association
           joinColumns = @JoinColumn(name = "user_id"), // Column linking to the Users entity primary key
           inverseJoinColumns = @JoinColumn(name = "authority_name") // Column linking to the Authority entity primary key
   )
   private Set<Authority> authorities = new HashSet<>();

   public Users() { }


   public Users(Long id, String username, String password, String name, String address, String city, String stateCode, String ZIP) {
      this.id = id;
      this.username = username;
      this.password = password; // Note: password should be encrypted outside of the constructor
      this.name = name;
      this.address = address;
      this.city = city;
      this.stateCode = stateCode;
      this.ZIP = ZIP;
      this.activated = true; // Depending on your business logic, this could be handled differently
   }

   public Users(String username, String password, String name, String address, String city, String stateCode, String ZIP) {
      this(0L, username, password, name, address, city, stateCode, ZIP);
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public String getAuthoritiesString() {
      String authString = "";
      for (Authority auth : authorities) {
         if (authString.length() == 0) {
            authString += auth.getName();
         } else {
            authString += "," + auth.getName();
         }
      }
      return authString;
   }

//   public void setAuthorities(Set<Authority> authorities) {
//      this.authorities = authorities;
//   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getStateCode() {
      return stateCode;
   }

   public void setStateCode(String stateCode) {
      this.stateCode = stateCode;
   }

   public String getZIP() {
      return ZIP;
   }

   public void setZIP(String ZIP) {
      this.ZIP = ZIP;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Users user = (Users) o;
      return id == user.id &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(name, user.name) &&
              Objects.equals(address, user.address) &&
              Objects.equals(city, user.city) &&
              Objects.equals(stateCode, user.stateCode) &&
              Objects.equals(ZIP, user.ZIP) &&
              Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", name='" + name + '\'' +
              ", address='" + address + '\'' +
              ", city='" + city + '\'' +
              ", state='" + stateCode + '\'' +
              ", zip='" + ZIP + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }
}

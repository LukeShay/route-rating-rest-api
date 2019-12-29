package com.lukeshay.restapi.gym;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

/** The type Gym. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
class Gym implements Persistable<String> {
  @Id @Expose private String id;

  @CreatedDate
  @JsonProperty(access = Access.WRITE_ONLY)
  private String createdDate;

  @LastModifiedDate
  @JsonProperty(access = Access.WRITE_ONLY)
  private String modifiedDate;

  @JsonProperty(access = Access.WRITE_ONLY)
  private boolean persistable;

  @Expose private String name;
  @Expose private String address;
  @Expose private String city;
  @Expose private String state;
  @Expose private String website;
  @Expose private String email;
  @Expose private String phoneNumber;
  @Expose private List<String> authorizedEditors;

  public Gym(
      String name,
      String address,
      String city,
      String state,
      String website,
      String email,
      String phoneNumber,
      List<String> authorizedEditors) {
    this.name = name;
    this.address = address;
    this.city = city;
    this.state = state;
    this.website = website;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.authorizedEditors = authorizedEditors;
  }

  @Override
  public boolean isNew() {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Gym && toString().equals(obj.toString());
  }

  @Override
  public String toString() {
    return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this);
  }
}

package com.lukeshay.restapi.route;

import com.google.gson.annotations.Expose;
import com.lukeshay.restapi.rating.route.RouteRating;
import com.lukeshay.restapi.route.RouteProperties.Grade;
import com.lukeshay.restapi.utils.ModelUtils;
import com.lukeshay.restapi.wall.WallProperties.WallTypes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "routes")
public class Route { // extends Auditable<String> {
  @Column(name = "id", unique = true, updatable = false)
  @Expose
  @GeneratedValue(generator = "pg-uuid")
  @GenericGenerator(name = "pg-uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @Id
  String id;

  @Column(name = "wall_id", updatable = false)
  @Expose
  String wallId;

  @Column(name = "gym_id", updatable = false)
  @Expose
  String gymId;

  @Column(name = "name")
  @Expose
  private String name;

  @Column(name = "setter")
  @Expose
  private String setter;

  @Column(name = "hold_color")
  @Expose
  private String holdColor;

  @Column(name = "types")
  @ElementCollection(fetch = FetchType.EAGER)
  @Expose
  private List<WallTypes> types;

  @Column(name = "average_grade")
  @Expose
  private Grade averageGrade;

  @Column(name = "average_rating")
  @Expose
  private double averageRating;

  public Route(
      String wallId,
      String gymId,
      String name,
      String setter,
      String holdColor,
      List<WallTypes> types) {
    this.wallId = wallId;
    this.gymId = gymId;
    this.name = name;
    this.setter = setter;
    this.holdColor = holdColor;
    this.types = types;
  }

  public void updateAverages(List<RouteRating> ratings) {
    List<Grade> userGrades = new ArrayList<>();
    List<Integer> userRatings = new ArrayList<>();

    ratings.forEach((rating) -> {
      userGrades.add(rating.getGrade());
      userRatings.add(rating.getRating());
    });

    int numberGrades = userGrades.size();
    int numberRatings = userRatings.size();
    double averageGrade;
    double averageRating;

    averageGrade = userGrades.stream().mapToDouble(Grade::getValue).sum() / numberGrades;
    averageRating = userRatings.stream().mapToDouble(element -> element).sum() / numberRatings;

    setAverageRating(averageRating);
    setAverageGrade(Grade.getGrade(averageGrade));
  }

  @Override
  public boolean equals(Object obj) {
    return ModelUtils.equals(this, obj);
  }

  @Override
  public String toString() {
    return ModelUtils.toString(this);
  }
}

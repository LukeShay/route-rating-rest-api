package com.lukeshay.restapi.route;

import com.google.gson.annotations.Expose;
import com.lukeshay.restapi.rating.route.RouteRating;
import com.lukeshay.restapi.route.RouteProperties.Grade;
import com.lukeshay.restapi.utils.Auditable;
import com.lukeshay.restapi.utils.ModelUtils;
import com.lukeshay.restapi.wall.WallProperties.WallTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "routes")
public class Route extends Auditable<String> implements Serializable {

	@Column(name = "id", unique = true, updatable = false)
	@Expose
	@GeneratedValue(generator = "pg-uuid")
	@GenericGenerator(name = "pg-uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Id
	private String id;

	@Column(name = "wall_id", updatable = false)
	@Expose
	private String wallId;

	@Column(name = "gym_id", updatable = false)
	@Expose
	private String gymId;

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

	Route() {
	}

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

	public Grade getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(Grade averageGrade) {
		this.averageGrade = averageGrade;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
	}

	public String getHoldColor() {
		return holdColor;
	}

	public void setHoldColor(String holdColor) {
		this.holdColor = holdColor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSetter() {
		return setter;
	}

	public void setSetter(String setter) {
		this.setter = setter;
	}

	public List<WallTypes> getTypes() {
		return types;
	}

	public void setTypes(List<WallTypes> types) {
		this.types = types;
	}

	public String getWallId() {
		return wallId;
	}

	public void setWallId(String wallId) {
		this.wallId = wallId;
	}

	public void updateAverages(List<RouteRating> ratings) {
		List<Grade> userGrades = new ArrayList<>();
		List<Integer> userRatings = new ArrayList<>();

		ratings.forEach(
			(rating) -> {
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
	public String toString() {
		return ModelUtils.toString(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Route route = (Route) o;
		return Double.compare(route.averageRating, averageRating) == 0
			&& Objects.equals(id, route.id)
			&& Objects.equals(wallId, route.wallId)
			&& Objects.equals(gymId, route.gymId)
			&& Objects.equals(name, route.name)
			&& Objects.equals(setter, route.setter)
			&& Objects.equals(holdColor, route.holdColor)
			&& ModelUtils.collectionsEqual(types, route.types)
			&& averageGrade == route.averageGrade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			id, wallId, gymId, name, setter, holdColor, types, averageGrade, averageRating);
	}
}

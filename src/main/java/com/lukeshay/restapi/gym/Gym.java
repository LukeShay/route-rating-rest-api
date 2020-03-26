package com.lukeshay.restapi.gym;

import com.google.gson.annotations.Expose;
import com.lukeshay.restapi.utils.Auditable;
import com.lukeshay.restapi.utils.ModelUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Gym.
 */
@Entity
@Table(name = "gyms")
public class Gym extends Auditable<String> implements Serializable {

	@Column(name = "id", unique = true, updatable = false)
	@Expose
	@GeneratedValue(generator = "pg-uuid")
	@GenericGenerator(name = "pg-uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Id
	private String id;

	@Column(name = "name")
	@Expose
	private String name;

	@Column(name = "address")
	@Expose
	private String address;

	@Column(name = "city")
	@Expose
	private String city;

	@Column(name = "state")
	@Expose
	private String state;

	@Column(name = "zip_code")
	@Expose
	private String zipCode;

	@Column(name = "website")
	@Expose
	private String website;

	@Column(name = "email")
	@Expose
	private String email;

	@Column(name = "phone_number")
	@Expose
	private String phoneNumber;

	@Column(name = "logo_url")
	@Expose
	private String logoUrl;

	@Column(name = "photo_url")
	@Expose
	private String photoUrl;

	@Column(name = "authorized_editors")
	@ElementCollection(fetch = FetchType.EAGER)
	@Expose
	private List<String> authorizedEditors;

	public Gym() {
	}

	public Gym(
		String name,
		String address,
		String city,
		String state,
		String zipCode,
		String website,
		String email,
		String phoneNumber,
		List<String> authorizedEditors) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.website = website;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.authorizedEditors = authorizedEditors;
	}

	public Gym(
		String id,
		String name,
		String address,
		String city,
		String state,
		String zipCode,
		String website,
		String email,
		String phoneNumber,
		String logoUrl,
		String photoUrl,
		List<String> authorizedEditors) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.website = website;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.logoUrl = logoUrl;
		this.photoUrl = photoUrl;
		this.authorizedEditors = authorizedEditors;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getAuthorizedEditors() {
		return authorizedEditors;
	}

	public void setAuthorizedEditors(List<String> authorizedEditors) {
		this.authorizedEditors = authorizedEditors;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
		Gym gym = (Gym) o;
		return Objects.equals(id, gym.id)
			&& Objects.equals(name, gym.name)
			&& Objects.equals(address, gym.address)
			&& Objects.equals(city, gym.city)
			&& Objects.equals(state, gym.state)
			&& Objects.equals(zipCode, gym.zipCode)
			&& Objects.equals(website, gym.website)
			&& Objects.equals(email, gym.email)
			&& Objects.equals(phoneNumber, gym.phoneNumber)
			&& Objects.equals(logoUrl, gym.logoUrl)
			&& Objects.equals(photoUrl, gym.photoUrl)
			&& ModelUtils.collectionsEqual(authorizedEditors, gym.authorizedEditors);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			id,
			name,
			address,
			city,
			state,
			zipCode,
			website,
			email,
			phoneNumber,
			logoUrl,
			photoUrl,
			authorizedEditors);
	}
}

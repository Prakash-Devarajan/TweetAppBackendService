
package com.tweetapp.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;

@Document(collection = "user")
public class User {
	@Id
	@JsonProperty("id")
	private ObjectId id;
	@NonNull
	@JsonProperty("loginId")
	@Indexed(unique = true)
	private String loginId;
	@NonNull
	@JsonProperty("firstName")
	private String firstName;
	@NonNull
	@JsonProperty("lastName")
	private String lastName;
	@NonNull
	@JsonProperty("email")
	@Indexed(unique = true)
	private String email;
	@NonNull
	@JsonProperty("password")
	private String password;
	@NonNull
	@JsonProperty("confirmPassword")
	private String confirmPassword;
	@NonNull
	@JsonProperty("contactNumber")
	private String contactNumber;

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginId=" + loginId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", confirmPasword=" + confirmPassword
				+ ", contactNumber=" + contactNumber + "]";
	}

//	public User(ObjectId id, String loginId, String firstName, String lastName, String email, String password,
//			String confirmPasword, String contactNumber) {
//		super();
//		this.id = id;
//		this.loginId = loginId;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//		this.confirmPassword = confirmPasword;
//		this.contactNumber = contactNumber;
//	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirmPasword
	 */
	public String getConfirmPasword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPasword the confirmPasword to set
	 */
	public void setConfirmPasword(String confirmPasword) {
		this.confirmPassword = confirmPasword;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}

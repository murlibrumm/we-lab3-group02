package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import at.ac.tuwien.big.we15.lab2.api.Avatar;

@Entity
public class Player {

	public enum Gender {
		male, female
	}

	private String firstname;
	
	private String lastname;
	
	private String birthday;
	
	private Gender gender;
	
	private Avatar avatar;
	
	@Id
	@Constraints.Required
	@Constraints.MinLength(4)
	@Constraints.MaxLength(8)
	private String username;
	
	@Constraints.Required
	@Constraints.MinLength(4)
	@Constraints.MaxLength(8)
	private String password;

	public Player() {
		
	}
	
	public Player (String firstname, String lastname, String birthday, Gender gender, 
			Avatar avatar, String username, String password) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthday = birthday;
		this.gender = gender;
		this.avatar = avatar;
		this.username = username;
		this.password = password;
	}
	
	/*public List<ValidationError> validate() {
		List<ValidationError> errors = null;
		if (!Character.isUpperCase(name.charAt(0))) {
			errors = new ArrayList<ValidationError>();
			errors.add(new ValidationError("name",
					"Must start with upper case letter"));
		}
		return errors;
	}*/

	public String getName() {
		return username;
	}

	public void setName(String username) {
		this.username = username;
	}
	
	public Avatar getAvatar() {
		return avatar;
	}
	
	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	/*public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}*/
	
}

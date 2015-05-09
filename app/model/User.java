package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import at.ac.tuwien.big.we15.lab2.api.Avatar;

@Entity
public class User implements at.ac.tuwien.big.we15.lab2.api.User {

	public enum Gender {
		male, female
	}

	private String avatarid;

	private String firstname;
	
	private String lastname;
	
	private String birthday;
	
	private Gender gender;
	
	private Avatar avatar;
	
	@Id
	@Constraints.Required
	@Constraints.MinLength(4)
	@Constraints.MaxLength(8)
	private String name;
	
	@Constraints.Required
	@Constraints.MinLength(4)
	@Constraints.MaxLength(8)
	private String password;

	public User() {
		
	}
	
	public User (String firstname, String lastname, String birthday, Gender gender, 
			String avatarid, String name, String password) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthday = birthday;
		this.gender = gender;
		this.name = name;
		this.password = password;
		this.avatarid = avatarid;
		this.avatar = Avatar.getAvatar(avatarid);
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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	// Getter & Setter must exist for DataBindings
	//http://stackoverflow.com/questions/16860046/playframework-jsr-303-validated-field-does-not-have-a-corresponding-accessor-f
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Avatar getAvatar() {
		return avatar;
	}

	public String getAvatarid(){ return avatarid;}
	
	@Override
	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public void setAvatarid(String avatarid){ this.avatarid = avatarid; }

	/*public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}*/
	
}

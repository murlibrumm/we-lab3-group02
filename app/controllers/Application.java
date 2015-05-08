package controllers;

import model.User;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.i18n.Messages;
import views.html.*;

public class Application extends Controller {

    public static Result showLogin() {
    	return ok(authentication.render(Form.form(User.class), null));
    }
    
    public static Result showRegistration() {
    	return ok(registration.render(Form.form(User.class)));
    }
    
    public static Result authenticate() {
    	return null;
    }

    public static Result registration() {
    	return null;
    }

    public static Result startJeopardy() {
    	return null;
    }
    
    public static Result checkAnswer() {
    	return null;
    }

    public static Result chooseQuestion() {
    	return null;
    }

    public static Result showWinner() {
    	return null;
    }
    
    /*public static Result newPet() {
		return ok(petform.render(Form.form(Pet.class)));
	}
	
	@Transactional
	public static Result createPet() {
		Form<Pet> form = Form.form(Pet.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(petform.render(form));
		} else {
			Pet pet = form.get();
			JPA.em().persist(pet);
			return redirect(routes.Pets.list());
		}
	}*/

}

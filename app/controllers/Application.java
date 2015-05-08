package controllers;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import model.Player;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.i18n.Messages;
import views.html.*;

public class Application extends Controller {

    public static Result showLogin() {
    }
    
    public static Result showRegistration() {
    	return ok(registration.render(Form.form(Player.class), null));
    }
    
    public static Result authenticate() {
    	return null;
    }

    public static Result registration() {
    	/*EntityManager em = JPA.em();
		Form<Player> registerForm = Form.form(Player.class).bindFromRequest();
		if (registerForm.hasErrors() || userExists(registerForm.data().get("name"))) {
			//return badRequest(registration.render(registerForm, Messages.get("registration.error")));
			return badRequest(registration.render(registerForm, null));
		} else {
			Player p = registerForm.get();
			em.persist(p);
			return redirect(routes.Application.authenticate());
		}*/
    	return null;
    }
    
    /**
	 * called by registration(), checks if User exists in Persistence
	 */
	private static boolean userExists(String name) {
		/*EntityManager em = JPA.em();
		TypedQuery<Player> query = em.createQuery(
				"SELECT u from User u WHERE u.name = :name", Player.class);
		try {
			query.setParameter("name", name).getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}*/
		return false;
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

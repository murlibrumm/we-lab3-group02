package controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import at.ac.tuwien.big.we15.lab2.api.Avatar;
import at.ac.tuwien.big.we15.lab2.api.JeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.JeopardyGame;
import at.ac.tuwien.big.we15.lab2.api.impl.PlayJeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleJeopardyGame;
import model.User;
import play.*;
import play.cache.Cache;
import play.data.Form;
import play.data.validation.ValidationError;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.i18n.Messages;
import views.html.*;

public class Application extends Controller {

    public static Result showLogin() {
    	return ok(authentication.render(Form.form(User.class)));
    }
    
    public static Result showRegistration() {
		return ok(registration.render(Form.form(User.class)));
    }
    
    @Transactional
    public static Result authenticate() {
    	Form<User> authenticationForm = Form.form(User.class).bindFromRequest();
    	User u = getUserFromPersistence(authenticationForm.data().get("name"));
		if (authenticationForm.hasErrors() || u == null) {
			// Wrong Username
			//return badRequest(registration.render(registerForm, Messages.get("registration.error")));
			return badRequest(authentication.render(authenticationForm));
		} else {
			if (u.getPassword().equals(authenticationForm.data().get("password"))) {
				// Store Username in Session & start game
				session("user", authenticationForm.data().get("name"));
				return redirect(routes.Application.startJeopardy());
			} else {
				// Wrong Password
				return badRequest(authentication.render(authenticationForm));
			}
		}
    }

    @Transactional
    public static Result registration() {
    	EntityManager em = JPA.em();
		Form<User> registrationForm = Form.form(User.class).bindFromRequest();
		if (registrationForm.hasErrors()  || (getUserFromPersistence(registrationForm.data().get("name")) != null )) {
			/*String errorMsg = "";
			java.util.Map<String, List<play.data.validation.ValidationError>> errorsAll = registrationForm.errors();
			for (String field : errorsAll.keySet()) {
				errorMsg += field + " ";
				for (ValidationError error : errorsAll.get(field)) {
					errorMsg += error.message() + ", ";
				}
			}
			System.out.println(errorMsg); */
			//return badRequest(registration.render(registerForm, Messages.get("registration.error")));
			return badRequest(registration.render(registrationForm));
		} else {
			User u = registrationForm.get();
			if(u.getAvatarid() == null){
				System.out.println("Avatar Id ist null .-.");
			}
			u.setAvatar(Avatar.getAvatar(u.getAvatarid()));
			em.persist(u);
			return redirect(routes.Application.authenticate());
		}
    }
    
    // called by registration() and authentication(), returns User from Persistence; null if User doesn't exist
    @Transactional
	private static User getUserFromPersistence(String name) {
		EntityManager em = JPA.em();
		return em.find(User.class, name);
	}

    @Transactional
    public static Result startJeopardy() {
    	JeopardyFactory factory = new PlayJeopardyFactory(Messages.get("json.file"));
    	EntityManager em = JPA.em();
    	// Create Game with Username from Session
    	// TODO Zeile produziert Error, weil User.Avatar == null
    	JeopardyGame game = new SimpleJeopardyGame(factory, em.find(User.class, session().get("user")));
    	// Store Game in Cache
    	Cache.set(session().get("user") + "game", game);
    	return ok(jeopardy.render());
    	//return redirect(routes.Application.showAllQuestions());
    }
    
    public static Result showAllQuestions() {
    	/*HashMap<>
    	List<Category> categories = getCategories();
		for(Category category : categories) 
			for(Question question : category.getQuestions()) {
				idToQuestion.put(question.getId(), question);
				openQuestions.add(question);
			}
    	//answerHumanQuestion(List<Integer> answerIds);
    	JeopardyGame game = (JeopardyGame) Cache.get(session().get("user") + "game");
		game.startNewRound();
		return ok(jeopardy.render());*/
    	return ok(index.render("DUMMY showAllQuestions"));
    }
    
    /*public static Result checkQuestionAnswer() {
    	return ok(index.render("DUMMY checkAnswer"));
    }*/

    public static Result showQuestion() {
    	JeopardyGame game = (JeopardyGame) Cache.get(session().get("user") + "game");
    	//game.chooseHumanQuestion(int questionId);
    	return ok(index.render("DUMMY showQuestion"));
    }

    public static Result showWinner() {
    	return ok(index.render("DUMMY showWinner"));
    }

}

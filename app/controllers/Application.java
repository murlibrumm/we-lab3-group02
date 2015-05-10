package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import at.ac.tuwien.big.we15.lab2.api.*;
import at.ac.tuwien.big.we15.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.impl.PlayJeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleJeopardyGame;
import model.User;
import play.*;
import play.cache.Cache;
import play.data.DynamicForm;
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
				return redirect(routes.Application.showAllQuestions());
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

	/*
    @Transactional
    public static Result startJeopardy() {
    	JeopardyFactory factory = new PlayJeopardyFactory(Messages.get("json.file"));
    	EntityManager em = JPA.em();

		JeopardyGame game;
		if(Cache.get(session().get("user")+"game") != null){
			game = (JeopardyGame) Cache.get(session().get("user")+"game");
		} else {
			// Create Game with Username from Session
			game = new SimpleJeopardyGame(factory, em.find(User.class, session().get("user")));
			// Store Game in Cache
			Cache.set(session().get("user") + "game", game);
		}

		return redirect(routes.Application.showAllQuestions());
    }
	**/

	@Transactional
    public static Result showAllQuestions() {

		JeopardyFactory factory = new PlayJeopardyFactory(Messages.get("json.file"));
		EntityManager em = JPA.em();

		JeopardyGame game;
		if(Cache.get(session().get("user")+"game") != null){
			game = (JeopardyGame) Cache.get(session().get("user")+"game");
		} else {
			// Create Game with Username from Session
			game = new SimpleJeopardyGame(factory, em.find(User.class, session().get("user")));

			// Store in Cache
			Cache.set(session().get("user") + "game", game);
		}

    	//answerHumanQuestion(List<Integer> answerIds);
    	//JeopardyGame game = (JeopardyGame) Cache.get(session().get("user") + "game");
		//game.startNewRound();
		//return ok(jeopardy.render());
    	return ok(jeopardy.render(game));
    }
    
    /*public static Result checkQuestionAnswer() {
    	return ok(index.render("DUMMY checkAnswer"));
    }*/

    public static Result showQuestion() {
    	JeopardyGame game = (JeopardyGame) Cache.get(session().get("user") + "game");

		DynamicForm dynamicForm = Form.form().bindFromRequest();
		int selected = Integer.valueOf(dynamicForm.get("question_selection"));
		game.chooseHumanQuestion(selected);

    	return ok(question.render(game));
    }

    public static Result showWinner() {
    	return ok(index.render("DUMMY showWinner"));
    }

}

package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//		app.test();
		app.launch();
	}

//	private void test() {
//    Film film = db.findFilmById(100);
//    if(film != null)
//    System.out.println(film);
//    else 
//    System.out.println("id not found");
//    Actor actor = db.findActorById(20);
//    if(actor!= null)
//    	System.out.println(actor);
//    else
//    	System.out.println("id not found");
//    List<Actor> actorList = db.findActorsByFilmId(100);
//    for (Actor actor2 : actorList) {
//		System.out.println(actor2);
//	}
//		List<Film> filmsQueryResult = db.findFilmByKeyword("ab");
//		if (!filmsQueryResult.isEmpty()) {
//
//			for (Film film : filmsQueryResult) {
//				System.out.println(film);
//
//			}
//		} else
//			System.out.println("no results found");
//	}

	private void launch() {
		boolean continueApp;
		Scanner input = new Scanner(System.in);
		do {
			continueApp = startUserInterface(input);
		} while (continueApp);
		input.close();

	}

	private boolean startUserInterface(Scanner scanner) {
		boolean continueApp = true;
		System.out.println("1. Look up film by id\n2. Look up film by keyword\n3. Exit");
		String choice = scanner.next();

		switch (choice) {
		
		case "1":
			executeSearchById(scanner);
			break;
			
		case "2":
			executeSearchByKeyword(scanner);
			break;
			
		case "3":
			System.out.println("Goodbye");
			continueApp = false;
			break;
			
		default:
			System.out.println("Invalid entry");
			break;
		}
		return continueApp;

	}

	private void executeSearchById(Scanner input) {
		int idChoice = 0;
		Film searchResult = null;
		System.out.println("Enter Film ID# (ex: 123)");
		
		try {
			idChoice = input.nextInt();
			searchResult = db.findFilmById(idChoice);
		} catch (Exception e) {
			System.out.println("Invalid entry");
		} finally {
			input.nextLine();
		}
		
		if (searchResult == null)
			System.out.println("No result found");
		else
			System.out.println(searchResult);
	}

	private void executeSearchByKeyword(Scanner input) {
		System.out.println("Enter search keyword: ");

		List<Film> filmsQueryResult = db.findFilmByKeyword(input.next());
		if (!filmsQueryResult.isEmpty()) {

			for (Film film : filmsQueryResult) {
				System.out.println(film);

			}
		} else
			System.out.println("No results found");
	}

}

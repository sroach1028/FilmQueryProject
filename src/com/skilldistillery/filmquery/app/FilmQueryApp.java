package com.skilldistillery.filmquery.app;

import java.util.InputMismatchException;
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
//	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();

	}

	private void startUserInterface(Scanner scanner) {
		boolean continueApp = true;

		do {
			System.out.println("1. Look up film by id\n"
								+ "2. Look up film by keyword\n"
								+ "3. Exit");
			
			String choice = scanner.next();

			switch (choice) {

			case "1":
				executeSearchById(scanner);
				break;

			case "2":
				executeSearchByKeyword(scanner);
				break;

			case "3":
				System.out.println("Goodbye...");
				continueApp = false;
				break;

			default:
				System.out.println("Invalid entry");
				break;
			}
		} while (continueApp);

	}

	private void executeSearchById(Scanner input) {
		Film searchResult = null;
		System.out.println("Enter Film ID# (ex: 123)");

		try {
			searchResult = db.findFilmById(input.nextInt());
		} catch (InputMismatchException e) {
			System.out.println("Invalid entry. Ids are in number format");
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

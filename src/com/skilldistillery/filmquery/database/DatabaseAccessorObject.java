package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		List<Actor> cast = new ArrayList<>();
		
		String user = "student";
		String pass = "student";
		String sql = "SELECT * FROM film join language on film.language_id = language.id WHERE film.id = ?";
		
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet res = stmt.executeQuery();
			
			if(res.next()) {
					cast = findActorsByFilmId(filmId);
					film = new Film(res.getInt("id"), res.getString("title"), 
							res.getString("description"), res.getInt("release_year"), 
							res.getInt("language_id"), res.getInt("rental_duration"), 
							res.getInt("length"), res.getDouble("replacement_cost"), 
							res.getString("rating"), res.getString("special_features"), 
							cast, res.getString("language.name"));
				}
			res.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			return film;
	} 
		

	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		Film film = null;
		List<Actor> cast = new ArrayList<>();
		List<Film> filmList = new ArrayList<>();
		String user = "student";
		String pass = "student";
		String sql = "select * FROM film join language on film.language_id = language.id WHERE film.title like ? or film.description like ?";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+keyword+"%");
			stmt.setString(2, "%"+keyword+"%");
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				cast = findActorsByFilmId(res.getInt("film.id"));
				film = new Film(res.getInt("id"), res.getString("title"), 
						res.getString("description"), res.getInt("release_year"), 
						res.getInt("language_id"), res.getInt("rental_duration"), 
						res.getInt("length"), res.getDouble("replacement_cost"), 
						res.getString("rating"), res.getString("special_features"), 
						cast, res.getString("language.name"));
						filmList.add(film);
			}
			
			res.close();
			stmt.close();
			conn.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return filmList;
	}
	
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actorList = new ArrayList<>();;
		
		String user = "student";
		String pass = "student";
		String sql = "SELECT * FROM actor "
				+ "join film_actor on actor.id = film_actor.actor_id "
				+"join film on film.id = film_actor.film_id "
				+ "WHERE film.id = ?";
		
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet res = stmt.executeQuery();
			
			while (res.next())
				actorList.add(new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name")));
			
			res.close();
			stmt.close();
			conn.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}		
		return actorList;
	}
	
	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		String sql = "SELECT * FROM actor WHERE id = ?";
		
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet res = stmt.executeQuery();
			
			if (res.next())
				actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
			
			res.close();
			stmt.close();
			conn.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}
}

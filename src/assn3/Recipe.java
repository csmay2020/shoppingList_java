package assn3;

import java.util.HashMap;
import java.util.Map;

/*
 * File name:Recipe.java
 * Author: Shan Cai 
 * ID:040469755 
 * Course: CST8284-OOP
 * Assignment: Assignment 03 
 * Due Date: April 6 ,2025 
 * Professor: Reg Dyer 
 */
/**
 * Recipe defines hashMap as the container for bread name and its ingredient
 * recipe. Also, it defines the hashMap of customers choices to store selected 
 * type of bread and quantity of bread
 * 
 * @author Shan Cai
 * @since Mar 25,2025
 */
public class Recipe {

	/** breadName stores all name from different type of bread */
	private String breadName;
	/** hashMap recipes stores ingredients of each bread */
	private Map<String, Double> recipes = new HashMap<>();
	/** hashMap fullRecipes stores full information of one kind of bread */
	private Map<String, Map<String, Double>> fullRecipes = new HashMap<>();
	/** selectedBread will stores users choices */
	private Map<Integer, Integer> selectedBread = new HashMap<>();
	/** recipeNumber stores the users input of which type of bread */
	private int recipeNumber;
	/** quantity stores the users input of how many bread they need */
	private int quantity;

	/**
	 * Default constructor
	 */
	public Recipe() {
	}

	/**
	 * Overload constructor with bread name and hashMas recipes The two parameter
	 * will be stored into fullRecipes
	 * 
	 * @param breadName, the name of bread
	 * @param recipes,   the hashMap stores bread ingredients of recipes.
	 */
	public Recipe(String breadName, Map<String, Double> recipes) {
		this.breadName = breadName;
		this.recipes = recipes;
		this.fullRecipes.put(breadName, recipes);
	}

	/**
	 * Getter of BreadName
	 * 
	 * @return breadName
	 */
	public String getBreadName() {
		return breadName;
	}

	/**
	 * setter of BreadName
	 * 
	 * @param breadName the name of bread
	 */
	public void setBreadName(String breadName) {
		this.breadName = breadName;
	}

	/**
	 * getter of hashMap Recipes
	 * 
	 * @return hashMap recipes
	 */
	public Map<String, Double> getRecipes() {
		return recipes;
	}

	/**
	 * setter of hashMap Recipes
	 * 
	 * @param recipes the hashMap stores recipes
	 */
	public void setRecipes(Map<String, Double> recipes) {
		this.recipes = recipes;
	}

	/**
	 * getter of hashMap of fullRecipes
	 * 
	 * @return hashMap
	 */
	public Map<String, Map<String, Double>> getFullRecipes() {
		return fullRecipes;
	}

	/**
	 * setter of fullRecipes
	 * 
	 * @param fullRecipes, stores the name of bread and the ingredients accordingly
	 */
	public void setFullRecipes(Map<String, Map<String, Double>> fullRecipes) {
		this.fullRecipes = fullRecipes;
	}

	/**
	 * getter of recipeNumber.
	 * 
	 * @return recipeNumber
	 */
	public int getRecipeNumber() {
		return recipeNumber;
	}

	/**
	 * setter of recipeNumber
	 * 
	 * @param recipeNumber, it is stores the list number of each type of bread
	 */
	public void setRecipeNumber(int recipeNumber) {
		this.recipeNumber = recipeNumber;
	}

	/**
	 * getter of quantity
	 * 
	 * @return quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * setter of quantity. re-calculate if user modified the quantity of the bread.
	 * 
	 * @param quantity, it is to store the quantity of bread that users chose.
	 */
	public void setQuantity(int quantity) {
		//if the type of bread has been chosen, it will re-calculate the quantity again.
		//otherwise, it will store the quantity and recipeNumbe directly. 
		if (selectedBread.containsKey(recipeNumber)) {
			int originalQuantity = selectedBread.get(recipeNumber);
			selectedBread.put(recipeNumber, originalQuantity + quantity);
		} else {
			selectedBread.put(recipeNumber, quantity);
		}
	}

	/**
	 * Getter of selectedBread save the customers choices, the type of bread and the
	 * quantity
	 * 
	 * @return selectedBread
	 */
	public Map<Integer, Integer> getSelectedBread() {
		return selectedBread;
	}

	/**
	 * Setter of selectedBread
	 * 
	 * @param selectedBread hashMap save the customers choices, the type of bread
	 *                      and the quantity
	 */
	public void setSelectedBread(Map<Integer, Integer> selectedBread) {
		this.selectedBread = selectedBread;
	}

}

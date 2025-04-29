package assn3;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * File name:RecipeManagerTest.java
 * Author: Shan Cai 
 * ID:040469755 
 * Course: CST8284-OOP
 * Assignment: Assignment 03 
 * Due Date: April 6 ,2025 
 * Professor: Reg Dyer 1
 * 
 */

/**
 * This class is to create menu and prompt users input their options
 * 
 * @author Shan Cai
 * @since Mar25,2025
 */

public class RecipeManagerTest {

	/**
	 * Create menu and prompt user input
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Recipe r = new Recipe();
		RecipeManager rm = new RecipeManager();
		int choice;
		try (Scanner sc = new Scanner(System.in);) {
			do {
				//do while loop starts with menu of options
				System.out.println("Welcome to Shan recipe manager.");
				System.out.println("Please select one of the following options:");
				System.out.println("1. Show available recipes.");
				System.out.println("2. Create Shopping List.");
				System.out.println("3. Print Shopping List.");
				System.out.println("4. Quit Program.");
				System.out.println("0. Reprint this menu.");
				System.out.print("Please enter your choice: ");

				choice = sc.nextInt();

				switch (choice) {
				case 1:
					//invoke displayRecipeList to display list of bread name
					rm.displayRcipeList(r);
					break;
				case 2:
					System.out.print("Which bread would you like?");
					//input for choosing number that presents the specific type of bread
					int input1 = sc.nextInt();
					sc.nextLine();
					System.out.print("How much of this bread would you like?");
					//input for the quantity of bread that customer wants
					int input2 = sc.nextInt();
					sc.nextLine();
					//set both inputs to variable of recipeNumber and quantity
					r.setRecipeNumber(input1);
					r.setQuantity(input2);
					break;
				case 3:
					rm.displayShoppingList(r);
					System.out.print("Do you want to save this list (Y/n)?");
					String input3 = sc.next();
					//input for choosing save or not save the file
					rm.setSaveChoice(input3);
					rm.saveList(r);
					break;
				case 4:
					break;
				case 0:
					break;
				default:
					System.out.print("Invalid choice. Please try again.");
				}
				//exit if choose #4
			} while (choice != 4);

			sc.close();
			//catch invalid input
		} catch (InputMismatchException e) {
			System.out.println("Invalid input: " + e);
		}
	}
}

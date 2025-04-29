package assn3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RecipeManager handles all recipe-related operations including reading recipes,
 * displaying them, creating shopping lists, calculate the amount of each ingredient
 * that need in the shopping list and saving lists to files.
 * 
 * @author Shan Cai
 * @since Mar 25, 2025
 */
public class RecipeManager {

	/** saveChoice to stores answers if users want to save the shoppingList file */
	private String saveChoice;
	/** allRecipes will be assigned by fullRecipes */
	private Map<String, Map<String, Double>> allRecipes = new HashMap<>();

	/**
	 * getter of saveChoice
	 * 
	 * @return saveChoice
	 */
	public String getSaveChoice() {
		return saveChoice;
	}

	/**
	 * getter of saveChoice
	 * 
	 * @param saveChoice, it stores users choice if wants to save the shopping list.
	 */
	public void setSaveChoice(String saveChoice) {
		this.saveChoice = saveChoice;
	}


    /**
     * Reads recipe information from text file and stores it in the Recipe object.
     * 
     * @param r The Recipe object to store the read data
     */
	
	public void readRecipe(Recipe r) {
	    try (Scanner sc = new Scanner(new File("recipelist.txt "));){
	        
	    	String breadName = r.getBreadName();
	        Map<String, Double> ingredient = null;
	        
	        while (sc.hasNextLine()) {
	        	//define the line read by scanner and trim the space
	            String line = sc.nextLine().trim();
	            //Regular express for search pattern;
	            Pattern pattern = Pattern.compile("^Recipe\\s+(.*)$");
	            //matcher to match line by the defined regular express.
	            Matcher matcher = pattern.matcher(line);
	            double amount;
	            if (matcher.find()) {
	                breadName = matcher.group(1).trim();
	                ingredient = new HashMap<>();  // Create new map for each recipe
	            } else if (!line.isEmpty()) {      //if line is not empty
	            	//read each line with different starts, parse the ingredient amount and store into hashMap ingredient.
	                if (line.startsWith("eggs")) {   
	                    amount = Double.parseDouble(line.substring(4).trim());
	                    ingredient.put("eggs", amount);
	                } else if (line.startsWith("yeast")) {
	                    amount = Double.parseDouble(line.substring(5).trim());
	                    ingredient.put("yeast", amount);
	                } else if (line.startsWith("flour")) {
	                    amount = Double.parseDouble(line.substring(5).trim());
	                    ingredient.put("flour", amount);
	                } else if (line.startsWith("sugar")) {
	                    amount = Double.parseDouble(line.substring(5).trim());
	                    ingredient.put("sugar", amount);
	                } else if (line.startsWith("butter")) {
	                    amount = Double.parseDouble(line.substring(6).trim());
	                    ingredient.put("butter", amount);
	                }
	            }
	            // Store the recipe after processing each line
	            if (ingredient != null) {
	                r.getFullRecipes().put(breadName, new HashMap<>(ingredient));
	            }
	        }
	        sc.close();
	        //handle with 2 exception
	    } catch (FileNotFoundException e) {
	        System.out.println("Error: can not find the file, please check the name and path.");
	    } catch (NumberFormatException e) {
	        System.out.println("Error: can not get the value");
	    }
	}

    /**
     * Displays the list of available recipes.
     * 
     * @param r The Recipe object to store the read data
     */
    public void showRecipeList(Recipe r) {
        int index = 1;
        System.out.println("Available Recipes:");
        //loop the full recipe to get the bread name for displaying
        for (String recipeName : r.getFullRecipes().keySet()) {
            System.out.println(index++ + ". " + recipeName);
        }
        this.allRecipes = r.getFullRecipes();
    }

    /**
     * Creates a new shopping list file.
     */
    public void createFile() {
    	//create shopping list
        File file = new File("shoppingList.txt");
        //handle IOException
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error: Can create the file. Please check the path.");
        }
    }

    /**
	 * displayRcipeList with recipeManage and Recipe as parameter invoke
	 * showRecipeList in recipeManage. Also, create shoppingList.txt in this method.
	 * Assign fullRecipes to hashMap to allRecipes.
	 * 
	 * @param r The Recipe object to store the read data
	 */
	public void displayRcipeList(Recipe r) {
		readRecipe(r);
		showRecipeList(r);
		createFile();
	}
	
	/**
	 * listCreate() is to create a shopping list.
	 * 
	 * @return toStringisEmpty()
	 * 
	 * @param r The Recipe object to store the read data
	 */
	public String listCreate(Recipe r) {
		 if (r.getSelectedBread().isEmpty()) {
		        return "No recipes selected.";
		    }
		// String builder is preparing for outputting list
		StringBuilder results = new StringBuilder();
		Map<String, Double> totalIngredients = new HashMap<>();

		// loop users selection to get bread name and ingredients hashMap
		for (Map.Entry<Integer, Integer> entry : r.getSelectedBread().entrySet()) {
			int recipeNum = entry.getKey();
			int quantity = entry.getValue();
			// when quantity of selected bread is greater than 0,
			// then append output to String builder "results" and calculate amount of
			// ingredients
			if (quantity > 0) {
				// get bread name from hashMap allRecipes
				String breadName = (String) allRecipes.keySet().toArray()[recipeNum - 1];
				// append to output to results
				results.append(quantity).append(" ").append(breadName).append(" loaf/loaves.\n");
				// new hashMap to get the value of the key "breadName" from hashMap allRecipes.
				Map<String, Double> ingredients = allRecipes.get(breadName);
				// loop the hashMap ingredients and calculate the amount of ingredients.
				for (Map.Entry<String, Double> ingEntry : ingredients.entrySet()) {
					String name = ingEntry.getKey();
					int amount = (int)(ingEntry.getValue() * quantity);
					totalIngredients.merge(name, (double)amount, Double::sum);
				}
			}
		}

		// append ingredients to list
		if (!totalIngredients.isEmpty()) {
			results.append("\nYou will need a total of:\n");
			for (Map.Entry<String, Double> ingEntry : totalIngredients.entrySet()) {
				String name = ingEntry.getKey();
				//casting value of ingredients to be int
				double value = (ingEntry.getValue());
				int amount = (int)value;
				//if amount equals to 0, nothing is printing
				if(amount==0) {	
				} else if (amount !=0 && name.equals("eggs")) {
					results.append(amount).append(" egg(s)\n");
				}else {
					results.append(amount).append(" grams of ").append(name).append("\n");
				}
			}
		}

		return results.toString();
	}

	/**
	 * displayShoppingList() is to display the shopping list.
	 * 
	 * @param r The Recipe object to store the read data
	 */
	
	public void displayShoppingList(Recipe r) {
		
		String outputList = listCreate(r);
		System.out.println(outputList);
	}

	/**
	 * saveList() is to write shopping list to the file and save. handle IOException
	 * exception
	 * 
	 * @param r The Recipe object to store the read data
	 */
	
	public void saveList(Recipe r){
		//make both 'Y' and 'y' as valid input
		if (saveChoice.equalsIgnoreCase("Y")) {
			//write shopping list in to the file
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("shoppingList.txt"))) {
				String outputList = listCreate(r);
				writer.write(outputList);
			} catch (FileNotFoundException e) {
				System.out.println("Error: Can not find 'src/shoppingList.txt'");
			}
			  catch (IOException e) {
				 System.out.println("Error: Can not save 'src/shoppingList.txt'");
			 }
			
		}
	}

}
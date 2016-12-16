# What should I eat?
Hungry and have food in the fridge lying around, but have no inspiration what to do with it?
'What Should I Eat?' solves that problem for you. 
You only have to add the ingredients you have to your ingredients list and search for recipes to make with them.

The user can login using a google account.

![whatsapp image 2016-12-16 at 15 22 18 10](https://cloud.githubusercontent.com/assets/17069785/21266105/e02f836c-c3a4-11e6-90ac-000223e52b47.jpeg)

Ingredients can be added to the ingredient list. Ingredients can be selected and deselected by tapping on them.
When selected, an ingredient will be included in the search query.

![whatsapp image 2016-12-16 at 15 22 18 1](https://cloud.githubusercontent.com/assets/17069785/21266144/09137c7a-c3a5-11e6-9b8d-d82ddd1f4077.jpeg)

The results are shown. A user can tap an recipe to view further instructions.

![whatsapp image 2016-12-16 at 15 22 18 2](https://cloud.githubusercontent.com/assets/17069785/21266210/53761dc2-c3a5-11e6-97a6-fd20a8bbd9d0.jpeg)

All ingredients of a recipe are shown. The user can tap on the ingredients to add them to the grocery list.

![whatsapp image 2016-12-16 at 15 22 18 4](https://cloud.githubusercontent.com/assets/17069785/21266254/84494ef6-c3a5-11e6-9fe7-2cf0af8b629c.jpeg)

The directections of the recipe can be viewed on the website, that will be shown in-app.

![whatsapp image 2016-12-16 at 15 22 18 6](https://cloud.githubusercontent.com/assets/17069785/21266284/b99bf13a-c3a5-11e6-9caf-16e33e06ccbc.jpeg)

The grocerylist is updated since the user has tapped on ingredients in the recipe. The user can also
manually add ingredients to the grocerylist.

![whatsapp image 2016-12-16 at 15 22 18 5](https://cloud.githubusercontent.com/assets/17069785/21266318/f250fb6a-c3a5-11e6-987c-c6cc03c6a3b8.jpeg)

The menu contains preferences for allergies and diets, an about section and the option to log out. 

![whatsapp image 2016-12-16 at 15 22 18 7](https://cloud.githubusercontent.com/assets/17069785/21266382/3e32d33c-c3a6-11e6-8d92-4abab9092b83.jpeg)

The allergy and diet preferences can be set. The search results of the recipes will be adapted to it.

![whatsapp image 2016-12-16 at 15 22 18 8](https://cloud.githubusercontent.com/assets/17069785/21266402/5eddb6ec-c3a6-11e6-98ab-cc830c3b5234.jpeg)

![whatsapp image 2016-12-16 at 15 22 18 9](https://cloud.githubusercontent.com/assets/17069785/21266404/5fd9524a-c3a6-11e6-880a-fb10a0cdcabe.jpeg)

# The feedback
Eisen:

        Live open API CHECK
        
        HTTP requests CHECK
        
        SharedPrefs TODO
        
        Firebase CHECK/TODO (firebaseAuth nog niet aanwezig)
        
        Organized code CHECK
        
        Documented code TODO   (README.md ontbreekt)

Tips/Tops:

        Erg nette scheiding van taken
  
        Goed leesbare code
  
        Consistente naamgeving
  
        Voeg dus nog ff een beschrijving toe van het doel van je app!:-)
  

MainActivity:

        Goede naamgevingen 
         
        Genoeg comments
         
        Indentatie is goed
         
        onCancelled is leeg
         
        Smaken verschillen maar hier en daar evt. wat overbodige enters
  
        Je onCreate heeft een mooi natuurlijke opdeling, maar is te lang (ong. 140 lines) , maak hier meerdere methoden van (bv vanaf de database references)

RecipeActivity / ViewDirections:

    indentatie goed
    
    geef nog even een comment over het general purpose van deze activity

SearchAsincTask:

    Deel onPostExecute op in een paar methoden (suggestie alles uit de else{ <VOID> } van  (recipe_results.length == 0))

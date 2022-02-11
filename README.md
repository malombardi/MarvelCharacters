# MarvelCharacters

This is a list->detail app showing Marvel's characters.

Using [Marvel's API](https://developer.marvel.com/docs) the initial requirement was to have a list of characters and navigate to detail for each character. <br/>
As I have the freedom to add new functionality on this, I'll be adding these functionality:
1. List of characters
2. Detail screen that will contain 
    * Image
    * Name and Description (if available)
    * A button that on click will show a List of comics where that character can be found (if available)
    * A button that on click will open a dialog with a web view containing the bio (if available) 
3. Search -> The user will be able to search a particular character once the input has at least 3 letters


For the design part you can go to [this link](https://github.com/malombardi/MarvelCharacters/wiki/Design-specs)

***

<h3> Technical part </h3>

First of all to be able to compile the project is a must have a file in the root level named "keys.properties" and it must contain these two lines:
public_key=<YOUR_PUBLIC_API_KEY>
private_key=<YOUR_PRIVATE_API_KEY>
Those keys can be gathered from Marvel's developer page

<h5> Package strutcture </h5>

* Domain
* Data
* Presentation

The reason is to keep the code as clean as possible.
Domain layer contains the logic with clases and interfaces needed
Data layer has the actual implementation of the domain layer interfaces
Presentation contains the ViewModels, Activity and Fragments 

The architecture chosen was MVVM, the reasons for that are:
* The logic is decuple from the ui (the only activity and fragments)
* The ViewModel is easier to test because they not contain the view
* One ViewModel can be used on different Views
* One View can contain more than one ViewModel

I tried to follow SOLID principles and to do that I used Hilt for Dependency Injection
Other Libraries were Room (for DB), Glide (for images), Retrofit and Gson (for networking), Hilt (for DI), Turbine (for testing)

Turbine makes easier testing flows.

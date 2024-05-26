# 🎮 Hangman Game: A Classic Word-Guessing Challenge
![Static Badge](https://img.shields.io/badge/version-1.0.0-blue) ![Static Badge](https://img.shields.io/badge/java-22.0.1-blue) ![Static Badge](https://img.shields.io/badge/Apache%20Maven-3.9.6-blue) ![Static Badge](https://img.shields.io/badge/JavaFX-17.0.11-blue)

## 📝 Introduction
This application presents a traditional Hangman Game, built with JavaFX. Hangman stands as a timeless word-guessing game that puts players to the test as they strive to unravel a concealed word, one letter at a time.

## 📚 Table of Contents
1. 🕹️ [Gameplay Instructions](#️-gameplay-instructions)
    - [Overview](#overview)
    - [Creation of a New Dictionary](#creation-of-a-new-dictionary)
    - [Selection of an Existing Dictionary](#selection-of-an-existing-dictionary)
2. 🚀 [Game Execution](#-game-execution)
3. 🛠️ [Project Compilation](#️-project-compilation)

## 🕹️ Gameplay Instructions
### Overview
The game begins with players selecting a dictionary of their choice. A secret word is then chosen from this dictionary, represented by blank spaces. Players embark on the guessing journey, suggesting letters they believe are part of the secret word.

Correct guesses result in the corresponding blanks being filled with the guessed letter. Incorrect guesses, however, initiate the drawing of a hangman. The game concludes when either the secret word is fully revealed or the hangman is completely drawn, depending on which occurs first.

This engaging and educational game serves as an enjoyable platform for enhancing players’ vocabulary and spelling abilities. Players can enjoy unlimited rounds using the same dictionary or switch to a different one as they wish. The game also features a scoring system, enabling players to accumulate points over multiple rounds.

### Creation of a New Dictionary
The recommended method for creating a dictionary is by using the built-in request to the OpenLibrary Works API. Follow these steps to create a new dictionary:
1. Select the `Application > Create` menu item.
2. Choose your preferred book from the Open Library website. **Please note that only descriptions in English are currently supported.** Extract the OpenLibray ID from the corresponding URL. The value is found between `/works` and `/title` sections of the URL. For more information, refer to the [Open Library Works API](https://openlibrary.org/dev/docs/api/books).
3. A dialog box will appear, prompting you to enter an OpenLibrary ID and a suitable dictionary name of your choice. Both fields are mandatory.

Upon completion, the application will retrieve the `description` field from the JSON-formatted response of the API.

For example, if you have this URL for your book: `https://openlibrary.org/works/OL82586W/Harry_Potter_and_the_Deathly_Hallows?edition=key%3A/books/OL39443624M`, the OpenLibrary ID is the part between `/works/` and the next `/`. So in this case, the OpenLibrary ID is `OL82586W`.

#### Prerequisites for Dictionary Creation
The following criteria were considered for the creation of the dictionary:
* Words containing “_” characters or digits are excluded. **This is the minimum precaution taken to ensure the validity of a dictionary.** However, this validation is not exhaustive and there may be spelling errors.
* **At least 20% of the words** found must have **a length of 9 characters or more**.
* **A minimum of 20 valid words** are required.

If any of these prerequisites are not met, the dictionary will not be created. Otherwise, a `Hangman-Game/medialab/<your-chosen-dictionary-name>.txt` file will be created.

### Selection of an Existing Dictionary
To select a dictionary, follow these steps:
1. Navigate to the `Application > Load` menu item and click on it.
2. A dialog box will appear. Click on the `Choose Dictionary` button within this box.
3. This will open the File Loader. Navigate to the `Hangman-Game/medialab/` folder and select your preferred dictionary.
4. After selecting the dictionary, click on the `Update Dictionary` button to set your chosen dictionary as the active one.

The dictionary displayed in the main window of the application will automatically update to reflect your selection. 

Afterwards, the player should close the dialog box and return to the main window to start the game! If a player wishes to view statistics about the current dictionary, they can do so by clicking on the `Details > Dictionary` menu item.

## 🚀 Game Execution
To execute the game, ensure that Java and JavaFX are installed on your computer. Follow the steps below:

1. Download the JAR file from the Releases section of this repository.
2. Download the JavaFX libraries from [here](https://gluonhq.com/products/javafx/) based on your operating system and extract them to a known location on your computer.
3. Open a terminal/command prompt.
4. Navigate to the directory where you downloaded the JAR file.
5. Run the command:

	```shell
	$ java --module-path <path-to-javafx-lib> --add-modules javafx.controls,javafx.fxml -jar hangman-1.0.jar
	```

	Replace `<path-to-javafx-lib>` with the path to the extracted JavaFX libraries.

## 🛠️ Project Compilation
If you wish to compile the project from the source, ensure that Java and Maven are installed on your computer. Follow the steps below:

1. Clone the repository:
	```shell
	$ git clone https://github.com/alexandrosst/Hangman-Game
	```
2. Navigate to the project directory:
	```shell
	$ cd Hangman-Game
	```
4. Build the project:
	```shell
	$ mvn clean install
	```

	This will create a JAR file in the `Hangman-Game/target` directory that you can run using the instructions in the "Running the Game" section.
# streaming-service-oop-project

<div align="center"><img src="./readme.resources/giphy.gif" width="500px"></div>

## Overview
This project is a simplistic implementation of a streaming service like Netflix, HBO Max or HULU.
This project for me is the first contact with design patterns in OOP. The realisation of this project helped 
me a lot in consolidating the basic concepts of OOP and in observing when and where it is good to use design 
patterns or even necessary.

The streaming service has an improvised database of registered users and movies. To use the platform 
a user must log in or register. Then the logged in user has access to pages like: Movies, Upgrades.
On the movie page the user sees all the movies that are not banned in his country. From the Movie page
the user can select a displayed movie and purchase, watch, like or rate it.

## Project Structure

* src/
    
    * fileio/ - contains classes used to read data from the json files
    * action.classes - contains all the classes that helps us execute actions.
        * pages - contains all page types.
    * database.elements - contains the implementation of the database.
    * factory.strategy - contains the classes that implements the factory
      strategy pattern for the action execution.
    * output - contains a class that has functions for printing the output.
    * run.program - contain the class that initializes the database and 
      executes all the actions from the input.
    * checker/ - contains the checkstyle checker files
    * Main - The main class is the entry point of the program it gets
      an input file and writes the output of the program in the output file.
      Run Main to test the implementation from the IDE or from command line.
    * Test - The test class runs the checker on the implementation. 
* lib/ - libraries used
* checker - the package contains input files and the refs to check if the implementation works

## Classes and Design Pattern
Now I'll explain in more detail the core classes of the project.

### Databse. Singleton Pattern.
I implemented a simple database with a collection of users and movies. The implementations have some 
simple actions like adding a user or a movie to the database and finding a user by its 
username and password. I implemented the database with a singleton pattern because we should 
have just one instance of the database in all our program.

### Page Factory Strategy.
When implementing the project the problem that I encountered was that I had multiple pages with multiple
functionalities. First though was to create a Page factory and that's what I've done. The factory gets 
the name of the page to be created and creates it. The second problem was that many pages can execute
many actions. The solution was to use a sort of strategy pattern so we could swap the alorithm of 
execute action at runtime.

### Program.
This class contains the flow of the program and my small victory is that it's very easy to understand
it. First it initializes the database and gets the array of actions from input. Then it simply iterates
through the actions array and executes them. Here our factory strategy shines. invoke our doStrategy() function
that creates the right page and does the right action.

#### Trofim ALexandru, 322CD

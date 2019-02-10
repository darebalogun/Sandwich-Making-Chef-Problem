======= Sandwich-Making Chefs Problem =======
This program implements a solution to the infamous Sandwich-Making Chef problem, initially
published as the cigarette -smokers problem by S. Patil in 1971. In the system, there are 
three Chef threads and one Agent thread. There are also 3 ingredients: peanut butter, jam and bread.
Each Chef has infinite supply of one of the three inrgedients and no 2 chefs have the same ingredient.
Therefore to make a complete peanut butter jelly sandwich each chef needs 2 other ingredients.
The Agent has an infinite supply of all three ingredients and randomly places 2 of them on the table at 
any time. The chefs which has the missing ingredient then makes and consumes a sandwich.


Author: Dare Balogun 101062340
Version: Feb - 07 - 2019


======= Installation =======
1. Launch Eclipse-java-photon
2. Click File -> Open Projects From File System -> Archive
3. Select the zip file from the directory
4. Click Open -> FInish
5. On the package explorer panel on the left select 101062340_Assignment2 -> src -> (default package) -> Sandwich_Chef_Problem.java
6. Click Run As -> Java Application
7. View the results on the console
8. Program will run continuously so terminate it when you are satisfied



======= Files =======
******* Sandwich_Chef_Problem.java *******
This is the only source file required to run the program. This file contains the following classes:

a. Sandwich_Chef_Problem:
	Contains the main function and as such the main thread of control. It also has an enumeration 
	class that contains the three ingredients in the problem. 

b. Table:
	The table class models the table in the problem, it can only have 2 ingredients on it at any
	time and this is modeled with the ArrayList. The table also has synchonized objects get() and put().
	The agent calls put() to put 2 random ingredients on the table and the chefs call get() to retrieve them.
	Mutual exclusion and conditional synchronization is used to protect these methods and ensure they are only
	ran by one thread at a time and only the appropriate thread.

c. Agent:
	The agent class models the agent in the problem. It continuosly calls put to place 2 random objects
	on the table

d. Chef:
	The chef class has only one ingredient and continouly calls get() on the table. Only consuming a 
	sandwich if the 2 ingredients on the table complement the chefs ingredient.


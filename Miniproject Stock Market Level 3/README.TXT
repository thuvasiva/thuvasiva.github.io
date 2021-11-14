Student name: Thuvaragan Sivayoganathan
Student number: 200012355

Please complete this README file for your level 3 mini-project submission.

If your level 1/2 submission did not get full marks you MUST complete the second part of this template describing how and where your have improved your code so that it now meets the level 1 and 2 requirements. If your code still does not meet level 1 and 2 requirements then you will get 0 for level 3.

LEVEL THREE

GUI

In GUI.java line 143-215, when the button named 'Simulate 1 Day' is pressed, the method changeFinancialValueDaily() is called on all objects in the main ArrayList of the simulation called 'instruments'(line 11).
This causes the values of all FinancialInstrument objects to change and the new values are appended to the textarea t1 (changes can be seen north of the GUI window regarding this).
Furthermore, if an interest payment is due, this is added to the user's account, and events from the Events class may also be generated after this button press (details appended to textarea t3 or south of the GUI).
New moving average data is also generated after the button press although not displayed unless its respective button is pressed.

Exceptions

GUI.java line 237-248.
There is exception handling implemented to catch a custom exception called CashTradingException if one is thrown by the completePurchase() method (line 337-346).
Exception handling is important as without it, the program would terminate when the exception is thrown, which is very unhelpful for the user. The idea of the CashTradingException
is that it prevents the user from purchasing Cash objects and tells the user to purchase something else, not to terminate the program. And so, try-catch statements are used to catch this
exception and append to the screen the message on line 247. The reason for having the custom exception itself is to terminate the completePurchase() method without returning a value, as returning a value would interrupt the program flow.

Collections

GUI.java line 299.
A newly generated FinancialInstrument object is added to an ArrayList called instruments. This ArrayList is an instance variable of the GUI class, seen on line 11.
I have used the collection class ArrayList because I want to have a dynamic list of FinancialInstrument objects, allowing me to easily add a newly generated financial instrument by the method addInstrument() line 387.
In the level 2 version, I had it so that objects can only be replaced in the array upon request by the user, but now new objects can be added by the user to provide a larger set of assets to work with.
If I had used arrays, I would have to construct a new larger array each time I want to add a new object, and so the collection class makes it simpler.

File I/O

My code reads from 2 files called Instruments.txt and Account.txt, which are also written to by the program, after pressing the 'Quit For Now' button. (Both files are created at the start of the program if they don't exist and destroyed when the 'End Simulation' button is pressed.)
After this button press, the objects in the main ArrayList of instruments(line 11) are written to the file Instruments.txt using writeInstrumentsToFile() (line 435) and the Account object in the simulation is also written to the file Account.txt using writeBalanceToFile() method (line 450).
When the program is restarted again, these files are read using an ObjectInputStream object (lines 108 and 128) leading to each FinancialInstrument object being added to the main ArrayList of instruments and the myBalance instance variable being set to the Account object.
This has the effect of carrying on from where you had left off before(ie. the balance/instruments/owned instruments remain the same), apart from the day counter and moving average data being reset. 

'Something impressive'

Using a 2 dimensional ArrayList named 'rawData'(GUI.java line 13) and an extra ArrayList called 'movingAverages' (line 12), I have managed to generate moving average data for each FinancialInstrument object in the simulation.
The moving average data is appended to the text area t1 by pressing the button 'Show moving averages'. The averages are generated as follows: Initially, the ArrayList rawData is initialized in the constructor GUI() by adding a new ArrayList object
for each instrument in the ArrayList named 'instruments' (line 34-37) . Then in the generateMovingAverages() method (line 463), using a for loop, in each second dimension ArrayList of the 'rawData' ArrayList, the current value of each FinancialInstrument object is added.
After this using 2 nested for loops, I loop through the 2D 'rawData' ArrayList, calculating the average for each second dimension ArrayList. Each average value is then added to the ArrayList movingAverages, which then contains the final moving average data at the current simulation point.
The average is calculated by summing up the total of all values in the second dimension ArrayList and dividing by the length of the second dimension array. This feature, thanks to using ArrayLists, still works when a new
FinancialInstrument is added to the 'instruments' ArrayList (line 299), at which point on line 300, a new second dimension ArrayList is added to rawData to hold the raw data for that new object.




LEVELS 1 AND 2

(ONLY complete this if you did not get full marks for your level 1/2 submission.)

LEVEL ONE

[Briefly (three sentences) explain the changes you have made to your code so that it fully meets the level one requirements.]

My code demonstrates inheritance in the following way...

I have a superclass called [Insert name of superclass here]

This superclass is extended into at least two subclasses called [Insert names of the subclasses here]

For each of the named subclasses complete the following...

Subclass 1.

Subclass [Insert name of subclass] extends the superclass by adding at least one property and its getters and setters. The name(s) of the added properties are [Insert names of properties here.]

These/this new properties/property are used by the subclass in the following way: [Insert justification for adding the property and give the line numbers in the code of where the property is used.]

Subclass [Insert name of subclass] extends the superclass by overriding the following methods (there must be at least one): [Insert names of overridden methods and their line numbers in the code.]

These overridden methods are used in the working code in the following places: [list the file names and line numbers where the overridden methods are called.]

Subclass 2.

Subclass [Insert name of subclass] extends the superclass by adding at least one property and its getters and setters. The name(s) of the added properties are [Insert names of properties here.]

These/this new properties/property are used by the subclass in the following way: [Insert justification for adding the property and give the line numbers in the code of where the property is used.]

Subclass [Insert name of subclass] extends the superclass by overriding the following methods (there must be at least one): [Insert names of overridden methods and their line numbers in the code.]

These overridden methods are used in the working code in the following places: [list the file names and line numbers where the overridden methods are called.]


LEVEL TWO

[Briefly (three sentences) explain the changes you have made to your code so that it fully meets the level two requirements.]

Polymorphism consists of the use of the Substitution principle and Late Dynamic binding.

In my code, polymorphism is implemented in at least two places…

Example 1.

The substitution principle can be seen in use in [class name and line number where substitution is used]. The name of the superclass used this example is [name of superclass] and the subclasses used are [names of subclasses].

Late dynamic binding can be seen in [class name and line number].

[Explain briefly (no more than four sentences), why this example of polymorphism is necessary in your code.]

Example 2.

The substitution principle can be seen in use in [class name and line number where substitution is used]. The name of the superclass used this example is [name of superclass] and the subclasses used are [names of subclasses].

Late dynamic binding can be seen in [class name and line number].

[Explain briefly (no more than four sentences), why this example of polymorphism is necessary in your code.]

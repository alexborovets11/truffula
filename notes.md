# Truffula Notes
As part of Wave 0, please fill out notes for each of the below files. They are in the order I recommend you go through them. A few bullet points for each file is enough. You don't need to have a perfect understanding of everything, but you should work to gain an idea of how the project is structured and what you'll need to implement. Note that there are programming techniques used here that we have not covered in class! You will need to do some light research around things like enums and and `java.io.File`.

PLEASE MAKE FREQUENT COMMITS AS YOU FILL OUT THIS FILE.

## App.java
We are going to be printing the directory tree. We are going to create a TruffulaOptions object using the args and pass it to a new TruffulaPrinter that uses System.out. Then, we are going to call printTree on the TruffulaPrinter.
## ConsoleColor.java
Here we are going to code different colors into the console. We are going to be applying a lot of different colors into the console.
## ColorPrinter.java / ColorPrinterTest.java
We are going to print colorful file names. For example, the list of files are going to be colorful and we are going to test it here with ColoorPrinterTest.java. The test file is going to test different colors and see if they pass.
## TruffulaOptions.java / TruffulaOptionsTest.java
Here we are going to have options for controlling how a directory tree is displayed. We are going to have three options, show hidden files, use colored output, and/or the root directory from where to start printing the tree. We are going to test if the truffula.java is working correctly and passing all the test with different options. 
## TruffulaPrinter.java / TruffulaPrinterTest.java
this is responisble for printing a directory tree structure with colored output, we have to make sure it is case insenstive. In summary, this file will print the directory structure cleanly and colorfully, based on user settings. We will test using the TruffulaPrinterTest.java, we will test all the different formatting structures. 
## AlphabeticalFileSorter.java, 
This will sort the files alphabetically by the name and we have to make sure it is ignoring case differences.
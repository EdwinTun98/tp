# limleyhooi Project PortFolio Page

## Project: MoneyTrail v1.0 - v2.1

MoneyTrail is a lightweight, user-friendly budget tracking 
application designed to help you keep track of your expenses,
monitor your spending habits, and achieve your financial goals.
It is written in Java and is designed specifically for CLI users.

Given below are my contributions to the project:

- **Code contributed**: [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=limleyhooi&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

- **Implementations**:
    
    - New class: Created a `Parser` class for handling and interpreting user input.
        - What it does: It takes in the raw input, trims unnecessary spaces, and then converts the input string into a specific Command object.
        - Justification: This implementation follows the **Single Responsibility Principle**. It isolates the input interpretation logic from other parts of the program, making the code easier to maintain, extend, and debug.
       
    - New interface: Created a `Command` interface that defines the contract for all executable actions within the MoneyTrail application.
        - What it does: Various command classes (like AddExpenseCommand, AddIncomeCommand, DeleteCommand, ListCommand, etc.) implement this interface to encapsulate specific behaviors.
        - Justification: The Command interface allows for a clean abstraction of actions.
  
    - New class: Created a `Income` class that represents an income entry with attributes for description, amount, and date.
        - What it does: It provides getter methods for accessing these attributes. 
        - Justification: By encapsulating the data for an income entry in its own class, this implementation follows the **Encapsulation Principle**. 
  
    - New feature: Added the ability to record income entries. 
        - What it does: It allows users to add income entries by entering a command with a description, an amount, and an optional date (e.g., "addIncome Salary $/1000 d/15 march").
        - Justification: This feature enhances the application by enabling users to track their incoming funds in addition to their expenses. With income recording, users gain a complete view of their financial inflows and outflows, supporting more effective budgeting and financial planning.
        - Highlight: It handles optional date inputs by defaulting to “no date” when none is provided, making the feature more robust and user-friendly.

  - **Enhancements made**:
  - Performed JUnit tests on `Income` class. 


  
    
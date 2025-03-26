# User Guide

## Introduction

Managing your finances has never been easier. MoneyTrail is a 
lightweight, user-friendly budget tracking application designed to 
help you keep track of your expenses, monitor your spending habits, 
and achieve your financial goals.

## Quick Start

Here are the steps to get started quickly:

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `MoneyTrail` from [here](http://link.to/duke).
3. Copy the file to the folder you want to use as the *home folder* for your application.
4. Open a command terminal, `cd`, into the folder you put the jar file in, and use the `java -jar tp.jar` command to run the application.

## Features 

### Listing all tasks: `list`

Prints out a list of all recorded entries.

Format: `list`

Outcome:
```
Coming soon...
```

### Locating tasks: `find`

Searches for entries whose descriptions match the specified keyword.

Format: `find KEYWORD`

- Only the entry description is searched.
- Only finds texts that match the `KEYWORD` as a whole word. e.g. `find me` will not return `meeting` which contains the substring `me`.

Example: `find expense`, `find income`

Outcome:
```
Coming soon...
```

### Prompting help: `help`

Displays all available commands and their descriptions.

Format: `help`

Outcome:
```
Coming soon...
```

### Setting the total budget: `setTotalBudget`

Format: `setTotalBudget <amount>`

Example: `setTotalBudget 500`

Outcome: `Total budget set to: $500.00`
```
Coming soon...
```

### Adding expense entries: `addExpense`

Format: `addExpense <description> $/<amount> c/<category>`

#### Notes:
- Category parameter is optional! If no category is given, the expense will be tagged as "uncategorised".

Example 1: `addExpense Honey $/20.25 c/Food`
Example 2: `addExpense Honey $/20.25`   

Outcome 1: `Expense added: [Expense] Honey Value=$20.12 (Food)`
Outcome 2: `Expense added: [Expense] Honey Value=$20.12 (Uncategorised)`

### Adding up expenses: `totalExpense`

Coming soon...

Format: `Coming soon...`

Example: `Coming soon...`

Outcome:
```
Coming soon...
```

### Deleting a task: `delete`

Removes an entry from the list based on the list index given.

Format: `delete INDEX`

Example: `delete 1`

Outcome:
```
Coming soon...
```

### Exiting the program: `exit`

Exits the MoneyTrail Budget Tracker application.

Format: `exit`

Outcome: `Exiting program... Thank you for using MoneyTrail! :)`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Data are saved in a text file (*mt.txt*) automatically after
any command that changes the data. You can copy this file in a pen drive.

## Command Summary

* `Coming soon...`

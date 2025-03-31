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

### Listing all tasks: `listAll`

Prints out a list of all recorded entries.

Format: `listAll`

Outcome:
```
What do you want to do today?
list
Expense list:
1: [Expense] fuel $10.00 {transportation} [no date]
-------------------------------------------------------------------------------
What do you want to do next?
```

### Locating tasks: `find`

Searches for entries whose descriptions match the specified keyword.

Format: `find KEYWORD`

- Only the entry description is searched.
- Only finds texts that match the `KEYWORD` as a whole word. e.g. `find me` will not return `meeting` which contains the substring `me`.

Example: `find fuel`

Outcome:
```
What do you want to do next?
find fuel
Found Matching entries for: fuel
1: [Expense] fuel $10.00 {transportation} [no date]
-------------------------------------------------------------------------------
What do you want to do next?
```

### Prompting help: `help`

Displays all available commands and their descriptions.

Format: `help`

Outcome:
```
What do you want to do next?
help
List of available commands:
1. help: Displays this list of available commands.
2. listAll: Lists out all entries.
3. addExp <description> $/<value> c/<category> <: Adds a new expense entry. Category is optional.
4. totalExp: Displays the total expense accumulated from all entries.
5. setTotalBudget <budget>: Sets a total spending budget to adhere to.
6. del <entry_number>: Deletes an entry.
7. find <keyword>: Finds an entry based on the given keyword.
8. listCats: Lists out all entry categories in order of appearance.
9. exit: Exits the program.
-------------------------------------------------------------------------------
What do you want to do next?
```

### Setting the total budget: `setTotalBudget`

Specifies the intended value of the total budget.

Format: `setTotalBudget <amount>`

Example: `setTotalBudget 500`

Outcome:
```
What do you want to do next?
setTotalBudget 500
Total budget set to: $500.00
-------------------------------------------------------------------------------
What do you want to do next?
```

### Adding expense entries: `addExpense`

Adds an expense entry to the list.

Format: `addExpense <description> $/<amount> c/<category> d/<date>`

#### Notes:
- Category and date parameters are optional!
    * If no category is given, the expense will be tagged as "uncategorized".
    * Similarly, if no date is given, the expense will be tagged as "no date".

Example 1: `addExp Honey $/20.25 c/Food d/30-03-25`
Example 2: `addExp Honey $/20.25` 
Example 3: `addExp Honey $/20.25 d/30-03-25`

Outcome 1: `Expense added: [Expense] Honey $20.25 {Food} [2025-03-30]`
Outcome 2: `Expense added: [Expense] Honey $20.25 {Uncategorised} [no date]`
Outcome 3: `Expense added: [Expense] Honey $20.25 {Uncategorised} [2025-03-30]`

### Adding up expenses: `totalExp`

Adds the monetary value of all expense entries and displays the result.

Coming soon...

Format: `totalExp`

Example: `totalExp`

Outcome:
```
What do you want to do next?
totalExp
Total expenses: $660.00
-------------------------------------------------------------------------------
```

### Deleting a task: `del`

Removes an entry from the list based on the list index given.

Format: `del INDEX`

Example: `del 1`

Outcome:
```
What do you want to do next?
del 1
This entry will be permanently deleted:
[Expense] fuel $10.00 {transportation} [no date]
You now have 1 entry.
-------------------------------------------------------------------------------
What do you want to do next?
```

### Exiting the program: `exit`

Exits the MoneyTrail Budget Tracker application.

Format: `exit`

Outcome: 
```
What do you want to do next?
exit
-------------------------------------------------------------------------------
Exiting program... Thank you for using MoneyTrail! :)
```

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Data are saved in a text file (*mt.txt*) automatically after
any command that changes the data. You can copy this file in a pen drive.

## 💰 MoneyTrail Command Cheat Sheet

| Command          | Format                                          | Example                                    | Description                                  |
|------------------|-------------------------------------------------|--------------------------------------------|----------------------------------------------|
| `help`           | `help`                                          | `help`                                     | Shows all available commands                 |
| `listAll`        | `listAll`                                       | `listAll`                                  | Displays all expense entries                 |
| `addExp`         | `addExp <desc> $/<amount> [c/<cat>] [d/<date>]` | `addExp Lunch $/12.50 c/Food d/2023-10-15` | Add new expense (category/date optional)     |
| `del`            | `del <index>`                                   | `del 3`                                    | Removes entry #3 from list                   |
| `find`           | `find <keyword>`                                | `find coffee`                              | Searches entries by keyword                  |
| `totalExp`       | `totalExp`                                      | `totalExp`                                 | Shows sum of all expenses                    |
| `setTotalBudget` | `setTotalBudget <amount>`                       | `setTotalBudget 500.00`                    | Sets spending limit                          |
| `listCats`       | `listCats`                                      | `listCats`                                 | Shows all used categories                    |
| `exit`           | `exit`                                          | `exit`                                     | Closes the application                       |

#### 📝 Usage Notes
```bash
# Basic expense with required fields
addExp Textbook $/59.99

# With optional category
addExp Concert $/120.00 c/Entertainment

# With both optional fields
addExp Hotel $/200.00 c/Travel d/2023-12-15
```

#### 🔍 Tips
- View entry numbers first with `listAll` before using `del`
- Amounts must be positive numbers (e.g. `$/15.50`)
- Dates should use `YYYY-MM-DD` format

# User Guide

## Introduction

Managing your finances has never been easier. MoneyTrail is a 
lightweight, user-friendly budget tracking application designed to 
help you keep track of your expenses, monitor your spending habits, 
and achieve your financial goals.

## Quick Start

Here are the steps to get started quickly:

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `MoneyTrail` from [here](https://github.com/AY2425S2-CS2113-W12-4/tp/releases/download/v1.0/tp.jar).
3. Copy the file to the folder you want to use as the *home folder* for your application.
4. Open a command terminal, `cd`, into the folder you put the jar file in, and use the `java -jar tp.jar` command to run the application.

## Features

> [!NOTE]
> Useful information about the command format:
> - Words in `UPPER_CASE` are the parameters to be supplied by the user.
    e.g. in `find KEYWORD`, `KEYWORD` is a parameter which can be used as `find rent`.
> - Parameters must be in a **specific order** (except `edit` command).
    e.g. if the command specifies `c/CATEGORY d/DATE`, `d/DATE c/CATEGORY` is unacceptable.
> - **Extraneous parameters** for commands that do not take in parameters (such as `list`) will generate an error.
    e.g. `list 123` is unacceptable.
> *BEWARE:* All *<amount>* inputs will be formatted to *2 d.p.* with *no rounding*

### Listing all tasks: `list`

Prints out a list of all recorded entries.

Format: `list`

Outcome:
```
What do you want to do today?
list
Expense list:
1: Expense: fuel $10.00 {transportation} [no date]
-------------------------------------------------------------------------------
What do you want to do next?
```

### Locating tasks: `find`

Searches for entries whose descriptions match the specified keyword.

Format: `find KEYWORD`

- Finds texts that match the `KEYWORD` even not as a whole word. e.g. `find me` will return `meeting` which contains the substring `me`.

Example: `find fuel`

Outcome:
```
What do you want to do next?
find fuel
Found Matching entries for: fuel
1: Expense: fuel $10.00 {transportation} [no date]
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
2. list: Lists out all entries.
3. addExp <description> $/<value> [c/<category>] [d/<date>]: Adds a new expense entry.
4. addIncome <description> $/<value> [d/<date>]: Adds a new income entry.
5. totalExp: Displays the total expense accumulated.
6. setTotBgt <value>: Sets a total spending budget.
7. setCatBgt c/<category> <value>: Sets a budget for a specific category.
8. listBgt: Lists out all category budgets.
9. listCat: Lists out all entry categories in order of appearance.
10. del <index>: Deletes an entry.
11. find <keyword>: Finds an entry based on the keyword.
12. edit <index> [<description>] [$/<amount>] [c/<category>] [d/<date>]: Modify the full entry or just selected details.
13. check <Overall> or <Category>: Shows overall expense or total expense for searched category
    and show set budget and total expenditure of respective category.
14. clear: Clears all entries.
15. exit: Exits the program.
-------------------------------------------------------------------------------
What do you want to do next?
```

### Setting the total budget: `setTotBgt`

Specifies the intended value of the total budget.

Format: `setTotBgt VALUE`

Example: `setTotBgt 500`

Outcome:
```
What do you want to do next?
setTotBgt 500
Total budget set to: $500.00
-------------------------------------------------------------------------------
What do you want to do next?
```

### Adding expense entries: `addExp`

Adds an expense entry to the list.

Format: `addExp <description> $/<amount> c/<category> d/<date>`

#### Notes:
- Category and date parameters are optional!
    * If no category is given, the expense will be tagged as "uncategorized".
    * Similarly, if no date is given, the expense will be tagged as "no date".

Example 1: `addExp Honey $/20.25 c/Food d/30-03-25`

Example 2: `addExp Honey $/20.25` 

Example 3: `addExp Honey $/20.25 d/30-03-25`

Outcome 1: `Expense added: Expense: Honey $20.25 {Food} [2025-03-30]`

Outcome 2: `Expense added: Expense: Honey $20.25 {Uncategorised} [no date]`

Outcome 3: `Expense added: Expense: Honey $20.25 {Uncategorised} [2025-03-30]`

### Adding up expenses: `totalExp`

Adds the monetary value of all expense entries and displays the result.

Format: `totalExp`

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
Expense: fuel $10.00 {transportation} [no date]
You now have 1 entry.
-------------------------------------------------------------------------------
What do you want to do next?
```

### Clearing entries: `clear`

Clears all existing entries in the list.

Format: `clear`

Outcome:
```
What do you want to do today?
clear
All entries have been cleared.
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

### Editing an entry: `edit`

Modifies an existing entry's description, amount, 
category, or date.

Format: `edit INDEX DESCRIPTION [options]`

Options:

- `$/VALUE`: Updates the entry's amount.

- `c/CATEGORY`: Updates the entry's category.

- `d/DATE`: Updates the entry's date.

> [!NOTE]
> Useful information about the command format:
> - Parameters need not be in a **specific order**.
    e.g. if the command specifies `$/VALUE c/CATEGORY`, `c/CATEGORY $/VALUE` is acceptable.
> - Options need not be updated all at once. Users can select which options they want to update and leave the rest. 
> - However, INDEX must always come after `edit`, followed by DESCRIPTION.
    e.g. if the command specifies `edit INDEX DESCRIPTION`, `edit DESCRIPTION INDEX` is unacceptable.

Example: `edit 1 $/20`

Outcome:
```
What do you want to do next?
edit 1 $/20
Entry updated. Expense: Milk $20.00 {Uncategorized} [no date]
-------------------------------------------------------------------------------
What do you want to do next?
```

### Setting a Category Budget: `setCatBgt`

Specifies a budget value for a specific expense/income category.

Format: `setCatBgt c/<CATEGORY> <AMOUNT>`

Example: `setCatBgt c/Uncategorized 200`

Outcome:
```
What do you want to do next?
setCatBgt c/Uncategorized 200
Budget for category 'Uncategorized' set to $200.00
-------------------------------------------------------------------------------
What do you want to do next?
```

### Listing all category budgets: `listBgt`

Displays the respective budget limits for all categories.

Format: `listBgt`

Example: `listBgt`

Outcome:
```
What do you want to do next?
listBgt
-------- Overall Budgets --------
- Overall Budget: $5000.00

-------- Category Budgets: --------
- Budget for uncategorized: $200.00

-------------------------------------------------------------------------------
What do you want to do next?
```

### Listing all used categories: `listCat`

Displays all used categories in order of appearance in money list.

Format: `listCat`

Example: `listCat`

Outcome:
```
Categories (in order of appearance):
- Uncategorized
- Food
```

### Listing the overall expense or expense for a specific category: `check`

Displays overall expense or expense for a specified category

Format: `check <Overall or Category>`

Example: `check Food`

Outcome:
```
What do you want to do next?
check Food
-------- CATEGORY EXPENSES BUDGET CHECK --------
Budget for food: $1000.00
Total Spent: $10.00
Remaining: $990.00
-------------------------------------------------------------------------------
What do you want to do next?
```

### Clearing all entries: `clear`

Clear all entries stored in money list.

Format: `clear`

Example: `clear`

Outcome:
```
What do you want to do next?
setCatBgt c/food 100
Budget for category 'food' set to $100.00
-------------------------------------------------------------------------------
What do you want to do next?
```

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Data are saved in a text file (*mt.txt*) automatically after
any command that changes the data. You can copy this file in a pen drive.

## 💰 MoneyTrail Command Summary

| Command     | Format                                        | Example                                    | Description                                                |
|-------------|-----------------------------------------------|--------------------------------------------|------------------------------------------------------------|
| `help`      | `help`                                        | `help`                                     | Shows all available commands                               |
| `list`      | `list`                                        | `list`                                     | Displays all expense entries                               |
| `addExp`    | `addExp <desc> $/<amount> [c/<cat>] [d/<date>]` | `addExp Lunch $/12.50 c/Food d/2023-10-15` | Add new expense (category/date optional)                   |
| `addIncome` | `addIncome <desc> $/<amount> [d/date]`        | `addIncome Salary $/2500.00 d/2023-10-15`  | Adds a new income entry (date optional)                    |
| `del`       | `del <index>`                                 | `del 3`                                    | Removes entry #3 from list                                 |
| `find`      | `find <keyword>`                              | `find coffee`                              | Searches entries by keyword                                |
| `edit`      | `edit <index> [options]`                      | `edit 1 $/15 d/2024-05-08`                 | Modifies full entry or selected details                    | 
| `totalExp`  | `totalExp`                                    | `totalExp`                                 | Shows sum of all expenses                                  |
| `setTotBgt` | `setTotBgt <amount>`                          | `setTotBgt 500.00`                         | Sets total spending budget                                 |
| `setCatBgt` | `setCatBgt c/<category> <amount>`             | `setCatBgt c/Food 200.00`                  | Sets budget for a specific category                        |
| `listBgt`   | `listBgt`                                     | `listBgt`                                  | Shows all category budgets                                 |
| `listCat`   | `listCat`                                     | `listCat`                                  | Shows all used categories                                  |
| `check`     | `check <Overall or Category>`                 | `check Food`                               | Shows overall expenses of expenses for a specific category |
| `clear`     | `clear`                                       | `clear`                                    | Clears all entries                                         |
| `exit`      | `exit`                                        | `exit`                                     | Closes the application                                     |

#### 📝 Usage Notes
```bash
# Basic expense with required fields
addExp Textbook $/59.99

# With optional category
addExp Concert $/120.00 c/Entertainment

# With both optional fields
addExp Hotel $/200.00 c/Travel d/2023-12-15
```

### Known Issues

- Editing multiple fields at once may cause incorrect updates. Ensure that parameters are entered correctly to avoid data corruption.

- Application does not support undoing actions. Deleted or edited entries cannot be reverted.

- Special characters in descriptions may cause errors. Avoid using symbols like #, &, or @ in expense descriptions.

- Large datasets may slow down performance. If you have a significant number of entries, performance may degrade.

#### 🔍 Tips
- View entry numbers first with `list` before using `del`
- Amounts must be positive numbers (e.g. `$/15.50`)
- Dates should use `YYYY-MM-DD` format

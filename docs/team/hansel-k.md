# Hansel-K's Project Portfolio Page (PPP)

## Project: MoneyTrail v1.0 - v2.1

MoneyTrail is a lightweight, user-friendly budget tracking
application designed to help you keep track of your expenses,
monitor your spending habits, and achieve your financial goals.
It is written in Java and is designed specifically for CLI users.

Given below are my contributions to the project.

- **Code contributed**: [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=hansel-k&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=test-code~other~functional-code~docs)

---
### Implementations:
#### New Feature: Add Expense Entry [PR #46](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/46)
  
- **What it does**: Enables users to add new expense entries by specifying details like description, amount, category, and date.

- **Justification**: Provides a core functionality for users to track their expenses efficiently within the application
    
- **Highlights:**
  - Designed a flexible input format allowing optional fields (e.g., category and date).
  - Implemented validation mechanisms to ensure input correctness, prevent invalid markers, and handle edge cases like non-numeric amounts or negative values.
  - Enhanced usability with default values for optional fields (e.g., "Uncategorized" for category and "no date" for date).
      
- **Credits:** Utilized Java's standard libraries for string manipulation and error handling. Developed a comprehensive validation pipeline to parse and save expense data.

#### New Feature: Display All Unique Expense Categories [PR #50](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/50)
- **What it does:** Extracts and displays all unique categories from the expense entries recorded by the user.
- **Justification:** Helps users organize and understand their spending habits by categorizing expenses effectively
- **Highlights:**
  - Implemented a mechanism to extract unique categories while maintaining their order of appearance within the money list.
  - Developed handling for edge cases like empty entries and invalid data formats.
  - Ensured reliability with comprehensive error logging.
- **Credits:** Custom implementation utilizing Java's `LinkedHashSet` for maintaining uniqueness and order.

#### New Feature: Clear All Expense Entries
- **What it does:** Provides users with the ability to clear all entries from the money list in one command.
- **Justification:** Adds a convenient way for users to reset their financial data when starting anew or correcting errors.
- **Highlights:**
  - Integrated persistent storage updates to maintain data integrity after clearing entries.
  - Designed error handling to manage scenarios like saving failure or attempting to clear an empty list.

#### New Feature: Display Total Expenses [PR #41](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/41)
- **What it does:** Calculates and displays the total sum of expenses recorded by the user.
- **Justification:** Provides users with an essential overview of their spending habits, enabling better financial tracking and decision-making.
- **Highlights:**
  - Designed a reliable mechanism to parse and sum expense amounts from user input.
  - Developed error handling to manage invalid or malformed entries gracefully.
  - Integrated detailed logging for debugging and tracking purposes.
- **Credits:** Custom implementation using standard Java libraries.

#### New Feature: Set Total Budget [PR #41](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/41)
What it does: Enables users to set a total budget for their financial tracking. The budget is validated and formatted to ensure accuracy.
Justification: Provides a critical functionality to allow users to monitor and limit their expenses within a predefined budget.
- **Highlights:**
  - Implemented assertions to validate input and prevent misuse of commands.
  - Added persistent storage for budget information, ensuring data integrity across sessions.
  - Designed error handling for edge cases like invalid formats or negative values.
- **Credits:** Utilized Java's `DecimalFormat` for budget formatting and managed persistent storage integration.

---

### Enhancements to Existing Features
- Shortened some command inputs (e.g., from `addExpense` to `addExp`) for optimization. [PR #64](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/64) [PR #66](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/66)
- Performed and added JUnit tests on `addExpense` and other methods [PR #53](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/53) [PR #59](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/59) [PR #164](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/164)
- Applied the SLAP concept on the `addExpense` and `listCats` methods. [PR #88](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/88)

---
### Documentation
#### User Guide:
- Focused on adding and updating `addExp`, `totalExp` and `del` commands, and the Command Cheat Sheet. [PR #54](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/54) [PR #64](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/64) [PR #79](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/79) 
- Majorly updated User Guide with new features for V2.0 [PR #92](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/92)

#### Developer Guide:
- Provided UML diagrams illustrating the workflow of the `help` and `exit` commands. [PR #79](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/79)
- Filled in the User Stories, Non-Functional Requirements, Glossary, and Instructions for Manual Testing sections. [PR #79](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/79)
---
### Project Management
- Occasionally assigned and coordinated feature development.
- Reviewed numerous issues from Practical Exam dry run and assigned them to team members best suited to resolve them.
---
### Team-Based Contributions
- Shared insights and suggestions with team members to enhance the project (e.g. shortening user inputs for better UX).
- Manually tested for bugs before Practical Examination dry run and rectified them or raised the issue to team members. [PR #56](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/56) [PR #66](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/66) [PR #92](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/92)
- Resolved Parser input bugs, Double formatting bugs across multiple methods, and `addExp` date formatting bugs after Practical Examination dry run. [PR #164](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/164) [PR #166](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/166) [PR #171](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/171)

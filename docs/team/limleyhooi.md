# limleyhooi Project Portfolio Page (PPP)

## Project: MoneyTrail v1.0 - v2.1

MoneyTrail is a lightweight, user-friendly budget tracking  
application designed to help you keep track of your expenses,  
monitor your spending habits, and achieve your financial goals.  
It is written in Java and is designed specifically for CLI users.

Given below are my contributions to the project.

- **Code contributed**: [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=limleyhooi&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

---

### Implementations:
#### New Feature: Income Tracking (`addIncome`) [PR #70](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/70)
- **What it does:**  
  Enables users to record income entries using the `addIncome` command.  
  Supports input of a description, amount, and an optional date.

- **Scope of work:**
    - **`AddIncomeCommand`:** Parses and triggers income addition.
    - **`Income` class:** Represents income entries.
    - **`MoneyList.addIncome`:** Validates input and stores data.
    - **Parser enhancements:** Handles the `addIncome` command input.

- **Justification:**  
  Completes the core functionality of the app by enabling income tracking alongside expenses.

- **Highlights:**
    - Supports optional parameters.
    - Implements robust input validation.
    - Displays user-friendly error messages.

#### New Feature: JUnit Test Cases for Income Tracking [PR #90](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/90)
- **What it does:**  
  Provides thorough testing for the income handling functionality, covering edge cases such as:
    - Missing markers
    - Negative or zero values
    - Malformed inputs
    - Optional date usage

- **Justification:**  
  Ensures that the `addIncome` feature works reliably across different scenarios, thereby bolstering the app's robustness.

---

### Command Pattern Integration [PR #55](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/55)

- **What it does:**  
  Contributed to the design and implementation of the command system by developing the `Command` interface and its implementations (e.g., `AddIncomeCommand`).

- **Justification:**  
  Promotes modularity and scalability, making it easier to add future commands.

---

### Parser Logic Contributions
- **What it does:**  
  Enhanced the logic behind how commands are parsed and dispatched in the `Parser` class.  
  Designed command extraction and validation logic for:
    - `createAddIncomeCommand`
    - `createDeleteCommand`
    - `createBudgetCommand`
  
- **Justification:**  
  Improved consistency and correctness in handling diverse user inputs across commands.

---

### Contributions to Team
- **Troubleshooting:**  
  Helped teammates resolve CI check errors and test case failures.

- **Repository Management:**  
  Added MT logs to `.gitignore` to keep the repository clean and tidy.
# EdwinTun98's Project Portfolio Page

## Project: MoneyTrail v1.0 - v2.1

MoneyTrail is a lightweight, user-friendly budget tracking application designed to help users monitor expenses, enforce spending limits, and develop better financial habits. Built in Java, it is optimized for CLI users.

Given below are my contributions to the project:

- **Code Contributed**:  
  [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=EdwinTun98&tabRepo=AY2425S2-CS2113-W12-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

---

- **Implemented Features**:

- **Find Entries**
    - *What it does*: What it does: Enables users to search for expense or income entries by a keyword (searches in description, category, and date, case-insensitive).

- **Edit Expenses**
    - *What it does*: What it does: Allows users to perform partial or full updates on existing entries with validation to prevent negative or zero values. Alerts users if an invalid index or formatting is encountered.

- **List Expenses**
    - *What it does*: Displays all expenses, providing a clear snapshot of the expenses.

- **List Budgets**
    - *What it does*: Displays all category-specific and overall budgets, providing a clear snapshot of the allocated amounts.
       
- **Set Category Budget Limit**
    - *What it does*: Allows users to assign a budget limit to individual categories. Validates format and prevents limits that exceed the total budget. It validates input using a helper `parseAndValidateAmount()` (ensuring up to 7 whole digits and 2 decimal places) and prevents individual limits from exceeding the overall budget.

- **Check Expenses Against Budget**
    - *What it does*: Allows users to check either their overall or category-specific expenses against the corresponding budget.

- **Budget Storage Integration**
    - *What it does*: Ensures budget entries persist across sessions and are reliably retrieved.
---

## Enhancements & Refactoring

- **Find Entries**:
  - *Enhancement*: Refined search functionality and introduced the `findEntry()` feature. [PR #38](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/38)

- **Edit Expenses**:
  - *Enhancement*: Improved modularity and reliability of update logic. [PR #67](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/67)

- **List Budgets**:
  - *Enhancement*: Added support for category budgets and introduced the `Budget` class to better manage these details. [PR #78](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/78)

- **Set Category Budget Limit**:
  - *Enhancement*: Enhanced validations and bug fixes in budget checking. [PR #86](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/86)

- **Set Total Budget**:
  - *Enhancement*: Improved validation logic to handle edge cases effectively. [PR #86](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/86)

- **Check Expenses Against Budget**
  - *Enhancement*: Adjusted logic to better handle edge cases and ensure user warnings in problematic scenarios. [PR #168](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/168)

- **Refactored Complex Methods into Readable Components Enhancements**  
  Refactored long and hard-to-test methods (`addExpense()`, `editExpense()`, `setCategoryLimit()`) into smaller, modular helpers to improve clarity, testability, and maintainability.

- **Set Category Budget Limit and Set Total Budget**
  - Added validation using parseAndValidateAmount() to enforce correct budget input (up to 7 whole digits and 2 decimal places).

  - Introduced logic to prevent setting a category budget that exceeds the total overall budget.

  - Added check for presence of overall budget before allowing category budget to be set.

  - Enhanced modularity and defensive coding practices in handling invalid formats and overflow. [PR #170](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/170)

- **Expense Addition Enhancements**
  - *Enhancement*: Overall refactoring for improved modularity and error handling. [PR #170](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/170)

- **Budget Storage Integration**
    - *Enhancement*: Fixes issues with file persistence so that budget entries, including the overall budget, are consistently loaded from `budgets.txt`.Improved consistency in data storage and retrieval. [PR #168](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/168)

- **Extracted and Reused Helper Methods**  
  Created reusable utility methods to prevent code duplication and simplify error handling:
    - `parseAndValidateAmount()` – validates numerical input format and limits.
    - `checkIfExceedsOverallBudget()` – alerts users if a new expense exceeds total budget.
    - `checkIfExceedsCategoryBudget()` – provides a similar alert for category-specific limits.
    - `getTotalCategoryBudgets()` – aggregates all category budgets (excluding overall).
    - `matchesKeyword()` – simplifies keyword matching for the `findEntry()` command.

- **Improved Input Validation & Error Messaging**
    - Ensured all amount-related inputs conformed to a strict format using regex.
    - Standardized exception handling using meaningful messages and centralized logger calls.
    - Supported defensive programming to guard against `null`, blank inputs, and malformed formats.

- **Improved Robustness in Budget Handling**
    - Introduced budget overflow detection before saving new values.
    - Prevented incorrect persistence of invalid budget values using early exception exits.
    - Ensured total and category budgets are always respected during expense addition.

- **Streamlined Storage Logic**
    - Ensured budget data load consistently on program restart.
    - Fixed issues related to missing "Overall" budget during file reads by introducing conditional checks and cleaner parsing in `Budget.parseString()`.

---

## Testing Contributions (JUnit)

- Developed comprehensive test cases for core functionalities including `SetTotalBudget`, `SetCategoryLimit`, `CheckExpenses`, `AddExpense`, `ListBudgets`, `Find`, and `EditExpense`.
- *Edge Cases Covered*: Budget overflow/underflow, invalid input characters, and inconsistencies in command formatting.
- *Enhancement*: Updated test cases to reflect the latest changes and bug fixes. [PR #168](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/168)

---

## Documentation

- **Developer Guide**:
    - Updated diagrams and detailed explanations for modules such as `AddExpense`, `SetCategoryLimit`, `CheckExpenses`, `Find`, and `EditExpense`.
    - Elaborated on the helper methods and their logic flows.

- **User Guide**:
    - Ensured feature changes are clearly documented to improve user understanding.
---


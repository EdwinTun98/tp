# EdwinTun98's Project Portfolio Page

## Project: MoneyTrail v1.0 - v2.1

Project: MoneyTrail v1.0 - v2.1
MoneyTrail is a lightweight, user-friendly budget tracking
application designed to help users monitor expenses, enforce spending limits,
and develop better financial habits. It is built in Java and optimized for CLI users.


Given below are my contributions to the project:

- **Code contributed**: [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=EdwinTun98&tabRepo=AY2425S2-CS2113-W12-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)


## Implementations

- **New feature**: `Find`
    - What it does: Enables users to search for entries by keywords.
    - Justification: Improves navigation through the list of expenses, especially when it's long.

- **New feature**: `List`
    - What it does: Displays all saved expense entries in a readable format.

- **New feature**: `EditExpenses`
    - What it does: Allows editing of any existing entry's description, amount, category, and date.
    - Justification: Fixes user mistakes or updates entry values without deletion.

- **New feature**: `ListBudget`
    - What it does: Lists all budgets set for categories and the overall budget.

- **New feature**: `SetCategoryBudgetLimit`
    - What it does: Allows setting individual budgets per category.

- **New feature**: `CheckExpenses`
    - What it does: Compares spending against set budgets and alerts if limits are exceeded.

- **Storage enhancement**: Added persistent storage support for **budget entries** on load.

---

## Enhancements

- Refactored methods for:
    - `EditExpenses`
    - `CheckExpenses`
    - `SetCategoryBudgetLimit`
- Applied SLAP (Single Level of Abstraction Principle) to parser logic for better maintainability.
- **JUnit Tests**:
    - Wrote unit tests for:
        - `SetTotalBudget`
        - `SetCategoryBudgetLimit`
        - `CheckExpenses`
        - `ListBudgets`
    - Ensured proper coverage for edge cases such as invalid/empty input and negative values.

---

## Documentation

- **Developer Guide**:
    - Added sequence and class diagrams for:
        - `ListCategory`
        - `SetCategoryLimit`
        - `EditExpense`
        - `Find`
    - Wrote detailed descriptions for each of the features implemented, including command format, validation logic, and storage behavior.

---

## Team-based Tasks & Collaboration

- Participated in review and refinement of common utility classes (`TextUI`, `Parser`)
- Worked closely with teammates to ensure consistent CLI command formats
- Reviewed PRs and suggested improvements to maintain coding standards across the codebase


  
    

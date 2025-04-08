# EdwinTun98's Project PortFolio Page

## Project: MoneyTrail v1.0 - v2.1
MoneyTrail is a Java-based CLI budget tracker that helps users manage expenses, enforce spending limits, and build better financial habits.

## Key Contributions

### Feature Development
- **Find Entries:**  
  Developed a keyword search for quick access to expense/income entries via descriptions, categories, and dates ([PR #38](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/38)).
- **Edit Expenses:**  
  Enhanced editing with validations to prevent negative or zero values, ensuring data integrity ([PR #67](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/67)).
- **List Displays:**  
  Created clear displays for all expenses and both overall and category-specific budgets ([PR #78](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/78)).
- **Budget Management:**  
  Implemented features to set, validate, and compare category and total budgets against expenses ([PR #86](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/86); [PR #168](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/168)).
- **Data Persistence:**  
  Integrated reliable budget storage to maintain data across sessions ([PR #168](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/168)).

### Enhancements & Refactoring
- **Modularity:**  
  Refactored complex methods into helper functions (e.g., `parseAndValidateAmount()`, `checkIfExceedsOverallBudget()`) for better readability and testability ([PR #170](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/170)).
- **Validation & Error Handling:**  
  Standardized input validations with regex and improved error messaging.
- **Storage Reliability:**  
  Streamlined saving/loading logic to prevent data issues like missing overall budgets.
- **Incremental Improvements:**  
  Several targeted PRs ([PR #38](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/38), [PR #67](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/67), [PR #78](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/78), [PR #86](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/86), [PR #168](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/168), [PR #170](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/170)) supported these enhancements.

### Testing & Documentation
- **JUnit Testing:**  
  Developed comprehensive tests for expense management, budget settings, and edge cases.
- **Documentation:**  
  Updated developer and user guides with diagrams, workflows, and usage instructions.

## Team Contribution

- **User Experience:** Enhanced search, editing, displays, and timely budget alerts.
- **Code Quality:** Modular refactoring and shared helpers improved readability and testing.
- **Data Integrity:** Unified validations and error handling protect data accuracy.
- **Proactive Design:** Team-driven alerts and spending checks empower effective financial management.
- **Testing & Documentation:** Thorough tests and clear guides ensure a robust application.

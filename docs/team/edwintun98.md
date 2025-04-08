# EdwinTun98's Project PortFolio Page

## Project: MoneyTrail v1.0 - v2.1
MoneyTrail is a CLI-based, Java budget tracking app designed to help users manage expenses, enforce spending limits, and build better financial habits. It provides a lightweight yet powerful tool for users who prefer command-line interaction to monitor and control their finances.

## Key Contributions

### Feature Development
- **Search & Edit:**
  - **Find Entries:**  
    Developed a keyword-based search to allow users to quickly locate expense or income entries by searching through descriptions, categories, and dates. This improves usability by reducing the time users spend finding specific entries and makes data access more efficient. ([PR #38](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/38))
  - **Edit Expenses:**  
    Enhanced the editing functionality with robust validations that prevent negative or zero values. This work ensures data integrity and reliability, preventing accidental entry errors while allowing users to update their records smoothly. ([PR #67](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/67))

- **Display Functions:**
  - **List Expenses:**  
    Built a comprehensive display function that shows all expenses in a clear format, offering users an at-a-glance overview of their spending.
  - **List Budgets:**  
    Created functionality to list both overall and category-specific budgets, enabling users to easily monitor their financial limits and manage their allocations effectively. ([PR #78](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/78))

- **Budget Management:**
  - **Set Category Budget Limit:**  
    Implemented a feature with advanced validations to set category-specific budget limits. The system ensures that each individual limit does not exceed the total budget, thereby enforcing financial discipline and preventing overspending errors. ([PR #86](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/86))
  - **Check Expenses Against Budget:**  
    Designed a mechanism for comparing expenses against set budgets to alert users when spending is nearing or exceeding allocated limits. This proactive approach helps users maintain control over their finances. ([PR #168](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/168))

- **Data Persistence:**
  - **Budget Storage Integration:**  
    Integrated persistent storage for budget data, ensuring that all budget entries are saved and reliably reloaded across sessions. This enhancement reinforces user trust by guaranteeing that their financial data is secure and consistently available. ([PR #168](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/168))

### Enhancements & Refactoring
- **Modularity & Helper Methods:**
  - Refactored complex logic into smaller, manageable helper functions such as `parseAndValidateAmount()`, `checkIfExceedsOverallBudget()`, and others. This not only improves code readability and maintainability but also makes the system easier to test and extend in the future. ([PR #170](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/170))

- **Error Handling & Input Validation:**
  - Standardized input validations using regular expressions and comprehensive error messages across features. This uniform approach minimizes bugs and provides clear user feedback, ensuring that only valid and correctly formatted data is processed.

- **Streamlined Storage Logic:**
  - Enhanced the logic responsible for data storage to provide a more reliable and consistent process when loading and saving budget data. This work prevents issues such as missing overall budget information and improves long-term data stability.

- **Incremental Improvements via Pull Requests:**
  - Utilized a series of focused pull requests to incrementally improve the codebase. This structured approach not only facilitated rigorous code reviews but also ensured that each enhancement built upon a stable foundation:
    - [PR #38](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/38)
    - [PR #67](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/67)
    - [PR #78](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/78)
    - [PR #86](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/86)
    - [PR #168](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/168)
    - [PR #170](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/170)

### Testing & Documentation
- **JUnit Testing:**
  - Developed comprehensive test cases covering functionalities like setting total budgets, category limits, expense additions, and validations. These tests cover edge cases such as budget overflow, invalid input formats, and ensure consistent behavior across various user scenarios.

- **Documentation:**
  - Updated both the developer and user guides to include detailed diagrams, module workflows, and usage instructions. Enhanced documentation provides clarity on system architecture and helps new developers or users quickly understand the application's functionality.

## Team Contribution

- **Collaborative User Experience:**  
  The team worked closely to enhance user interactions, developing features like quick searches, robust editing, clear expense displays, and proactive budget tracking. This collaboration has resulted in an intuitive and effective financial management tool.

- **Collective Code Quality & Maintainability:**  
  By jointly implementing modular refactoring and shared helper methods, the team significantly improved the readability and maintainability of the codebase. This collective approach has streamlined future development and simplified testing processes.

- **Unified Data Integrity Efforts:**  
  Through shared efforts in establishing rigorous validations and error handling, the team ensured that financial data remains accurate and secure. This coordinated focus prevents data corruption and guarantees reliable application performance.

- **Proactive Financial Management Design:**  
  The team collaboratively designed features that proactively alert users about budget limits and spending oversights. This group initiative empowers users to maintain financial discipline and better manage their resources.

- **Comprehensive Testing & Documentation:**  
  The development and maintenance of detailed JUnit tests and thorough documentation were team-oriented tasks. This collaborative process has ensured a robust, well-documented, and future-proof application that is easier for new contributors to understand and extend.

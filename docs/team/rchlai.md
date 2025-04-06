# rchlai's Project PortFolio Page

## Project: MoneyTrail v1.0 - v2.1

MoneyTrail is a lightweight, user-friendly budget tracking 
application designed to help you keep track of your expenses, 
monitor your spending habits, and achieve your financial goals.
It is written in Java and is designed specifically for CLI users.

Given below are my contributions to the project:

- **Code contributed**: [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=rchlai&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

- **Implementations**:
  - New feature: Added the ability to delete entries.
    - What it does: It allows the user to remove entries from the list using the delete command. This action, however, is irreversible.
    - Justification: It improves the product by enabling users to remove outdated, duplicate, and mistakenly entered entries. Removing old entries also allows users to read the list easier.

  - New feature: Added the ability to clear all entries
    - What it does: It allows users to clear all existing entries.
    - Justification: This caters to users who may want to reset their budget without old data influencing new records. It also helps in maintaining a clean slate for better financial planning.

- **Enhancements made**:
  - Applied the SLAP concept on the Parser class [PR #73](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/73)
  - Shortened the command inputs (e.g., from `delete` to `del`) for optimization [PR #68](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/68)
  - Performed JUnit tests on `Storage`, `TextUI`, `ClearListCommand`, and `TotalExpenseCommand` classes [PR #57](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/57) [PR #68](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/68)

- **Contributions to team-based tasks**:
  - Provided author tags for teammates [PR #73](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/73)
  - Added JavaDoc comments in the `MoneyTrail`, `Storage`, `MTLogger`, `Expense`, and `Command` classes. [PR #63](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/63)
  - Built the JAR files and managed `v1.0` - `v2.1` releases (3 releases, including the user guide (UG) and developer guide (DG) as PDF files) on GitHub
  - Set up the GitHub team organization and added members with their assigned roles
  - Maintained the issue tracker

- **Documentation**:
  - User Guide:
    - Added documentations for the `delete` feature [issue #19](https://github.com/AY2425S2-CS2113-W12-4/tp/issues/19) and `clear` feature [issue #65](https://github.com/AY2425S2-CS2113-W12-4/tp/issues/65)
    - Added draft details of introduction, quick start, features, and FAQ sections [PR #43](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/43)
  - Developer Guide:
    - Placed section links at the top of the DG for easy section navigation
    - Added all details of the design section, including the descriptions of the architecture, its components, and their corresponding diagrams. [PR #49](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/49) [PR #57](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/57) [PR #74](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/74) [PR #75](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/75) [PR #76](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/76) [PR #77](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/77)
    - Added implementation details for the `delete` and `exit` features [PR #81](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/81)
    - Did very minor cosmetic tweaks to existing documentations [PR #74](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/74) [PR #83](https://github.com/AY2425S2-CS2113-W12-4/tp/pull/83)
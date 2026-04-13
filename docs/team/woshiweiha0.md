# woshiweiha0 - Project Portfolio Page

## Overview

**Resumake CLI** is a desktop command-line application designed to help users manage and structure resume content such as projects, experiences, and activities. The user interacts with it via CLI commands, and the application is written in Java.

Given below are my contributions to the project.

---

## New Feature: Find Command

**What it does:**  
Allows users to search records by keyword using `find KEYWORD`.
It matches against title, role, tech stack, and date fields.

**Justification:**  
As the number of records grows, manual scanning becomes slow and error-prone.
This feature improves usability by helping users quickly retrieve relevant records.

**Highlights:**
- Added keyword-based matching workflow for records.
- Included user-facing output for both matched and no-match cases.
- Added validation and error handling for invalid/missing keyword input.

---

## New Feature: FindBullet Command

**What it does:**  
Allows users to search bullet points across all records using `findbullet KEYWORD`.
For each matching record, only matching bullets are shown.

**Justification:**  
Resume bullets often contain the most important achievements.
This feature helps users locate specific impact statements quickly without opening records one by one.

**Highlights:**
- Implemented bullet-level keyword search across all records.
- Included record index display in output to align with list numbering.

---

## New Feature: MoveBullet Command

**What it does:**  
Allows users to reorder bullets within a record using
`movebullet RECORD_INDEX FROM_BULLET_INDEX TO_BULLET_INDEX`.

**Justification:**  
Bullet ordering affects resume quality and emphasis.
This feature lets users prioritize stronger achievements without rewriting bullets.

**Highlights:**
- Added support for moving bullets by index within the same record.
- Implemented bounds checking and graceful handling of invalid indices.
- Added tests to verify forward/backward moves and no-op behavior.

---

## New Feature: Help Command

**What it does:**  
Adds a `help` command that displays the list of available commands with their formats and short descriptions.

**Justification:**  
New users may not remember command syntax immediately.  
This feature improves discoverability and reduces friction by showing a quick in-app command reference.

**Highlights:**
- Implemented `help` command parsing and execution flow.
- Added user-facing output covering all supported commands and brief usage descriptions.
- Updated tests and User Guide to document and verify the new command behavior.

---

## Summary of Contributions

### Code contributed

- Functional code and tests: [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=woshiweiha0&tabRepo=AY2526S2-CS2113-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements implemented

1. **Prevent app crashes on invalid user input** ([#52](https://github.com/AY2526S2-CS2113-F09-2/tp/pull/52))
   - Improved input validation paths so malformed commands and bad parameters are handled more safely.
   - Reduced abrupt failures by routing invalid input into controlled error handling instead of allowing unhandled runtime errors.
   - Benefit: the app remains usable even when users mistype command formats or pass invalid indices/dates.

2. **Made storage loading more defensive** ([#48](https://github.com/AY2526S2-CS2113-F09-2/tp/pull/48))
   - Strengthened file-loading behavior to tolerate invalid/corrupted record lines.
   - Implemented safer parsing and line-level skipping instead of failing the whole load operation.
   - Benefit: startup reliability is improved; one bad line is less likely to block access to all saved data.

3. **Standardized UI output usage (`Ui.showMessage`)** ([#43](https://github.com/AY2526S2-CS2113-F09-2/tp/pull/43))
   - Replaced direct `System.out.println()` usage in selected flow paths with `Ui.showMessage()` for better consistency.
   - Benefit: output formatting is easier to maintain and align across commands.

4. **Refactored Ui dependency flow to reduce coupling** ([#100](https://github.com/AY2526S2-CS2113-F09-2/tp/pull/100))
   - Shifted core runtime path toward shared `Ui` injection (app root -> parser/storage/commands).
   - Kept backward-compatible constructor overloads to avoid feature regressions during refactor.
   - Benefit: cleaner dependency management and better long-term testability.

5. **Expanded automated coverage for runtime + I/O defensive paths** ([#104](https://github.com/AY2526S2-CS2113-F09-2/tp/pull/104))
   - Added integration-focused tests for `Resumake` run loop to cover startup/exit and invalid-input error handling flow.
   - Expanded `Ui` tests to cover output helpers (`showLine`, `showMessage`, `showLoadingError`) beyond greeting/read basics.
   - Added `Storage` defensive tests for malformed user metadata, first-line record fallback, and unknown record type skip behavior.
   - Benefit: reduces regression risk in startup, console interaction, and persistence edge cases shared across the whole team codebase.

### Contributions to the User Guide (UG)

- Added **Quick Navigation** to improve discoverability of commands.
- Documented features that I implemented.
- Updated the **Command Summary** to reflect current command formats.

### Contributions to the Developer Guide (DG)

- Documented key technical design decisions for command handling and parsing flow, including validation/error paths.
- Added/updated implementation notes for robustness-related improvements (defensive storage loading and invalid-input handling).
- Clarified maintainability direction by documenting the Ui dependency flow refactor (shared injection to reduce coupling).

### Contributions to team-based tasks

- Set up the GitHub team organization and repository.
- Added Quick Navigation structure in UG for team documentation quality.
- Helped align command documentation with implemented behavior before release checks.
- Strengthened team-wide confidence in core flows by adding regression tests for app runtime, UI output consistency, and storage defensive behavior.

### Contributions beyond the project team

- Conducted PE-D for another team and reported a high number of actionable bugs (21), then followed up with peer input to clarify impact and improve fixability.
- Reviewed teammate code and shared feedback on robustness, parser flow, and command behavior consistency.
- Helped identify edge cases around invalid inputs and storage parsing so issues could be fixed earlier.
- Participated in team debugging discussions to unblock integration and testing.

### Tools

- Used Java logging (`Logger`) for debugging and tracing command execution.
- Utilised JUnit for testing parser and command behavior.
- Used Gradle (`test`, `check`) for verification workflows.
- Used GitHub Issues/PR workflow to track and coordinate enhancements.

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

## Summary of contributions

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

3. **Standardized UI output usage (`Ui.showMessage`)**
   - Replaced direct `System.out.println()` usage in selected flow paths with `Ui.showMessage()` for better consistency.
   - Benefit: output formatting is easier to maintain and align across commands.

### Contributions to the User Guide (UG)

- Added **Quick Navigation** to improve discoverability of commands.
- Documented features that I implemented.
- Updated the **Command Summary** to reflect current command formats.

### Contributions to the Developer Guide (DG)

- To be updated.

### Contributions to team-based tasks

- Set up the GitHub team organization and repository.
- Added Quick Navigation structure in UG for team documentation quality.
- Helped align command documentation with implemented behavior before release checks.

### Contributions beyond the project team

- Reviewed teammate code and shared feedback on robustness, parser flow, and command behavior consistency.
- Helped identify edge cases around invalid inputs and storage parsing so issues could be fixed earlier.
- Participated in team debugging discussions to unblock integration and testing.

### Tools

- Used Java logging (`Logger`) for debugging and tracing command execution.
- Utilized JUnit for testing parser and command behavior.
- Used Gradle (`test`, `check`) for verification workflows.
- Used GitHub Issues/PR workflow to track and coordinate enhancements.

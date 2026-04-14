# Tore Lim - Project Portfolio Page

## Overview

**ResuMake CLI** is a Java command-line app for managing resume content such as projects, experiences, CCAs, and bullet points through text commands. My contributions focused on record viewing and user-profile features like list, show, sort, generate, and edituser, plus the `User` singleton and skill tracking.

Given below are my contributions to the project.

---

## Summary of Contributions

### Code Contributed

- Functional code and tests: [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=tlimnus&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=tlimnus&tabRepo=AY2526S2-CS2113-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented

1. **List records with optional type filter (`list`)** — Displays all records or filters by `E`, `C`, or `P`. Records are shown with their actual list-position index, so the index is always consistent with what `show`, `edit`, and `delete` expect.

2. **Show individual record (`show`)** — Displays a single record by index together with all its bullet points.

3. **Generate resume (`generate`)** — Generates a resume-style output with personal details first, followed by records grouped by type with their bullet points, and an auto-generated skills section at the end.

4. **Sort records (`sort`)** — Sorts all stored records alphabetically by title using a case-insensitive comparator applied directly at the `RecordList` level. All subsequent `list` and `generate` output immediately reflects the new order.

5. **Edit user profile (`edituser`)** — Lets users update name, phone number, or email one field at a time. Shows the current value before prompting so the user knows what they are replacing.

6. **User singleton and reference-counted skill tracking (`User`)** — Maintains the user's personal details as a singleton accessible from anywhere without being threaded through every command. Tech skills are reference-counted via `addSkills()` / `removeSkills()`: a skill is only removed once no record references it.

7. **Standardised error messages** — Unified invalid-index error text across `DeleteCommand`, `EditBulletCommand`, `MoveBulletCommand`, `EditCommand`, `AddBulletCommand`, and `ShowCommand`.

8. **Comprehensive test coverage** — Wrote and expanded tests in `ListCommandTest`, `ShowCommandTest`, `GenerateCommandTest`, `UserTest`, `SortCommandTest`, and `EditUserCommandTest`.

### Contributions to the User Guide (UG)

- Documented the `list`, `show`, `sort`, `generate`, and `edituser` commands with usage examples and expected output.
- Clarified valid filter arguments for `list` and the retry behaviour (attempts counter, exit after exhaustion) for `edituser`.

### Contributions to the Developer Guide (DG)

- Added implementation notes for `ListCommand`, `ShowCommand`, `SortCommand`, `GenerateCommand`, and `EditUserCommand`.
- Documented the skill-tracking design in `User`, including reference-counting and normalisation, and how it integrates with `GenerateCommand`.

### Contributions to Team-Based Tasks

- Standardised error message conventions across the codebase to reduce user-facing inconsistencies.
- Fixed the Gradle `-ea` flag to unblock CI and surface assertion failures during local test runs.

### Review/Mentoring Contributions

- Reviewed pull requests and gave feedback on command validation, error handling patterns, and index consistency.
- Assisted teammates in diagnosing test failures introduced by the list-indexing change.

### Contributions Beyond the Project Team

- Reported bugs in other teams' products during the PE dry run.

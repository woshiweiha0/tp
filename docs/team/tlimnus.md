# Tore Lim - Project Portfolio Page

## Overview

**ResuMake CLI** is a Java command-line application that helps users manage resume content such as projects, experiences, CCAs, and bullet points. Users interact with the application through text commands, which are parsed into command objects and executed against the resume record list. My contributions centred on the record-viewing and user-profile side of the app — list, show, sort, generate, and edituser — as well as the underlying `User` singleton and skill-tracking system.

Given below are my contributions to the project.

---

## Summary of Contributions

### Code Contributed

- Functional code and tests: [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=tlimnus&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=tlimnus&tabRepo=AY2526S2-CS2113-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented

1. **List records with optional type filter (`list`)** — Displays all records or filters by `E`, `C`, or `P`. Records are shown with their actual list-position index rather than a re-numbered sequence, so the index is always consistent with what `show`, `edit`, and `delete` expect. Accepts the filter case-insensitively and validates it with a clear error if an unsupported value is given.

2. **Show individual record (`show`)** — Displays a single record by 1-based index together with all its bullet points (or a fallback `(no bullets)` message if there are none). Validates bounds before access, converts user-facing indices to internal 0-based indices in the constructor, and exposes `showRecordWithBullets()` as a static utility so `GenerateCommand` can reuse the same display logic without duplication.

3. **Generate resume (`generate`)** — Produces a structured resume-style output: personal details first, then records grouped by type (CCA / Experience / Project) with bullets, then an auto-derived skills section. Reuses `ShowCommand.showRecordWithBullets()` and aggregates skills from the `User` singleton, keeping generation logic thin and modular.

4. **Sort records (`sort`)** — Sorts all stored records alphabetically by title using a case-insensitive comparator applied directly at the `RecordList` level. All subsequent `list` and `generate` output immediately reflects the new order.

5. **Edit user profile (`edituser`)** — Lets users update name, phone number, or email one field at a time. Shows the current value before prompting so the user knows what they are replacing.

6. **User singleton and reference-counted skill tracking (`User`)** — Maintains the user's personal details as a singleton accessible from anywhere without being threaded through every command. Tech skills are reference-counted via `addSkills()` / `removeSkills()`: a skill is only removed once no record references it. Skill strings are normalised by stripping surrounding quotation marks before storage.

7. **Standardised error messages** — Unified invalid-index error text across `DeleteCommand`, `EditBulletCommand`, `MoveBulletCommand`, `EditCommand`, `AddBulletCommand`, and `ShowCommand`.

8. **Enabled JVM assertions in Gradle** — Added the `-ea` flag to `build.gradle` so `assert` statements are enforced during test runs instead of being silently skipped.

9. **Comprehensive test coverage** — Wrote and expanded tests in `ListCommandTest`, `ShowCommandTest`, `GenerateCommandTest`, `UserTest`, `SortCommandTest`, and `EditUserCommandTest`.

### Contributions to the User Guide (UG)

- Documented the `list`, `show`, `sort`, `generate`, and `edituser` commands with usage examples and expected output.
- Clarified valid filter arguments for `list` and the retry behaviour (attempts counter, exit after exhaustion) for `edituser`.

### Contributions to the Developer Guide (DG)

- Added implementation notes for `ListCommand`, `ShowCommand`, `SortCommand`, `GenerateCommand`, and `EditUserCommand`.
- Documented the skill-tracking design in `User`, including reference-counting and normalisation, and how it integrates with `GenerateCommand`.

### Contributions to Team-Based Tasks

- Standardised error message conventions across the codebase to reduce user-facing inconsistencies.
- Fixed the Gradle `-ea` flag to unblock CI and surface assertion failures during local test runs.
- Contributed to team discussions on index consistency, command retry behaviour, and error message standards.

### Review/Mentoring Contributions

- Reviewed pull requests and gave feedback on command validation, error handling patterns, and index consistency.
- Assisted teammates in diagnosing test failures introduced by the list-indexing change.

### Contributions Beyond the Project Team

- Reported bugs in other teams' products during the PE dry run.

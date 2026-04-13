# ResuMake

ResuMake is a command-line resume content manager for students and early-career applicants.
It helps users quickly create and refine resume-ready records for projects, experiences, and CCAs.

## Features

- Add timed records with structured fields (`title`, `role`, `tech`, `from`, `to`) using
  `project`, `experience`, and `cca`.
- Capture bullets immediately after adding a record (`y`/`n`, stop with `esc`).
- Manage bullets with `addbullet`, `editbullet`, `movebullet`, `findbullet`, and `deletebullet`.
- Manage records with `list`, `show`, `find`, `edit`, `delete`, `sort`, and `help`.
- Edit user profile fields (`edituser name|number|email`) with validation and retry limits.
- Reject duplicate records and duplicate bullets to keep content clean.
- Auto-load and persist data in `records.txt` (skips file writes when no content changes).
- Generate grouped resume output with user details and a derived skills section (`generate`).

## Quick Start

Prerequisite: Java 17.

1. Clone this repository.
2. Run the app in development mode:
   ```bash
   ./gradlew run
   ```
3. Or build a runnable jar:
   ```bash
   ./gradlew shadowJar
   java -jar build/libs/duke.jar
   ```

## Common Commands

- `help`
- `project TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`
- `experience TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`
- `cca TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`
- `list [TYPE]` where `TYPE` can be `E`, `C`, or `P` (case-insensitive)
- `show RECORD_INDEX`
- `find KEYWORD`
- `findbullet KEYWORD`
- `addbullet RECORD_INDEX / BULLET_TEXT`
- `edit RECORD_INDEX [NEW_TITLE] [/role NEW_ROLE] [/tech NEW_TECH] [/from YYYY-MM] [/to YYYY-MM]`
- `editbullet RECORD_INDEX BULLET_INDEX / NEW_BULLET_TEXT`
- `movebullet RECORD_INDEX FROM_BULLET_INDEX TO_BULLET_INDEX`
- `delete RECORD_INDEX`
- `deletebullet RECORD_INDEX BULLET_INDEX`
- `sort`
- `edituser FIELD`
- `generate`
- `bye`

## Testing

- Run unit tests:
  ```bash
  ./gradlew test
  ```
- Run text UI tests:
  - macOS/Linux: `text-ui-test/runtest.sh`
  - Windows: `text-ui-test/runtest.bat`

## Documentation

- [Project Docs Home](docs/README.md)
- [User Guide](docs/UserGuide.md)
- [Developer Guide](docs/DeveloperGuide.md)
- [About Us](docs/AboutUs.md)

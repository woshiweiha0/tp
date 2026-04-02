# ResuMake

ResuMake is a command-line resume content manager for students and early-career applicants.
It helps users quickly create and refine resume-ready records for projects, experiences, and CCAs.

## Features

- Add timed records with structured fields (`title`, `role`, `tech`, `from`, `to`).
- Manage bullet points (`addbullet`, `editbullet`, `movebullet`, `deletebullet`).
- Edit and delete records (`edit`, `delete`).
- Search by keyword across records and bullets (`find`, `findbullet`).
- Save and load data automatically via `records.txt`.
- Generate grouped resume output (`generate`).

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

- `project TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`
- `experience TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`
- `cca TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`
- `list [E|C|P]`
- `show RECORD_INDEX`
- `addbullet RECORD_INDEX / BULLET_TEXT`
- `delete RECORD_INDEX`
- `deletebullet RECORD_INDEX BULLET_INDEX`
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

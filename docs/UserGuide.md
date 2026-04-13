# ResuMake User Guide

ResuMake is a command-line resume content manager that helps you store, edit, and organize resume records quickly.

You can manage:
- Projects
- Experiences
- CCAs
- Bullet points under each record

ResuMake runs in the terminal and stores data in `records.txt`.

---

## Why ResuMake

Many users currently manage resume content in free-form tools (Word docs, Notion pages, Google Docs, or generic notes).
Those tools are flexible, but they do not enforce consistent structure, which makes content harder to maintain over time.

ResuMake solves this by:

- enforcing a consistent command format for each record (`title`, `role`, `tech`, `from`, `to`),
- supporting quick edits and bullet operations without manual reformatting,
- enabling fast filtering and keyword search across records,
- generating grouped resume-ready output on demand.

Compared to generic document tools, ResuMake is optimized for repeatable resume-content workflows, not free-form writing.

---

## Target User Profile

ResuMake is intended for users who frequently maintain resume content, especially:
- Students preparing internship or full-time job applications.
- Early-career job seekers tracking projects, experiences, and CCAs.
- Users who prefer keyboard-driven workflows over form-based apps.

Assumed user skill level:
- Comfortable with basic command-line usage (opening a terminal and running commands).
- Able to follow structured command formats and index-based operations.
- No programming background is required beyond basic CLI familiarity.

---

## Quick Start

1. Ensure you have Java 17 installed.
2. Place the jar file in a folder.
3. Navigate to that folder in the terminal and run `java -jar ResuMake.jar`.
4. On startup, ResuMake first prints `Welcome to ResuMake`, then prints `Loaded records from file.`.
5. If user details are not loaded from file, it will prompt:
   - `Welcome! What is your name?`
   - `Next, what is your number?`
   - `Finally, what is your email?`

---

## Quick Navigation

- [help](#viewing-available-commands--help)
- [list](#viewing-records--list)
- [project](#adding-a-project--project)
- [experience](#adding-an-experience--experience)
- [cca](#adding-a-cca--cca)
- [show](#showing-one-record--show)
- [find](#finding-records-by-keyword--find)
- [findbullet](#finding-bullets-by-keyword--findbullet)
- [addbullet](#adding-a-bullet--addbullet)
- [edit](#editing-a-record--edit)
- [editbullet](#editing-a-bullet--editbullet)
- [edituser](#editing-user-details--edituser)
- [movebullet](#moving-a-bullet--movebullet)
- [delete](#deleting-a-record--delete)
- [deletebullet](#deleting-a-bullet--deletebullet)
- [sort](#sorting-records-by-title--sort)
- [generate](#generating-resume-view--generate)
- [bye](#exiting-the-program--bye)

---

## Features

> Notes:
> - Words in `UPPER_CASE` are user inputs.
> - Command keywords are case-insensitive (for example, `LIST`, `list`, `LiSt`).
> - Record and bullet indices are 1-based in user input.
> - Date format is `yyyy-MM`.
> - For `project`, `experience`, and `cca`, fields must appear in this order: `/role`, `/tech`, `/from`, `/to`.
> - After adding a `project`, `experience`, or `cca`, ResuMake asks whether you want to add bullet points immediately.
> - For `list TYPE`, valid values are `E`, `C`, `P` (case-insensitive, e.g. `list e` works).
> - Duplicate records and duplicate bullets are rejected.
> - ResuMake auto-saves to `records.txt` after command execution.

---

### Viewing available commands : `help`

Shows all available commands with their formats and short descriptions.

Format:
`help`

Example:
```text
help
```

Expected output (example):
```text
--------------------
Available commands:
help - Show all available commands.
list [TYPE] - List records; TYPE can be E, C, or P.
find KEYWORD - Find records by keyword.
...
--------------------
```

---

### Viewing records : `list`

Lists all records, or filters by type.

Format:
`list [TYPE]`

- `TYPE` is optional. Omit it to list all records.
- Valid values: `E` (Experience), `C` (CCA), `P` (Project). Case-insensitive (e.g. `list e` works).
- **Indices shown are always the actual list position**, not a re-numbered sequence within the filtered results.
  This means the number shown next to each record matches the index you would use with `show`, `edit`, `delete`, and all other commands.

Example — list all:
```text
list
```

Expected output (example):
```text
Here is a list of all records.
1. [E] Google | role: SWE Intern | tech: Python | from: 2025-12 | to: 2026-02
2. [C] Chess Club | role: President | tech: N/A | from: 2023-01 | to: 2024-01
3. [E] Meta | role: SWE Intern | tech: Java | from: 2026-05 | to: 2026-08
--------------------
```

Example — filter by type:
```text
list E
```

Expected output (example):
```text
Here is a list of E records.
1. [E] Google | role: SWE Intern | tech: Python | from: 2025-12 | to: 2026-02
3. [E] Meta | role: SWE Intern | tech: Java | from: 2026-05 | to: 2026-08
--------------------
```

Notice that `Meta` is shown as index `3`, not `2`. This is because its position in the full list is 3, and that is the index you must use with other commands.

If an invalid type is given:
```text
list X
```
```text
Error: Invalid type for list command.
Valid types: E: Experience, C: Cca, P: Project
Leave blank to list all.
```

---

### Adding a project : `project`

Adds a new project record.

Format:
`project TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`

After the project is added, ResuMake asks whether you want to add bullet points immediately.
If an identical record already exists, the command is rejected as a duplicate.

- Enter `y` to add bullets one by one.
- Enter `esc` to stop adding bullets.
- Enter `n` to skip bullet entry.

Example:
```text
project Capo CLI /role Developer /tech Java /from 2026-01 /to 2026-03
```

Example interaction if you skip bullet entry:
```text
Do you want to add bullet points? (y/n)
n
--------------------
[P] Capo CLI added
--------------------
```

Example interaction if you add bullets:
```text
Do you want to add bullet points? (y/n)
y
Enter bullet points one by one. Type "esc" to stop
Implemented persistent storage with file IO
Bullet added
esc
--------------------
[P] Capo CLI added
--------------------
```

---

### Adding an experience : `experience`

Adds a new experience record.

Format:
`experience TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`

After the experience is added, ResuMake asks whether you want to add bullet points immediately.

- Enter `y` to add bullets one by one.
- Enter `esc` to stop adding bullets.
- Enter `n` to skip bullet entry.

Example:
```text
experience Google /role SWE Intern /tech JavaScript /from 2025-12 /to 2026-02
```

Example interaction if you skip bullet entry:
```text
Do you want to add bullet points? (y/n)
n
--------------------
[E] Google added
--------------------
```

---

### Adding a CCA : `cca`

Adds a new CCA record.

Format:
`cca TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`

After the CCA is added, ResuMake asks whether you want to add bullet points immediately.

- Enter `y` to add bullets one by one.
- Enter `esc` to stop adding bullets.
- Enter `n` to skip bullet entry.

Example:
```text
cca NUS Hackers /role Core Member /tech Python /from 2025-01 /to 2026-01
```

Example interaction if you skip bullet entry:
```text
Do you want to add bullet points? (y/n)
n
--------------------
[C] NUS Hackers added
--------------------
```

---

### Showing one record : `show`

Shows one record and all its bullets.

Format:
`show RECORD_INDEX`

Example:
```text
show 1
```

Expected output (example):
```text
Showing record 1
[P] Capo CLI | role: Developer | tech: Java | from: 2026-01 | to: 2026-03
  Bullets:
  1. Implemented persistent storage with file IO
--------------------
```

If no bullets exist, it prints:
```text
  (no bullets)
```

---

### Finding records by keyword : `find`

Finds records whose title, role, tech, start date, or end date contains the keyword.

Format:
`find KEYWORD`

Example:
```text
find java
```

Expected output (example):
```text
--------------------
Matching records:
1. [P] Capo CLI | role: Developer | tech: Java | from: 2026-01 | to: 2026-03
--------------------
```

---

### Finding bullets by keyword : `findbullet`

Finds bullet points containing the keyword across all records.
For each matching record, only matching bullets are shown.

Format:
`findbullet KEYWORD`

Example:
```text
findbullet persistent
```

Expected output (example):
```text
1. [P] Capo CLI | role: Developer | tech: Java | from: 2026-01 | to: 2026-03
Bullets:
  1. Implemented persistent storage with file IO
```

---

### Adding a bullet : `addbullet`

Adds a bullet to a record.

Format:
`addbullet RECORD_INDEX / BULLET_TEXT`

Notes:
- Bullet text cannot be blank.
- Duplicate bullets within the same record are rejected.

Example:
```text
addbullet 1 / Implemented persistent storage with file IO
```

Expected output:
```text
--------------------
Added bullet to: Capo CLI
--------------------
```

---

### Editing a record : `edit`

Edits an existing record. You can update one or more fields without changing the rest.

Format:
`edit RECORD_INDEX [NEW_TITLE] [/role NEW_ROLE] [/tech NEW_TECH] [/from YYYY-MM] [/to YYYY-MM]`

Notes:
- You can provide any combination of fields.
- Fields not provided remain unchanged.
- End date cannot be before start date.

Examples:
```text
edit 1 New Capo CLI
edit 1 /role Team lead /tech Java
edit 1 /from 2025-12 /to 2026-04
edit 1 Capo CLI /role Lead Developer /tech Java /from 2026-01 /to 2026-05
```

Expected output:
```text
--------------------
Record 1 has been updated.
--------------------
```

---

### Editing a bullet : `editbullet`

Edits an existing bullet point within a record.

Format:
`editbullet RECORD_INDEX BULLET_INDEX / NEW_BULLET_TEXT`

Notes:
- Both indices are 1-based.
- `/` is required before the new bullet text.
- New bullet text cannot be blank.

Example:
```text
editbullet 1 2 / Improved performance by optimizing algorithms
```

Expected output:
```text
Edited bullet 2 in record 1
```

---

### Editing user details : `edituser`

Edits one user field used in `generate` output.

Format:
`edituser FIELD`

- `FIELD` must be `name`, `number`, or `email`.
- The current value of the field is shown before you are prompted.
- You get up to **4 attempts** to provide a valid new value. After each failed attempt, the remaining number of tries is shown.
- If all 4 attempts are exhausted, the command exits with an error. Run `edituser` again to retry.

Example — successful update:
```text
edituser name
```
```text
Current name: Alex Tan
Enter new name:
Alexis Tan
Updated name to: Alexis Tan
```

Example — failed attempts then success:
```text
edituser number
```
```text
Current number: 91234567
Enter new number:
abc
Error: Please enter a valid number. You have 3 more chances.
Enter new number:
98765432
Updated number to: 98765432
```

Example — all attempts exhausted:
```text
Current number: 91234567
Enter new number:
abc
Error: Please enter a valid number. You have 3 more chances.
Enter new number:
abc
Error: Please enter a valid number. You have 2 more chances.
Enter new number:
abc
Error: Please enter a valid number. You have 1 more chance.
Enter new number:
abc
Error: You have exhausted all your attempts. edituser exited. If you would like to try editing the user profile, enter "edituser" again.
```

If an invalid field name is given:
```text
edituser age
```
```text
Error: Invalid User Field. Must be name, number or email.
```

---

### Moving a bullet : `movebullet`

Moves a bullet from one position to another in the same record.

Format:
`movebullet RECORD_INDEX FROM_BULLET_INDEX TO_BULLET_INDEX`

Example:
```text
movebullet 1 3 1
```

Expected output:
```text
--------------------
Bullet 3 moved to position 1 in record 1.
--------------------
```

---

### Deleting a record : `delete`

Deletes a record by index.

Format:
`delete RECORD_INDEX`

- `RECORD_INDEX` must be >= 1 and within the current list size.

Example:
```text
delete 1
```

Expected output:
```text
Deleted record 1
```

---

### Deleting a bullet : `deletebullet`

Deletes one bullet from a specific record.

Format:
`deletebullet RECORD_INDEX BULLET_INDEX`

- Use `show RECORD_INDEX` first to confirm bullet numbering before deleting.

Example:
```text
deletebullet 1 2
```

Expected output:
```text
Deleted bullet 2 from record 1
```

---

### Sorting records by title : `sort`

Sorts all records alphabetically by title (case-insensitive).

Format:
`sort`

Example:
```text
sort
```

Expected output:
```text
--------------------
Records sorted alphabetically by title.
--------------------
```

---

### Generating resume view : `generate`

Displays a formatted resume view grouped into sections: your personal details, then records grouped by type (`Cca`, `Experience`, `Project`), then a `Skills` section.

- Each record is shown with all its bullet points.
- The **Skills** section is automatically derived from the `tech` fields of all stored records. You do not need to enter skills separately.

Format:
`generate`

Example:
```text
generate
```

Expected output (example):
```text
John
Number: 91234567
Email: john@example.com
--------------------
Cca
--------------------
[C] Chess Club | role: President | tech: Leadership | from: 2023-01 | to: 2024-01
  Bullets:
  1. Led weekly training sessions
--------------------
Experience
--------------------
[E] Google | role: SWE Intern | tech: Python | from: 2025-12 | to: 2026-02
  (no bullets)
--------------------
Project
--------------------
[P] Resume Builder | role: Developer | tech: Java | from: 2026-01 | to: 2026-03
  Bullets:
  1. Built parser
  2. Added tests
--------------------
Skills
--------------------
leadership, python, java
--------------------
```

---

### Exiting the program : `bye`

Exits ResuMake.

Format:
`bye`

Example:
```text
bye
```

Expected output:
```text
--------------------
bye
--------------------
```

---

## FAQ

**Q: Why do I get `Error: Please follow the correct format`?**
**A:** The command format is invalid or incomplete. Check spelling, required parameters, and field order (for add record commands: `/role /tech /from /to`).

**Q: Can I use lowercase in `list TYPE` (for example, `list e`)?**
**A:** Yes. `list TYPE` accepts `E`, `C`, and `P` in a case-insensitive way.

**Q: I edited/deleted the wrong item. Can I undo it?**
**A:** There is no undo command currently. Use `edit`/`editbullet` to manually fix, or restore from a backup of `records.txt`.

**Q: Why do `delete`/`deletebullet` sometimes show index errors?**
**A:** The index is out of range or not 1-based; run `list` or `show` first and retry with the displayed index.

---

## Command Summary

| Command | Format |
|---|---|
| `help` | `help` |
| `list` | `list` or `list TYPE` |
| `project` | `project TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM` |
| `experience` | `experience TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM` |
| `cca` | `cca TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM` |
| `show` | `show RECORD_INDEX` |
| `find` | `find KEYWORD` |
| `findbullet` | `findbullet KEYWORD` |
| `addbullet` | `addbullet RECORD_INDEX / BULLET_TEXT` |
| `edit` | `edit RECORD_INDEX [NEW_TITLE] [/role NEW_ROLE] [/tech NEW_TECH] [/from YYYY-MM] [/to YYYY-MM]` |
| `editbullet` | `editbullet RECORD_INDEX BULLET_INDEX / NEW_BULLET_TEXT` |
| `edituser` | `edituser FIELD` |
| `movebullet` | `movebullet RECORD_INDEX FROM_BULLET_INDEX TO_BULLET_INDEX` |
| `delete` | `delete RECORD_INDEX` |
| `deletebullet` | `deletebullet RECORD_INDEX BULLET_INDEX` |
| `sort` | `sort` |
| `generate` | `generate` |
| `bye` | `bye` |

---

## Saving

ResuMake saves records to:

```text
records.txt
```

Records are loaded on startup and save is attempted after each executed command.
If serialized content is unchanged (for example, read-only commands), ResuMake skips file writes.

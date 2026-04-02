# ResuMake User Guide

ResuMake is a command-line resume content manager that helps you store, edit, and organize resume records quickly.

You can manage:
- Projects
- Experiences
- CCAs
- Bullet points under each record

ResuMake runs in the terminal and stores data in `records.txt`.

---

## Quick Start

1. Ensure you have Java 17 installed.
2. Place the jar file in a folder.
3. Navigate to that folder in the terminal and run `java -jar ResuMake.jar`.
4. On startup, ResuMake first prints `Welcome to ResuMake`, then prints `Loaded records from file.`.
5. If user details are not loaded from file, it will prompt:
   - `Hello, what is your name?`
   - `Hello what is your number?`
   - `Finally, what is your email?`

---

## Quick Navigation

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
> - For `list TYPE`, valid values are uppercase `E`, `C`, `P`.
> - After command execution, ResuMake writes to storage and prints `Records saved to file.`

---

### Viewing records : `list`

Lists all records, or filters by type.

Format:
`list [TYPE]`

- `TYPE` is optional.
- Valid values: `E` (Experience), `C` (CCA), `P` (Project).

Example:
```text
list C
```

Expected output (example):
```text
Here is a list of C records.
1. [C] Chess Club | role: President | tech: N/A | from: 2023-01 | to: 2024-01
--------------------
Records saved to file.
```

---

### Adding a project : `project`

Adds a new project record.

Format:
`project TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`

Example:
```text
project Capo CLI /role Developer /tech Java /from 2026-01 /to 2026-03
```

Expected output:
```text
--------------------
[P] Capo CLI added
--------------------
Records saved to file.
```

---

### Adding an experience : `experience`

Adds a new experience record.

Format:
`experience TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`

Example:
```text
experience Google /role SWE Intern /tech JavaScript /from 2025-12 /to 2026-02
```

Expected output:
```text
--------------------
[E] Google added
--------------------
Records saved to file.
```

---

### Adding a CCA : `cca`

Adds a new CCA record.

Format:
`cca TITLE /role ROLE /tech TECH /from YYYY-MM /to YYYY-MM`

Example:
```text
cca NUS Hackers /role Core Member /tech Python /from 2025-01 /to 2026-01
```

Expected output:
```text
--------------------
[C] NUS Hackers added
--------------------
Records saved to file.
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
Records saved to file.
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
Records saved to file.
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
Records saved to file.
```

---

### Adding a bullet : `addbullet`

Adds a bullet to a record.

Format:
`addbullet RECORD_INDEX / BULLET_TEXT`

Example:
```text
addbullet 1 / Implemented persistent storage with file IO
```

Expected output:
```text
--------------------
Added bullet to: Capo CLI
--------------------
Records saved to file.
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
Records saved to file.
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
Records saved to file.
```

---

### Editing user details : `edituser`

Edits one user field used in `generate` output.

Format:
`edituser FIELD`

- `FIELD` must be `name`, `number`, or `email`.

Example:
```text
edituser name
```

Expected output (example):
```text
Current name: Alex Tan
Enter new name:
Updated name to: Alexis Tan
Records saved to file.
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
Records saved to file.
```

---

### Deleting a record : `delete`

Deletes a record by index.

Format:
`delete RECORD_INDEX`

Example:
```text
delete 1
```

Expected output:
```text
Deleted record 1
Records saved to file.
```

---

### Deleting a bullet : `deletebullet`

Deletes one bullet from a specific record.

Format:
`deletebullet RECORD_INDEX BULLET_INDEX`

Example:
```text
deletebullet 1 2
```

Expected output:
```text
Deleted bullet 2 from record 1
Records saved to file.
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
Records saved to file.
```

---

### Generating resume view : `generate`

Displays a formatted resume view with user information and all records grouped by type (`Cca`, `Experience`, `Project`).

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
[C] Chess Club | role: President | tech: None | from: 2023-01 | to: 2024-01
  Bullets:
  1. Led weekly training sessions
--------------------
Experience
--------------------
...
Records saved to file.
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
Records saved to file.
```

---

## FAQ

**Q: Why do I get `Error: Please follow the correct format`?**
**A:** The command format is invalid or incomplete. Check spelling, required parameters, and field order (for add record commands: `/role /tech /from /to`).

**Q: Why is `list e` rejected?**
**A:** `list TYPE` currently accepts uppercase type codes only: `E`, `C`, `P`.

**Q: I edited/deleted the wrong item. Can I undo it?**
**A:** There is no undo command currently. Use `edit`/`editbullet` to manually fix, or restore from a backup of `records.txt`.

---

## Command Summary

| Command | Format |
|---|---|
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

Records are loaded on startup and written after each executed command.

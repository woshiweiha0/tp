# Resumake User Guide

Resumake is a command-line resume content manager that helps you store, edit, and organize resume records quickly.

You can manage:
- Projects
- Experiences
- CCAs
- Bullet points under each record

Resumake runs in the terminal and saves your data locally in `records.txt` so records persist across sessions.

---

## Quick Start

1. Ensure you have Java 17 installed.
2. Place the jar file in an empty folder
3. Navigate to that file in the terminal and run `java -jar ResuMake.jar`

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
- [movebullet](#moving-a-bullet--movebullet)
- [delete](#deleting-a-record--delete)
- [deletebullet](#deleting-a-bullet--deletebullet)
- [sort](#sorting-records-by-title--sort)
- [generate](#generating-grouped-output--generate)
- [bye](#exiting-the-program--bye)

---

## Features

> Note:
> - Words in `UPPER_CASE` are inputs provided by you.
> - Command keywords are case-insensitive (e.g., `LIST`, `list`, `LiSt` all work).
> - Record and bullet indices are 1-based.
> - Date format is `YYYY-MM`.
> - For `project`, `experience`, and `cca`, flags must appear in this order: `/role`, `/tech`, `/from`, `/to`.
> - After each valid command, Resumake auto-saves and prints `Records saved to file.`

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
[P] Capo CLI | role: Developer | tech: Java | from: 2026-01 | to: 2026-03
Bullets:

  1. Implemented persistent storage with file IO
```

---

### Moving a bullet : `movebullet`

Moves a bullet from one position to another within the same record.

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

### Exiting the program : `bye`

Exits Resumake.

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

**Q: How do I move my Resumake data to another computer?**  
**A:** Copy the `records.txt` file from your current project folder to the same location in the new machine’s project folder.

**Q: Why does Resumake show `Unknown command.`?**  
**A:** The command format is invalid or incomplete. Check spelling, required parameters, and flag order (for add record commands: `/role /tech /from /to`).

**Q: I edited/deleted the wrong item. Can I undo it?**  
**A:** There is no undo command currently. If needed, manually fix the record using `edit`/`editbullet`, or restore from a backup copy of `records.txt`.

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
| `movebullet` | `movebullet RECORD_INDEX FROM_BULLET_INDEX TO_BULLET_INDEX` |
| `delete` | `delete RECORD_INDEX` |
| `deletebullet` | `deletebullet RECORD_INDEX BULLET_INDEX` |
| `sort` | `sort` |
| `generate` | `generate` |
| `bye` | `bye` |

---

## Saving

Resumake saves records to:

```text
records.txt
```

Records are loaded on startup and saved automatically after every valid command.

Justin's Project Portfolio Page

Project: Resumake CLI

Resumake CLI is a desktop command-line application designed to 
help users manage and structure resume content such as projects, 
experiences, and activities. The user interacts with it using a CLI, 
and it is written in Java.

Given below are my contributions to the project.

---

## New Feature: Parser for Command Interpretation

**What it does:**  
Parses raw user input into executable commands by identifying the 
command keyword and extracting relevant fields such as title, role, 
tech stack, and dates.

**Justification:**  
This feature is essential as it acts as the bridge between user input 
and application logic. Without a robust parser, commands cannot be 
interpreted correctly, making the application unusable.

**Highlights:**
- Designed a structured parsing flow to handle multiple command types 
(e.g., add, edit, delete, bullet commands).
- Implemented validation for input formats 
(e.g., date formats, required fields).
- Refactored parsing logic to use a consistent exception-based 
approach (`ResumakeException`) instead of returning `null`.
- Handled complex parsing cases such as optional fields and 
command flags (`/role`, `/tech`, `/from`, `/to`).

---

## New Feature: Add Command

**What it does:**  
Allows users to add new records (e.g., project, experience, CCA)
into the system.

**Justification:**  
This is a core feature of the application as it enables users to 
build their resume content.

**Highlights:**
- Supports structured input with multiple fields.
- Integrates with the parser to create appropriate record types.
- Ensures data integrity through validation checks.

---

## New Feature: AddBullet Command

**What it does:**  
Allows users to add bullet points to a specific record.

**Justification:**  
Bullet points are essential for detailing achievements and 
responsibilities in a resume. This feature improves usability by 
allowing incremental updates to records.

**Highlights:**
- Supports indexed access to records.
- Includes validation for index bounds and bullet content.
- Ensures bullets are properly formatted and stored.

---

## Code contributed

[RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=)

---

## Project management

- Assisted in structuring core application flow 
(Parser → Command → Execution → Storage)
- Contributed to maintaining consistency in exception 
handling across the system

---

## Enhancements to existing features

- Refactored parser to replace `null` returns 
with `ResumakeException` for better error handling
- Improved input validation across multiple commands
- Standardised command parsing logic for maintainability

---

## Documentation

### User Guide:
- Documented command formats for adding records and bullet points
- Clarified input structure and required fields

### Developer Guide:
- Added implementation details for the Parser component
- Explained command parsing workflow and design decisions

---

## Community

- Reviewed teammates’ code and provided feedback on 
command structure and parsing logic
- Contributed ideas for improving command consistency and 
error handling

---

## Tools

- Used Java logging (`Logger`) for debugging and tracing 
command execution
- Utilised JUnit for testing parser and command functionality

---
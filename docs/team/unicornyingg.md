# Unicornyingg - Project Portfolio Page

## Overview
ResuMake is a CLI-based resume content manager for students to build and refine records for projects, experiences, and CCAs.  
My main contributions focused on improving reliability in record/bullet mutation workflows (especially delete + storage), while strengthening parser/command robustness and test coverage.

## Summary of contributions

### Code contributed
- [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=Unicornyingg&tabRepo=AY2526S2-CS2113-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements implemented
1. **Deletion and bullet mutation reliability**
- Strengthened `delete RECORD_INDEX` and `deletebullet RECORD_INDEX BULLET_INDEX` execution paths.
- Improved parsing/validation of indices and error messages for invalid record/bullet references.

2. **Storage and persistence correctness**
- Improved storage parsing/serialization robustness for records and bullets.
- Documented and aligned Base64 bullet encoding behavior in persisted output (`b64:` format).
- Ensured malformed lines are skipped safely during load instead of crashing startup.
- Improved save behavior so writes are skipped when serialized content is unchanged.

3. **Parser and command-flow robustness**
- Improved parser/command integration across command formats and validation paths.
- Strengthened command error-handling consistency with clearer `ResumakeException` outcomes.
- Improved command behavior alignment for read-only vs mutating flows.

4. **User-profile and generation flow support**
- Covered/updated retry behavior for `edituser` invalid input attempts.
- Covered/updated `generate` output behavior for grouped records and auto-derived skills.

5. **Quality assurance and CI support**
- Added/updated targeted tests for parser and delete-related scenarios.
- Resolved build/checkstyle issues during integration to keep CI checks green.

### Contributions to the User Guide
- Added and refined command usage details/examples for delete and bullet workflows (`delete`, `deletebullet`, `addbullet`, `editbullet`, `movebullet`).
- Clarified index behavior for `list TYPE` and consistency with index-based commands.
- Updated `edituser` documentation with retry limits and failure behavior after exhausted attempts.
- Updated `generate` behavior documentation, including grouped output and skills derivation from `tech` values.
- Improved the `Saving` section to reflect save-on-change behavior (skip write when content is unchanged).

### Contributions to the Developer Guide
- Added and refined implementation details for delete flows and index normalization.
- Added storage-format and persistence details, including user-line parsing and Base64 bullet serialization.
- Documented add/edit/duplicate-protection behavior and read-only save-skip behavior.
- Updated sequence/class diagram coverage and architecture notes to align with command execution flow.
- Improved DG coherence by aligning described behavior with actual parser/command implementation.

### Contributions to team-based tasks
- Integrated multiple feature branches through PR merges and conflict resolution during active development.
- Helped keep project quality checks healthy (tests/checkstyle/assertion-related cleanup while integrating changes).

### Review/mentoring contributions
- Supported teammates during integration/debugging for command-flow issues (especially parser + storage related behavior).
- Coordinated branch-sync and merge conflict handling for smoother team integration.

## Contributions to the Developer Guide
- **Storage Format and Persistence**  
  Added details on the `records.txt` structure, `USER|...` parsing, Base64 bullet encoding/decoding, malformed-line skip behavior, and save-on-change write skipping.
- **Delete Feature**  
  Added deletion-mode breakdown (`delete` vs `deletebullet`), 1-based to 0-based index normalization, and explicit invalid-index exception behavior.
- **Add, Edit, and Duplicate-Protection Features**  
  Added coverage for duplicate-record rejection, immediate bullet capture flow after add (`y`/`n` and `esc`), and date-range validation constraints.
- **User Profile and Exit Features**  
  Added `edituser` retry-loop behavior (maximum attempts, remaining-attempt messaging, and exit-after-exhaustion behavior).
- **Sequence Diagrams**  
  Updated sequence documentation so flows reflect parser-command dispatch and storage save-skip behavior accurately.

## Contributions to the User Guide
- **Deleting a record: `delete`**  
  Added format requirements, index expectations, and expected successful output.
- **Deleting a bullet: `deletebullet`**  
  Added two-index command format, usage guidance, and expected output behavior.
- **Editing user details: `edituser`**  
  Added examples for retry attempts, success after retries, and command exit after maximum failed attempts.
- **Viewing records: `list`**  
  Clarified that filtered output keeps full-list indices (not re-numbered within filter results).
- **Generating resume view: `generate`**  
  Added grouped output expectations and clarified automatic skill aggregation from `tech` fields.
- **Saving**  
  Added clarification that save is attempted after command execution but file writes are skipped if serialized content has no changes.

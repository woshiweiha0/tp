# Unicornyingg - Project Portfolio Page

## Overview
ResuMake is a CLI-based resume content manager for students to build and refine records for projects, experiences, and CCAs.  
My main contributions focused on improving reliability in record/bullet mutation workflows (especially delete + storage), while strengthening parser/command robustness and test coverage.

## Summary of contributions

### Code contributed
- [RepoSense link (my contributions)](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=Unicornyingg&tabRepo=AY2526S2-CS2113-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
- RepoSense snapshot for `AY2526S2-CS2113-F09-2/tp[master]` (2026-03-06 to 2026-04-13):
- `63` commits, `+2641 / -397` lines changed.
- By file type: `docs (+1551 / -145)`, `functional-code (+755 / -220)`, `test-code (+292 / -31)`, `other (+43 / -1)`.

### Enhancements implemented
1. **Extended deletion workflows for records and bullets**
- Implemented/strengthened support for `delete RECORD_INDEX` and `deletebullet RECORD_INDEX BULLET_INDEX`.
- Improved parser-command integration for deletion command formats and index validation.
- Added clearer failure paths for invalid record/bullet indices.

2. **Improved storage robustness and persistence behavior**
- Improved storage parsing/serialization reliability for structured records and bullets.
- Strengthened load behavior to skip malformed lines instead of crashing startup.
- Improved persistence behavior so command flows remain stable across restarts.

3. **Unified command error handling and reliability**
- Integrated application-specific exception flows (`ResumakeException`) in command/storage paths.
- Improved developer-facing validation signals and runtime stability in parser/command execution.

4. **Added/updated targeted tests**
- Added/updated tests for parser and delete-related behavior, including bullet deletion and invalid-index scenarios.
- Supported CI/checkstyle quality gates while integrating these changes.

### Contributions to the User Guide
- Added/updated command behavior details for delete workflows (`delete`, `deletebullet`).
- Clarified persistence-related behavior and expected user-facing outcomes.
- Contributed updates to command descriptions and examples for changed flows.

### Contributions to the Developer Guide
- Added/updated implementation notes for deletion/storage behavior and command error-handling flows.
- Contributed to feature/design documentation alignment with actual command behavior.
- Helped improve technical documentation consistency across DG sections.

### Contributions to team-based tasks
- Integrated multiple feature branches through PR merges and conflict resolution during active development.
- Helped keep project quality checks healthy (tests/checkstyle/assertion-related cleanup while integrating changes).

### Review/mentoring contributions
- Supported teammates during integration/debugging for command-flow issues (especially parser + storage related behavior).
- Coordinated branch-sync and merge conflict handling for smoother team integration.

### Contributions beyond the project team
- No major beyond-team contributions are claimed in this PPP.

## Optional: Contributions to the Developer Guide (extracts)
- [Storage Format and Persistence](../DeveloperGuide.md#storage-format-and-persistence)
- [Delete Feature](../DeveloperGuide.md#delete-feature)
- [Add, Edit, and Duplicate-Protection Features](../DeveloperGuide.md#add-edit-and-duplicate-protection-features)
- [Instructions for Manual Testing](../DeveloperGuide.md#instructions-for-manual-testing)

## Optional: Contributions to the User Guide (extracts)
- [Deleting a record: `delete`](../UserGuide.md#deleting-a-record--delete)
- [Deleting a bullet: `deletebullet`](../UserGuide.md#deleting-a-bullet--deletebullet)
- [Saving](../UserGuide.md#saving)

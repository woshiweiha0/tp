# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

This section summarizes the key class-level structure of ResuMake.

Main components
- Resumake: The main which launches the app.
- UI: The interface which helps to display all messages in User interface.
- Parser: Helps to parse user input and returns the correct command.
- Storage: Helps us Store the data in a record txt.

### Architecture class diagram

![Architecture Class Diagram](images/ArchitectureClassDiagram.png)

The architecture centers around `Resumake`, which coordinates `Ui`, `Parser`, `Storage`, and `RecordList`.
`Parser` maps input into `Command` objects, and `Storage` persists `RecordList` data.

### Record hierarchy class diagram

![Record Hierarchy Class Diagram](images/RecordHierarchyClassDiagram.png)

`Record` is the base abstraction for resume entries and is extended by `Project`, `Experience`, and `Cca`.
Bullets are managed inside `Record`, while `RecordList` provides collection-level operations.

### Delete and storage class diagram

![Delete and Storage Class Diagram](images/DeleteStorageClassDiagram.png)

This diagram focuses on your implemented feature area:
- `Parser` creates `DeleteCommand` for `delete` and `deletebullet`.
- `DeleteCommand` updates `RecordList` (record deletion) or `Record` (bullet deletion).
- `Storage` handles persistence through `saveToFile` and `loadFromFile`.

### Command hierarchy class diagram

![Command Hierarchy Class Diagram](images/CommandHierarchyClassDiagram.png)

This diagram shows all concrete command classes that extend the `Command` base class.
It summarizes the command-oriented architecture used by `Parser`.

### Bullet and edit feature class diagram

![Bullet and Edit Feature Class Diagram](images/BulletAndEditClassDiagram.png)

This diagram captures the classes used in bullet and record editing features:
`AddBulletCommand`, `EditBulletCommand`, `MoveBulletCommand`, `EditCommand`, and `DeleteCommand`.
It also shows how these commands interact with `RecordList`, `Record`, and `ResumakeException`.

### User and exception class diagram

![User and Exception Class Diagram](images/UserAndExceptionClassDiagram.png)

This diagram focuses on user profile and exception interactions across `User`,
`EditUserCommand`, `GenerateCommand`, `Storage`, and `Resumake`.

## Product scope
### Target user profile

Resumake targets students in universities who are looking for internships or full-time positions.
It serves as a way to consolidate their projects, CCAs, and experiences, and generates a ready made
sample resume that they can use for their job interviews and applications.

### Value proposition

ResuMake manages and organises project experiences to use for job-ready resume content. 
It is optimized for students and early-career applicants who want to quickly store, edit, and organize projects, experience, and achievements through a fast CLI workflow. 
It provides structured formatting, bullet management, and storage that plain notes lack, without the friction of form-heavy resume builders.
It helps users who do not have time to think thoroughly on how their resume should look like
Or if they need a resume ready in short notice.

## User Stories

|Version| As a ... | I want to ... | So that I can ...                                              |
|--------|----------|---------------|----------------------------------------------------------------|
|v1.0|student job applicant|add a new project with a title, role, and tech stack| start building resume content from my school and personal work |
|v1.0|student job applicant|list all my saved projects| quickly review what I already have for applications            |
|v1.0|student job applicant|view the full details of a project| check whether the information is complete and accurate         |
|v1.0|student job applicant|tag a project as school, hackathon, or personal| organise my experience more clearly                            |
|v1.0|internship applicant|mark a project as planned, in-progress, or done| track which projects are ready to show employers               |
|v1.0|fresh graduate|delete outdated or weak bullet points| keep only the most relevant content                            |
|v1.0|fresh graduate|load previously saved projects when the app starts| continue working without re-entering everything                |
|v2.0|internship applicant|filter projects by tag| choose the most relevant projects for a specific internship    |
|v2.0|internship applicant|export a project's bullet points| paste them directly into my resume or application form         |
|v2.0|fresh graduate|add start and end dates to a project| show a clear timeline of my work                               |
|v2.0|career switcher|duplicate an existing project| create tailored versions for different job types               |
|v2.0|career switcher|sort projects by date| review my experience in a meaningful order                     |
|v2.0|fresh graduate|edit each aspect of a project| change projects to be better suited for job applications       |
|v2.0|student job applicant|generate tags| include skills or industries a project is related to           |



## Non-Functional Requirements

1. Performance
    - The system should respond to user commands within 1 second under normal usage
    - The application should handle at least 100 records with multiple bullets without noticeable lag
    - File saving and loading should complete within 2 seconds
2. Usability
   - The CLI commands should follow a consistent and predicatable format
   - Error messages should be clear and actionable
3. Reliability
   - The system should auto-save after every modifying command (add/edit/delete)
   - Data should persist across sessions unless manually deleted
   - The system should gracefully handle corrupted files
4. Maintainability
   - Code should follow OOP principles 
   - Each class should have a single responsibility
   - The system should be easy to extend with new commands
5. Scalability
   - The architecture should allow adding new record types with minimal changes
6. Security 
   - The app should not execute arbitrary user input as code
   - File operations should be restricted to the app's working directory

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}

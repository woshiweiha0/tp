# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

Main components
- Resumake: The main which launches the app.
- UI: The interface which helps to display all messages in User interface.
- Parser: Helps to parse user input and returns the correct command.
- Storage: Helps us Store the data in a record txt.

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

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}

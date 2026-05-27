# Prompt-EngineeringLab6

Java console-based Surveys application for Lab 6, built iteratively with prompt engineering.

## What this project includes

- `Project.java`: Main single-file surveys app with:
	- Survey creation
	- Survey listing
	- Survey answering
	- Results display
	- Persistence to `surveys.dat` using Java serialization
- `TestSurvey.java`: Basic persistence roundtrip test.

## Compile and run

Compile:

```bash
javac Project.java
```

Run (standard JVM launch):

```bash
java Project
```

Run (single-file source mode):

```bash
java Project.java
```

## Run tests

Compile app + test:

```bash
javac Project.java TestSurvey.java
```

Run test:

```bash
java TestSurvey
```

Expected output on success:

```text
TESTS PASSED
```

## Prompts used

### Prompt 1

We need to do this Lab, i will guide you to do it okey? 
First,
Create a simple Java console application named Project that implements a Surveys app.

Requirements:
- Single-file implementation `Project.java`.
- Classes: `Survey`, `Question`, `SurveyManager`, `SurveyApp`, plus a `Project` class with `main`.
- Menu: create survey, list surveys, answer survey, show results, save surveys, exit.
- Surveys have a title and a list of questions; responses are lists of answers (one per question).
- Keep code runnable with `javac Project.java` and `java Project`.

Return only the full `Project.java` file content.

### Prompt 2

perfect, now:
2) Add persistence
```
Update `Project.java` to persist surveys to `surveys.dat` using Java serialization.

- On save/exit write the `surveys` map and `nextId` with `ObjectOutputStream`.
- On startup, load `surveys.dat` with `ObjectInputStream` if present and restore state.
- Print friendly messages for save/load success or failure.

Return only the modified `Project.java`.
```

### Prompt 3

Perfect, now  Improve UX/input handling
```
Improve console UX in `Project.java`:

- When creating a survey, allow entering multiple questions; stop when user submits a blank line.
- Handle empty states gracefully (friendly messages when no surveys).
- Accept empty answers and still record them.
- Validate numeric input where needed and avoid crashes from malformed input.

Return only the updated `Project.java`.
```

### Prompt 4

Alr now Add basic tests
```
Add `TestSurvey.java`:

- Programmatically create a survey, add one response, save to disk.
- Create a fresh `SurveyManager`, load from disk, verify the survey exists and response recorded.
- Print "TESTS PASSED" on success; print an error and exit non-zero on failure.

Return the full `TestSurvey.java` file content only.
```

### Prompt 5

alr now README + prompts log
```
Update `README.md`:

- Short description of the project.
- Compile/run instructions (`javac Project.java` / `java Project` and `java Project.java`).
- Test instructions to compile/run `TestSurvey.java`.
- A "Prompts used" section containing the exact prompts i sent to you and i will add the screenshots myself

Return only the updated `README.md`.
```

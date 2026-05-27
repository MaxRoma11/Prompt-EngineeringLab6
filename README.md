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
<img width="1440" height="900" alt="Screenshot 2026-05-27 at 15 57 52" src="https://github.com/user-attachments/assets/086dfb60-76fe-4470-be16-f4de56c5c475" />


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
<img width="1440" height="900" alt="Screenshot 2026-05-27 at 15 59 39" src="https://github.com/user-attachments/assets/9fbc2041-1559-4158-96de-6a73b96a6df7" />

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
<img width="1440" height="900" alt="Screenshot 2026-05-27 at 16 16 17" src="https://github.com/user-attachments/assets/e92ba9c9-152a-41e2-ae30-59a33e260980" />


### Prompt 4

Alr now Add basic tests
```
Add `TestSurvey.java`:

- Programmatically create a survey, add one response, save to disk.
- Create a fresh `SurveyManager`, load from disk, verify the survey exists and response recorded.<img width="1440" height="900" alt="Screenshot 2026-05-27 at 16 09 53" src="https://github.com/user-attachments/assets/31fc2272-0d29-4e50-8df4-7a1d288b6f8d" />

- Print "TESTS PASSED" on success; print an error and exit non-zero on failure.

Return the full `TestSurvey.java` file content only.
```
<img width="1440" height="900" alt="Screenshot 2026-05-27 at 16 02 23" src="https://github.com/user-attachments/assets/1a71093e-1892-49bc-ba3a-e1ca827b17cb" />


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

### Prompt 6

now Short reflection paragraph
```
Write a 6–8 sentence paragraph I can paste into the README describing:
- How I used the assistant/Copilot (prompts + iterative refinement),
- What the assistant produced,
- What I validated locally (compile/run/tests),
- One or two lessons learned about prompting and a next-step extension idea.

Return only the paragraph.
```
<img width="1440" height="900" alt="Screenshot 2026-05-27 at 16 09 48" src="https://github.com/user-attachments/assets/a1d59b51-f2fe-4877-bf3e-4e7b2478a9ca" />


## Reflection

I used Copilot through a sequence of clear prompts and iterative refinements, starting from a basic single-file Java surveys app and then adding features step by step. Instead of asking for everything at once, I requested focused changes (persistence, UX improvements, tests, and documentation), which made each iteration easier to review and correct. The assistant produced `Project.java` with the required classes and menu flow, added serialization-based persistence to `surveys.dat`, and created `TestSurvey.java` for a save/load roundtrip check. I validated the output locally by compiling and running the app with `javac Project.java` and `java Project` (also `java Project.java`), and by compiling/running tests with `javac Project.java TestSurvey.java` and `java TestSurvey`. This process confirmed that the code built successfully and that persistence behavior worked as expected across program restarts. One lesson learned is that prompt specificity strongly affects quality: giving exact constraints, class names, and output format reduced ambiguity and rework. Another lesson is that incremental prompts are better than one large prompt because they support faster verification and safer changes. As a next step, I would extend the app with per-question statistics (for example counts/percentages for repeated answers) and exportable reports for survey results.

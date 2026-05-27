import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String text;

    public Question(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

class Survey implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String title;
    private final List<Question> questions;
    private final List<List<String>> responses;

    public Survey(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public List<List<String>> getResponses() {
        return Collections.unmodifiableList(responses);
    }

    public void addQuestion(String questionText) {
        questions.add(new Question(questionText));
    }

    public void addResponse(List<String> answers) {
        if (answers == null || answers.size() != questions.size()) {
            throw new IllegalArgumentException("Answers must match the number of questions.");
        }
        responses.add(new ArrayList<>(answers));
    }
}

class SurveyManager {
    private Map<Integer, Survey> surveys;
    private int nextId;

    public SurveyManager() {
        this.surveys = new LinkedHashMap<>();
        this.nextId = 1;
    }

    public int createSurvey(String title, List<String> questionTexts) {
        Survey survey = new Survey(title);
        for (String q : questionTexts) {
            survey.addQuestion(q);
        }
        int id = nextId;
        surveys.put(id, survey);
        nextId++;
        return id;
    }

    public Map<Integer, Survey> getSurveys() {
        return Collections.unmodifiableMap(surveys);
    }

    public Survey getSurvey(int id) {
        return surveys.get(id);
    }

    public void saveToFile(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(surveys);
            out.writeInt(nextId);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object loaded = in.readObject();
            if (!(loaded instanceof Map)) {
                throw new IOException("Invalid save format.");
            }

            this.surveys = (Map<Integer, Survey>) loaded;
            this.nextId = in.readInt();

            if (this.surveys == null) {
                this.surveys = new LinkedHashMap<>();
            }
            if (this.nextId < 1) {
                this.nextId = 1;
            }
        }
    }
}

class SurveyApp {
    private static final String SAVE_FILE = "surveys.dat";

    private final SurveyManager manager;
    private final Scanner scanner;

    public SurveyApp() {
        this.manager = new SurveyManager();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        loadSavedData();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    createSurvey();
                    break;
                case 2:
                    listSurveys();
                    break;
                case 3:
                    answerSurvey();
                    break;
                case 4:
                    showResults();
                    break;
                case 5:
                    saveSurveys();
                    break;
                case 6:
                    saveSurveys();
                    running = false;
                    System.out.println("Exiting app. Bye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println("=== Surveys App ===");
        System.out.println("1. Create survey");
        System.out.println("2. List surveys");
        System.out.println("3. Answer survey");
        System.out.println("4. Show results");
        System.out.println("5. Save surveys");
        System.out.println("6. Exit");
    }

    private void createSurvey() {
        System.out.print("Survey title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        List<String> questionTexts = new ArrayList<>();
        System.out.println("Enter questions one by one. Submit a blank line to finish.");
        int questionNumber = 1;
        while (true) {
            System.out.print("Question " + questionNumber + ": ");
            String q = scanner.nextLine().trim();
            if (q.isEmpty()) {
                break;
            }
            questionTexts.add(q);
            questionNumber++;
        }

        if (questionTexts.isEmpty()) {
            System.out.println("Survey not created: add at least one question.");
            return;
        }

        int surveyId = manager.createSurvey(title, questionTexts);
        System.out.println("Survey created successfully with ID " + surveyId + ".");
    }

    private void listSurveys() {
        Map<Integer, Survey> surveys = manager.getSurveys();
        if (surveys.isEmpty()) {
            System.out.println("No surveys available yet. Create one from option 1.");
            return;
        }

        System.out.println("Available surveys:");
        for (Map.Entry<Integer, Survey> entry : surveys.entrySet()) {
            Survey survey = entry.getValue();
            System.out.println(entry.getKey() + ". " + survey.getTitle()
                    + " (" + survey.getQuestions().size() + " questions, "
                    + survey.getResponses().size() + " responses)");
        }
    }

    private void answerSurvey() {
        Map<Integer, Survey> surveys = manager.getSurveys();
        if (surveys.isEmpty()) {
            System.out.println("There are no surveys to answer yet.");
            return;
        }

        listSurveys();
        int selected = readInt("Select survey ID: ");
        Survey survey = manager.getSurvey(selected);
        if (survey == null) {
            System.out.println("Invalid survey ID.");
            return;
        }

        List<String> answers = new ArrayList<>();
        System.out.println("Answering: " + survey.getTitle());
        for (Question question : survey.getQuestions()) {
            System.out.print(question.getText() + " ");
            String answer = scanner.nextLine().trim();
            answers.add(answer);
        }

        survey.addResponse(answers);
        System.out.println("Thank you! Your responses were recorded.");
    }

    private void showResults() {
        Map<Integer, Survey> surveys = manager.getSurveys();
        if (surveys.isEmpty()) {
            System.out.println("There are no surveys yet, so no results to show.");
            return;
        }

        listSurveys();
        int selected = readInt("Select survey ID: ");
        Survey survey = manager.getSurvey(selected);
        if (survey == null) {
            System.out.println("Invalid survey ID.");
            return;
        }

        System.out.println("Results for: " + survey.getTitle());
        if (survey.getResponses().isEmpty()) {
            System.out.println("No responses yet.");
            return;
        }

        System.out.println("Total responses: " + survey.getResponses().size());
        for (int i = 0; i < survey.getQuestions().size(); i++) {
            System.out.println("\nQ" + (i + 1) + ": " + survey.getQuestions().get(i).getText());
            for (int r = 0; r < survey.getResponses().size(); r++) {
                List<String> response = survey.getResponses().get(r);
                String answer = i < response.size() ? response.get(i) : "";
                System.out.println("- Response " + (r + 1) + ": " + answer);
            }
        }
    }

    private void saveSurveys() {
        try {
            manager.saveToFile(SAVE_FILE);
            System.out.println("Save successful: surveys stored in " + SAVE_FILE + ".");
        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    private void loadSavedData() {
        File saveFile = new File(SAVE_FILE);
        if (!saveFile.exists()) {
            System.out.println("No previous save file found. Starting with empty surveys.");
            return;
        }

        try {
            manager.loadFromFile(SAVE_FILE);
            System.out.println("Load successful: surveys restored from " + SAVE_FILE + ".");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load failed: " + e.getMessage());
            System.out.println("Starting with empty surveys.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}

public class Project {
    public static void main(String[] args) {
        SurveyApp app = new SurveyApp();
        app.run();
    }
}

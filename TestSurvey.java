import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestSurvey {
    private static void fail(String message) {
        System.err.println("TEST FAILED: " + message);
        System.exit(1);
    }

    public static void main(String[] args) {
        String fileName = "test-surveys.dat";

        try {
            File testFile = new File(fileName);
            if (testFile.exists() && !testFile.delete()) {
                fail("Could not clean previous test file.");
            }

            SurveyManager manager = new SurveyManager();
            List<String> questions = Arrays.asList("What is your favorite color?");
            int surveyId = manager.createSurvey("Sample Survey", questions);

            Survey survey = manager.getSurvey(surveyId);
            if (survey == null) {
                fail("Created survey was not found.");
            }

            survey.addResponse(Arrays.asList("Blue"));
            manager.saveToFile(fileName);

            SurveyManager loadedManager = new SurveyManager();
            loadedManager.loadFromFile(fileName);

            Map<Integer, Survey> loadedSurveys = loadedManager.getSurveys();
            if (!loadedSurveys.containsKey(surveyId)) {
                fail("Loaded surveys does not contain expected survey ID.");
            }

            Survey loadedSurvey = loadedManager.getSurvey(surveyId);
            if (loadedSurvey == null) {
                fail("Loaded survey is null.");
            }

            if (!"Sample Survey".equals(loadedSurvey.getTitle())) {
                fail("Loaded survey title does not match.");
            }

            if (loadedSurvey.getResponses().size() != 1) {
                fail("Expected exactly one response after loading.");
            }

            List<String> loadedResponse = loadedSurvey.getResponses().get(0);
            if (loadedResponse.size() != 1 || !"Blue".equals(loadedResponse.get(0))) {
                fail("Loaded response content does not match.");
            }

            System.out.println("TESTS PASSED");

            if (testFile.exists() && !testFile.delete()) {
                System.err.println("Warning: Could not delete temporary test file: " + fileName);
            }
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}

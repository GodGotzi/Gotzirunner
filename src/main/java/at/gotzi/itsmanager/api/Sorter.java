package at.gotzi.itsmanager.api;

import at.gotzi.itsmanager.api.workspace.StudentTestResult;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.view.TestItemView;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class Sorter {

    public static TestItemView[] sortTestsViaIndex(TestItemView[] testItemViews) {

        for (int i = 0; i < testItemViews.length-1; i++) {
            for (int j = 0; j < testItemViews.length-1; j++) {
                if (testItemViews[j].getTest().getIndex() > testItemViews[j + 1].getTest().getIndex()) {
                    TestItemView save = testItemViews[j];
                    testItemViews[j] = testItemViews[j+1];
                    testItemViews[j+1] = save;
                }
            }
        }

        return testItemViews;
    }

    public static StudentTestResult[] sortStudentResultsViaKN(List<StudentTestResult> studentResults) {
        StudentTestResult[] results = studentResults.toArray(new StudentTestResult[0]);

        for (int i = 0; i < results.length-1; i++) {
            for (int j = 0; j < results.length-1; j++) {
                if (results[j].kn() > results[j + 1].kn()) {
                    StudentTestResult save = results[j];
                    results[j] = results[j+1];
                    results[j+1] = save;
                }
            }
        }

        return results;
    }

    private static int findHighestIndex(List<Test> testList) {
        int num = 0;
        for (Test test : testList) {
            if (num < test.getIndex())
                num = test.getIndex();
        }
        return num;
    }

    public static int getHighestJsonIndex(List<Test> testList) throws IOException, ParseException {
        if (testList.size() == 0)
            return 0;
        return findHighestIndex(testList);
    }
}

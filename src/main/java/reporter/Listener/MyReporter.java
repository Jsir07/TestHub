package reporter.Listener;

import com.aventstack.extentreports.ExtentTest;

/**
 * @Auther: jx
 * @Date: 2018/6/27 17:18
 * @Description:
 */
public class MyReporter {
    public static ExtentTest report;
    private static String testName;

    public static String getTestName() {
        return testName;
    }

    public static void setTestName(String testName) {
        MyReporter.testName = testName;
    }
}

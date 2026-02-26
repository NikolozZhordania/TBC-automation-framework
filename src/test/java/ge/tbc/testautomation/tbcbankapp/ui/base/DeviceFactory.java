package ge.tbc.testautomation.tbcbankapp.ui.base;

import org.testng.ITestContext;
import org.testng.annotations.Factory;

public class DeviceFactory {

    public DeviceFactory() {}

    @Factory
    public Object[] createTests(ITestContext context) {
        String testClassName = context.getCurrentXmlTest().getParameter("testClass");
        String browser = context.getCurrentXmlTest().getParameter("browser");
        if (browser == null) browser = "chromium";

        try {
            Class<?> clazz = Class.forName(testClassName);
            return new Object[]{
                    clazz.getConstructor(String.class, String.class).newInstance("desktop", browser),
                    clazz.getConstructor(String.class, String.class).newInstance("mobile", browser)
            };
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test instances for " + testClassName, e);
        }
    }
}
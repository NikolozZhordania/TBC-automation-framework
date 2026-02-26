package ge.tbc.testautomation.tbcbankapp.ui.base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class BaseDeviceTest extends BaseTest {

    private String device;
    private String browser;

    protected BaseDeviceTest(String device, String browser) {
        this.device = device;
        this.browser = browser;
    }

    protected BaseDeviceTest() {
    }

    @Parameters({"device", "browser"})
    @BeforeClass(alwaysRun = true)
    public void setUpDevice(
            @Optional("desktop") String device,
            @Optional("chromium") String browser) {

        if (this.device == null) {
            this.device = device;
        }
        if (this.browser == null) {
            this.browser = browser;
        }

        super.setUp(this.device, this.browser);
    }

}
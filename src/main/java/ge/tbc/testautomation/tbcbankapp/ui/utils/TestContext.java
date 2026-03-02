package ge.tbc.testautomation.tbcbankapp.ui.utils;

public class TestContext {

    private static final ThreadLocal<DeviceType> device = new ThreadLocal<>();

    public static void setDevice(DeviceType deviceType) {
        device.set(deviceType);
    }

    public static DeviceType getDevice() {
        return device.get();
    }
}


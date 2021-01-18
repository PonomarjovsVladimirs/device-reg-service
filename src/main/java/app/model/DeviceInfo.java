package app.model;

import java.io.Serializable;

public class DeviceInfo implements Serializable {

    private DeviceType deviceType;
    private String macAddress;
    private String uplinkMacAddress;

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getUplinkMacAddress() {
        return uplinkMacAddress;
    }

    public void setUplinkMacAddress(String uplinkMacAddress) {
        this.uplinkMacAddress = uplinkMacAddress;
    }

}

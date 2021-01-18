package app.model;

import java.io.Serializable;
import java.util.List;

public class DeviceResponse implements Serializable {

    public DeviceResponse(List<DeviceTree> deviceTrees){
        this.deviceTrees = deviceTrees;
    }

    private List<DeviceTree> deviceTrees;

    public List<DeviceTree> getDeviceTrees() {
        return deviceTrees;
    }

    public void setDeviceTrees(List<DeviceTree> deviceTrees) {
        this.deviceTrees = deviceTrees;
    }

}

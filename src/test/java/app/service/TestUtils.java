package app.service;

import app.model.*;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static DeviceResponse getSimpleResponse(){
        List<DeviceTree> deviceTrees = new ArrayList<>();
        DeviceNode deviceNode = createNode();
        DeviceTree deviceTree = new DeviceTree(deviceNode);
        deviceTrees.add(deviceTree);
        DeviceResponse deviceResponse = new DeviceResponse(deviceTrees);
        deviceResponse.setDeviceTrees(deviceTrees);
        return deviceResponse;
    }

    public static Device createEntity() {
        Device device = new Device();
        device.setDeviceType(DeviceType.GATEWAY);
        device.setMacAddress("mac");
        device.setUplinkMacAddress("root");
        return device;
    }

    public static Device createRootEntity() {
        Device device = new Device();
        device.setDeviceType(DeviceType.GATEWAY);
        device.setMacAddress("root");
        device.setUplinkMacAddress("null");
        return device;
    }

    public static DeviceInfo createDto() {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceType(DeviceType.GATEWAY);
        deviceInfo.setMacAddress("mac");
        deviceInfo.setUplinkMacAddress("root");
        return deviceInfo;
    }

    public static DeviceInfo createRootDto() {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceType(DeviceType.GATEWAY);
        deviceInfo.setMacAddress("root");
        deviceInfo.setUplinkMacAddress(null);
        return deviceInfo;
    }

    public static DeviceNode createNode(){
        DeviceNode deviceNode = new DeviceNode();
        deviceNode.setData(createDto());
        return deviceNode;
    }

    public static DeviceNode createRootNode(){
        DeviceNode deviceNode = new DeviceNode();
        deviceNode.setData(createRootDto());
        return deviceNode;
    }

    public static DeviceNode createNodeWithChildren(){
        DeviceNode deviceNode = new DeviceNode();
        deviceNode.setData(createRootDto());
        return deviceNode;
    }

    public static DeviceNode createUnrelatedNode(){
        DeviceNode deviceNode = new DeviceNode();
        deviceNode.setData(createRootDto());
        deviceNode.getData().setMacAddress("generic");
        deviceNode.getData().setUplinkMacAddress("generic uplink");
        return deviceNode;
    }

    public static DeviceTree createTree(){
        return new DeviceTree(createNode());
    }
}

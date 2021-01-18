package app.service;

import app.model.Device;
import app.model.DeviceInfo;
import app.model.DeviceNode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceTransformer {

    public DeviceInfo toDeviceInfo(Device device){
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceType(device.getDeviceType());
        deviceInfo.setMacAddress(device.getMacAddress());
        deviceInfo.setUplinkMacAddress(device.getUplinkMacAddress());
        return deviceInfo;
    }

    public Device toDeviceEntity(DeviceInfo deviceInfo){
        Device deviceEntity = new Device();
        deviceEntity.setDeviceType(deviceInfo.getDeviceType());
        deviceEntity.setMacAddress(deviceInfo.getMacAddress());
        deviceEntity.setUplinkMacAddress(deviceInfo.getUplinkMacAddress());
        return deviceEntity;
    }

    public DeviceNode toNode(DeviceInfo deviceInfo) {
        DeviceNode node = new DeviceNode();
        node.setData(deviceInfo);
        return node;
    }

    public DeviceNode toNode(Device device) {
        DeviceNode node = new DeviceNode();
        node.setData(toDeviceInfo(device));
        return node;
    }

    public List<DeviceNode> entitiesToNodes(List<Device> deviceEntities){
        return deviceEntities.stream()
                .map(this::toDeviceInfo)
                .map(this::toNode)
                .collect(Collectors.toList());
    }

}

package app.service;

import app.model.Device;
import app.model.DeviceInfo;
import app.model.DeviceNode;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static app.service.TestUtils.createDto;
import static app.service.TestUtils.createEntity;

@SpringBootTest
public class DeviceTransformerTest {

    @InjectMocks
    DeviceTransformer deviceTransformer;

    @Test
    public void shouldReturnDto() {
        Device device = createEntity();
        DeviceInfo deviceInfo = deviceTransformer.toDeviceInfo(device);
        Assert.assertEquals("device type should be GATEWAY", device.getDeviceType(), deviceInfo.getDeviceType());
        Assert.assertEquals("mac address should be mac", device.getMacAddress(), deviceInfo.getMacAddress());
        Assert.assertEquals("uplink address should be root", device.getUplinkMacAddress(), deviceInfo.getUplinkMacAddress());
    }

    @Test
    public void shouldReturnEntity() {
        DeviceInfo deviceInfo = createDto();
        Device device = deviceTransformer.toDeviceEntity(deviceInfo);
        Assert.assertEquals("device type should be GATEWAY", device.getDeviceType(), deviceInfo.getDeviceType());
        Assert.assertEquals("mac address should be mac", device.getMacAddress(), deviceInfo.getMacAddress());
        Assert.assertEquals("uplink address should be root", device.getUplinkMacAddress(), deviceInfo.getUplinkMacAddress());
    }

    @Test
    public void shouldReturnNodeFromEntity(){
        Device device = createEntity();
        DeviceNode deviceNode = deviceTransformer.toNode(device);
        Assert.assertEquals("device type should be GATEWAY", device.getDeviceType(), deviceNode.getData().getDeviceType());
        Assert.assertEquals("mac address should be mac", device.getMacAddress(), deviceNode.getData().getMacAddress());
        Assert.assertEquals("uplink address should be root", device.getUplinkMacAddress(), deviceNode.getData().getUplinkMacAddress());

    }

    @Test
    public void shouldReturnNodeFromDto(){
        DeviceInfo deviceInfo = createDto();
        DeviceNode deviceNode = deviceTransformer.toNode(deviceInfo);
        Assert.assertEquals("device type should be GATEWAY", deviceInfo.getDeviceType(), deviceNode.getData().getDeviceType());
        Assert.assertEquals("mac address should be mac", deviceInfo.getMacAddress(), deviceNode.getData().getMacAddress());
        Assert.assertEquals("uplink address should be root", deviceInfo.getUplinkMacAddress(), deviceNode.getData().getUplinkMacAddress());
    }

    @Test
    public void shouldReturnListOfNodesFromListOfEntities(){
        List<DeviceNode> deviceNodes = deviceTransformer.entitiesToNodes(Arrays.asList(createEntity(), createEntity()));
        Assert.assertEquals("three nodes should be returned", 2, deviceNodes.size());
        Assert.assertEquals("mac address should be mac", "mac", deviceNodes.get(0).getData().getMacAddress());
        Assert.assertEquals("mac address should be mac", "mac", deviceNodes.get(1).getData().getMacAddress());
    }

}

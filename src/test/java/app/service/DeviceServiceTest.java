package app.service;

import app.model.Device;
import app.model.DeviceNode;
import app.model.DeviceTree;
import app.repository.DeviceRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static app.service.TestUtils.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeviceServiceTest {

    @Mock
    DeviceRepository deviceRepository;
    @Mock
    DeviceTransformer deviceTransformer;
    @Mock
    TopologyService topologyService;
    @InjectMocks
    DeviceService deviceService;

    @Test
    public void shouldInvokeCorrectMethods(){
        DeviceService spy = Mockito.spy(deviceService);
        String macAddress = "macAddress";
        List<DeviceNode> topologyNodes = Arrays.asList(createNode(), createNode());
        List<DeviceNode> eachDeviceNodes = Arrays.asList(createNode(), createNode(), createNode());
        List<DeviceNode> oneDeviceNodes = Collections.singletonList(createNode());
        List<DeviceNode> oneDeviceTopology = Collections.singletonList(createRootNode());

        doReturn(topologyNodes).when(spy).getTopology(null);
        doReturn(eachDeviceNodes).when(spy).getEachDevice();
        doReturn(oneDeviceNodes).when(spy).getDevice(macAddress);
        doReturn(oneDeviceTopology).when(spy).getTopology(macAddress);

        List<DeviceNode> expectedTopology = spy.getDevices(null, true);
        List<DeviceNode> expectedEach = spy.getDevices(null, false);
        List<DeviceNode> expectedOneDevice = spy.getDevices(macAddress, false);
        List<DeviceNode> expectedOneDeviceTopology = spy.getDevices(macAddress,true);

        Assert.assertEquals("actual nodes should be equal to expected", topologyNodes, expectedTopology);
        Assert.assertEquals("actual nodes should be equal to expected", expectedEach, eachDeviceNodes);
        Assert.assertEquals("actual nodes should be equal to expected", oneDeviceNodes, expectedOneDevice);
        Assert.assertEquals("actual nodes should be equal to expected", oneDeviceTopology, expectedOneDeviceTopology);
    }

    @Test
    public void shouldReturnSingleDeviceNode(){
        Optional<Device> device = Optional.of(createEntity());
        DeviceNode deviceNode = createNode();
        List<DeviceNode> expected = Collections.singletonList(deviceNode);
        when(deviceRepository.findByMacAddress("macAddress")).thenReturn(device);
        when(deviceTransformer.toNode(device.get())).thenReturn(deviceNode);
        List<DeviceNode> actual = deviceService.getDevice("macAddress");
        Assert.assertEquals("result size should be 1", 1, actual.size());
        Assert.assertEquals("actual result should be equal to expected", actual, expected);
    }

    @Test
    public void shouldReturnAllDevices(){
        List<Device> devices = Arrays.asList(createEntity(),createEntity(),createEntity());
        List<DeviceNode> expected = Arrays.asList(createNode(),createNode(),createNode());
        when(deviceRepository.findEachDevice()).thenReturn(devices);
        when(deviceTransformer.entitiesToNodes(devices)).thenReturn(expected);
        List<DeviceNode> actual = deviceService.getEachDevice();
        Assert.assertEquals("result size should be 3", 3, actual.size());
        Assert.assertEquals("actual result should be equal to expected", actual, expected);
    }

    @Test
    public void shouldReturnAllRootAddresses(){
        List<Device> devices = Arrays.asList(createEntity(),createEntity());
        List<String> expected = Arrays.asList(createEntity().getMacAddress(), createEntity().getMacAddress());
        when(deviceRepository.getRootDevices()).thenReturn(devices);
        List<String> actual = deviceService.getRootAddresses();
        Assert.assertEquals("result size should be 2", 2, actual.size());
        Assert.assertEquals("actual result should be equal to expected", expected, actual);
    }

    @Test
    public void shouldReturnTrees(){
        List<DeviceNode> deviceNodes = Arrays.asList(createNode(),createNode());
        List<DeviceTree> actual = deviceService.createTrees(deviceNodes);
        Assert.assertEquals("result size should be 2", 2, actual.size());
        Assert.assertEquals("first element of the tree should be first node", deviceNodes.get(0), actual.get(0).getRoot());
        Assert.assertEquals("second element of the tree should be second node", deviceNodes.get(1), actual.get(1).getRoot());
    }

    @Test
    public void shouldReturnFullTopology(){
        DeviceService spy = Mockito.spy(deviceService);
        Device root1 = createEntity();
        root1.setMacAddress("root1");
        Device root2 = createEntity();
        root2.setMacAddress("root2");
        List<DeviceNode> topologies = Arrays.asList(createRootNode(), createRootNode());
        DeviceNode populatedNode = createNode();
        List<DeviceNode> populatedNodes = Arrays.asList(populatedNode, populatedNode);
        List<Device> rootDevices = Arrays.asList(root1,root2);
        List<String> rootNodeAddresses = Arrays.asList("root1", "root2");
        doReturn(rootNodeAddresses).when(spy).getRootAddresses();
        doReturn(Optional.of(rootDevices)).when(deviceRepository).getDeviceTopology("root1");
        doReturn(Optional.of(rootDevices)).when(deviceRepository).getDeviceTopology("root2");
        doReturn(topologies).when(deviceTransformer).entitiesToNodes(rootDevices);
        doReturn(populatedNode).when(topologyService).getPopulatedRoot("root1",topologies);
        doReturn(populatedNode).when(topologyService).getPopulatedRoot("root2",topologies);
        List<DeviceNode> actual = spy.getTopology(null);
        Assert.assertEquals("actual result should be equal to expected" , populatedNodes, actual);
    }

    @Test
    public void shouldReturnSingleDeviceTopology(){
        DeviceService spy = Mockito.spy(deviceService);
        String macAddress = "root";
        Device rootDevice = createEntity();
        rootDevice.setMacAddress(macAddress);
        List<DeviceNode> topologies = Collections.singletonList(createRootNode());
        DeviceNode populatedNode = createNode();
        List<DeviceNode> populatedNodes = Collections.singletonList(populatedNode);
        List<String> rootNodeAddresses = Collections.singletonList(macAddress);
        List<Device> rootDevices = Collections.singletonList(rootDevice);
        doReturn(rootNodeAddresses).when(spy).getRootAddresses();
        doReturn(Optional.of(rootDevice)).when(deviceRepository).findByMacAddress(macAddress);
        doReturn(Optional.of(rootDevices)).when(deviceRepository).getDeviceTopology(macAddress);
        doReturn(topologies).when(deviceTransformer).entitiesToNodes(rootDevices);
        doReturn(populatedNode).when(topologyService).getPopulatedRoot(macAddress,topologies);
        List<DeviceNode> actual = spy.getTopology(macAddress);
        Assert.assertEquals("actual result should be equal to expected" , populatedNodes, actual);
    }
}

package app.service;

import app.model.DeviceNode;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static app.service.TestUtils.*;
import static app.service.TestUtils.createNode;

@SpringBootTest
public class TopologyServiceTest {

    @InjectMocks
    TopologyService topologyService;

    @Test
    public void shouldReturnRootNode(){
        DeviceNode rootNode = createRootNode();
        List<DeviceNode> nodeList = Arrays.asList(createNode(),createNode(),createNode(),createNode(),rootNode);
        DeviceNode actualResult = topologyService.findRootNode("root",nodeList);
        Assert.assertEquals("root mac address should be root", "root", actualResult.getData().getMacAddress());
    }

    @Test
    public void shouldReturnChildrenNodes(){
        DeviceNode rootNode = createRootNode();
        List<DeviceNode> actualResult = topologyService.gatherChildrenNodes(rootNode, Arrays.asList(createNode(),createNode(),createUnrelatedNode()));
        Assert.assertEquals("should have two children",  2, actualResult.size());
        Assert.assertEquals("macAddress should be mac", "mac",actualResult.get(0).getData().getMacAddress());
        Assert.assertEquals("macAddress should be mac", "mac",actualResult.get(1).getData().getMacAddress());
    }

    @Test
    public void shouldPopulateNodes(){
        List<DeviceNode> actualResult = topologyService.gatherPopulatedNodes(Arrays.asList(createNode(),createNode(),createRootNode(),createUnrelatedNode()));
        Assert.assertEquals("first node should have no children", 0, actualResult.get(0).getChildren().size());
        Assert.assertEquals("second node should have no children", 0, actualResult.get(1).getChildren().size());
        Assert.assertEquals("third node should have two children", 2,actualResult.get(2).getChildren().size());
        Assert.assertEquals("fourth node should have no children", 0,actualResult.get(3).getChildren().size());
    }

    @Test
    public void shouldReturnPopulatedRootNode(){
        List<DeviceNode> allNodes = Arrays.asList(createNode(),createNode(), createRootNode());
        String rootAddress = "root";
        DeviceNode rootNode = topologyService.getPopulatedRoot(rootAddress, allNodes);
        Assert.assertEquals("rootNode should have two children", 2, rootNode.getChildren().size());
        Assert.assertEquals("rootNode should have mac address root", "root", rootNode.getData().getMacAddress());
        Assert.assertEquals("child one should have uplink address root", "root", rootNode.getChildren().get(0).getData().getUplinkMacAddress());
        Assert.assertEquals("child two should have uplink address root", "root", rootNode.getChildren().get(1).getData().getUplinkMacAddress());
    }
}

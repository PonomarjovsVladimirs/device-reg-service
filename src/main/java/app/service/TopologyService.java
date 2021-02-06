package app.service;

import app.model.DeviceNode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopologyService {

    /** returns populated root node for tree structure */
    public DeviceNode getPopulatedRoot(String rootAddress,List<DeviceNode> treeNodes) {
        return findRootNode(rootAddress, gatherPopulatedNodes(treeNodes));
    }

    List<DeviceNode> gatherPopulatedNodes(List<DeviceNode> treeNodes) {
        treeNodes.forEach(node -> node.setChildren(gatherChildrenNodes(node, treeNodes)));
        return treeNodes;
    }

    List<DeviceNode> gatherChildrenNodes(DeviceNode node, List<DeviceNode> treeNodes) {
        return treeNodes.stream()
                .filter(d -> node.getData().getMacAddress().equals(d.getData().getUplinkMacAddress()))
                .collect(Collectors.toList());
    }

    DeviceNode findRootNode(String rootAddress, List<DeviceNode> treeNodes){
        return treeNodes.stream()
                .filter(n -> rootAddress.equals(n.getData().getMacAddress()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}

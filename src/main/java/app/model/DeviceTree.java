package app.model;

public class DeviceTree{
    DeviceNode root;

    public DeviceTree(DeviceNode node) {
       this.root = node;
    }

    public DeviceNode getRoot(){
        return root;
    }
}

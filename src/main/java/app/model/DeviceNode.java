package app.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class DeviceNode {
    DeviceInfo data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<DeviceNode> children;

    public DeviceInfo getData() {
        return data;
    }

    public void setData(DeviceInfo data) {
        this.data = data;
    }

    public List<DeviceNode> getChildren() {
        return children;
    }

    public void setChildren(List<DeviceNode> children) {
        this.children = children;
    }
}

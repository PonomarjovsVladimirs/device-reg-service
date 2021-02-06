package app.service;

import app.model.Device;
import app.model.*;

import app.repository.DeviceRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceTransformer deviceTransformer;
    @Autowired
    private TopologyService topologyService;

    /** writes a device into DB */
    public Device registerDevice(DeviceInfo device) {
        return deviceRepository.save(deviceTransformer.toDeviceEntity(device));
    }

    /** Returns devices. The following responses are based on params:
     * @param macAddress - if present - specific device will be returned, if not - all devices
     * @param tree - if present - response will be of tree structure
     */
    public DeviceResponse getDevicesResponse(String macAddress, Boolean tree) {
        return new DeviceResponse(createTrees(gatherDevices(macAddress, tree)));
    }

    List<DeviceNode> gatherDevices(String macAddress, Boolean tree) {
        return tree ? gatherTopology(macAddress) :
                StringUtils.isBlank(macAddress) ?
                        findEachDevice() :
                        findDevice(macAddress);
    }

    List<DeviceNode> gatherTopology(String macAddress) {
        return findRoots(macAddress).stream()
                .map(r -> topologyService.getPopulatedRoot(r, deviceTransformer.entitiesToNodes(deviceRepository.getDeviceTopology(r)
                        .orElseThrow(() -> new UnExistingDeviceException(r)))))
                .collect(Collectors.toList());
    }

    List<DeviceNode> findDevice(String macAddress) {
        return Collections.singletonList(deviceTransformer
                .toNode(deviceRepository.findByMacAddress(macAddress).orElseThrow(() -> new UnExistingDeviceException(macAddress))));
    }

    List<DeviceNode> findEachDevice() {
        return deviceTransformer.entitiesToNodes(deviceRepository.findEachDevice());
    }

    List<DeviceTree> createTrees(List<DeviceNode> nodes) {
        return nodes.stream().map(DeviceTree::new).collect(Collectors.toList());
    }

    List<String> findRoots(String macAddress) {
        return StringUtils.isBlank(macAddress) ?
                findRootAddresses() :
                Collections.singletonList((deviceRepository.findByMacAddress(macAddress)
                        .orElseThrow(() -> new UnExistingDeviceException(macAddress))
                        .getMacAddress()));

    }

    List<String> findRootAddresses() {
        return deviceRepository.getRootDevices()
                .stream()
                .map(Device::getMacAddress)
                .collect(Collectors.toList());
    }
}

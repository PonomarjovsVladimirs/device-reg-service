package app.controller;

import app.model.Device;
import app.model.DeviceInfo;
import app.model.DeviceResponse;
import app.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceRegController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping(value = "/devices", consumes = "application/json", produces = "application/json")
    public Device registerDevice(@RequestBody DeviceInfo deviceData){
        return deviceService.registerDevice(deviceData);
    }

    @GetMapping(value = "/devices", produces = "application/json")
    public DeviceResponse getDevices(@RequestParam(required = false, defaultValue = "false") Boolean tree){
        return deviceService.getDevicesResponse(null,tree);
    }

    @GetMapping(value = "/devices/{macAddress}", produces = "application/json")
    public DeviceResponse getDevices(@PathVariable("macAddress") String macAddress, @RequestParam(required = false, defaultValue = "false") Boolean tree){
        return deviceService.getDevicesResponse(macAddress, tree);
    }
}

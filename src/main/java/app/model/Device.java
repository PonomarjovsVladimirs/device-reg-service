package app.model;

import javax.persistence.*;

@Entity
@Table(name = "DEVICES", schema = "DEVICES")
public class Device {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DEVICE_TYPE")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "MAC_ADDRESS")
    private String macAddress;

    @Column(name = "UPLINK_MAC_ADDRESS")
    private String uplinkMacAddress;

    public Device() {
        super();
    }

    public Device(Long id, DeviceType deviceType, String macAddress, String uplinkMacAddress) {
        this.id = id;
        this.deviceType = deviceType;
        this.macAddress = macAddress;
        this.uplinkMacAddress = uplinkMacAddress;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getUplinkMacAddress() {
        return uplinkMacAddress;
    }

    public void setUplinkMacAddress(String uplinkMacAddress) {
        this.uplinkMacAddress = uplinkMacAddress;
    }




}

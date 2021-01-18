package app.repository;

import app.model.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {

    @Query(value = "select * from devices.devices ORDER BY (\n" +
            "    CASE device_type\n" +
            "    \n" +
            "    WHEN 'GATEWAY'\n" +
            "    THEN 1\n" +
            "    \n" +
            "    WHEN 'SWITCH'\n" +
            "    THEN 2\n" +
            "    \n" +
            "    WHEN 'ACCESS POINT'\n" +
            "    THEN 3\n" +
            "    END\n" +
            ") ASC", nativeQuery = true)
    List<Device> findEachDevice();

    @Query(value= "select * from devices.devices where uplink_mac_address is null", nativeQuery = true)
    List<Device> getRootDevices();

    @Query(value = "with recursive topology as (\n" +
            "  select id, device_type,mac_address, uplink_mac_address\n" +
            "  from devices.devices\n" +
            "  where mac_address = :macAddress\n" +
            "  union all \n" +
            "  select c.id, c.device_type, c.mac_address, c.uplink_mac_address\n" +
            "  from devices.devices c\n" +
            "    join topology p on c.uplink_mac_address = p.mac_address\n" +
            ")\n" +
            "select *\n" +
            "from topology;\n", nativeQuery = true)
    Optional<List<Device>> getDeviceTopology(String macAddress);

    Optional<Device> findByMacAddress(String macAddress);

}

package app.controller;

import app.model.Device;
import app.service.DeviceService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static app.service.TestUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceRegControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private DeviceService deviceServiceMock;

    @Test
    public void shouldReturnDeviceTreesNoInput() throws Exception {
        Mockito.when(deviceServiceMock.getDevicesResponse(null, false)).thenReturn(getSimpleResponse());
        mvc.perform(MockMvcRequestBuilders.get("/device")
                .param("tree", "false")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceTrees").exists());
    }

    @Test
    public void shouldReturnDeviceTreesWithInput() throws Exception {
        Mockito.when(deviceServiceMock.getDevicesResponse("root", false)).thenReturn(getSimpleResponse());
        mvc.perform(MockMvcRequestBuilders.get("/device/root")
                .param("tree", "false")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceTrees").exists());
    }

    @Test
    public void shouldReturnOkForPost() throws Exception {
        Device device = createEntity();
        mvc.perform(MockMvcRequestBuilders.post("/device")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(device)))
                .andExpect(status().isOk());
    }
}

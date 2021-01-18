package app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeviceType {
    @JsonProperty("Gateway")
    GATEWAY,
    @JsonProperty("Switch")
    SWITCH,
    @JsonProperty("Access point")
    ACCESS_POINT
}

package app.service;

public class UnExistingDeviceException extends RuntimeException{

    UnExistingDeviceException(String s){
        super("Device does not exist" + ": " + s);
    }
}

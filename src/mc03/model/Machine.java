package mc03.model;

import mc03.Constants;

public class Machine {
    private String machineLocation;
    private String ipAddress;
    private int portNum;

    public Machine(String machineLocation, String ipAddress) {
        this.machineLocation = machineLocation;
        this.ipAddress = ipAddress;

        switch (machineLocation) {
            case Constants.CENTRAL:
                portNum = Constants.PORT_CENTRAL;
                break;
            case Constants.MARINDUQUE:
                portNum = Constants.PORT_MARINDUQUE;
                break;
            case Constants.PALAWAN:
                portNum = Constants.PORT_PALAWAN;
                break;
        }
    }

    public String getMachineLocation() {
        return machineLocation;
    }

    public void setMachineLocation(String machineLocation) {
        this.machineLocation = machineLocation;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPortNum() {
        return portNum;
    }

    public void setPortNum(int portNum) {
        this.portNum = portNum;
    }
}

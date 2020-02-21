package transport4future.tokenManagement;

import java.util.Date;

public class TokenRequest {

    private String deviceName;
    private Date requestDate;
    private String serialNumber;
    private String macAddress;

    /**
     * Default constructor with params.
     * @author d3sd1
     * @param deviceName The name of the device.
     * @param creationDate The creation date of the token.
     * @param serialNumber Token SN.
     * @param macAddress Associated MAC address.
     */
    public TokenRequest(String deviceName, Date creationDate,
                        String serialNumber, String macAddress) {
        this.deviceName = deviceName;
        this.requestDate = creationDate;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return "TokenRequest [\\n\\Device Name=" + this.deviceName
                + ",\n\t\\Request Date=" + this.requestDate + ",\n\t\\Serial Number="
                + this.serialNumber + ",\n\t\\MAC Address=" + this.macAddress + "\n]";
    }
}

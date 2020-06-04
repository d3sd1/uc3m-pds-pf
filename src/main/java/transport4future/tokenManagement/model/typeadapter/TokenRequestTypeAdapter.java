package transport4future.tokenManagement.model.typeadapter;

import transport4future.tokenManagement.config.RegexConstants;
import transport4future.tokenManagement.model.TokenRequest;
import transport4future.tokenManagement.model.skeleton.TransportTypeAdapter;
import transport4future.tokenManagement.service.PatternChecker;
import transport4future.tokenManagement.service.TypeChecker;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * The type Token request type adapter.
 */
public class TokenRequestTypeAdapter extends TypeAdapter<TokenRequest> implements TransportTypeAdapter<TokenRequest> {

    /**
     * From json to object.
     * we must check for integers since gson reflection does not divide strings nor ints, it threats it as the same
     *
     * @param reader JsonReader inhetired from Gson's TypeAdapter.
     * @return T obj filled.
     * @throws IOException If there is any issue.
     */
    @Override
    public TokenRequest read(JsonReader reader) throws IOException {
        try {
            reader.beginObject();
        } catch (IllegalStateException e) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        String fieldname = null;
        reader.setLenient(false);
        String deviceName = "",
                typeOfDevice = "",
                driverVersion = "",
                supportEmail = "",
                serialNumber = "",
                macAddress = "";
        boolean foundDeviceName = false,
                foundTypeOfDevice = false,
                foundDriverVersion = false,
                foundSupportEmail = false,
                foundSerialNumber = false,
                foundMacAddress = false;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if (fieldname == null) {
                throw new JsonSyntaxException("Unexpected errorr on TokenRequestDeserializer.");
            } else if (fieldname.equals("Device Name")) {
                deviceName = reader.nextString();
                foundDeviceName = true;
            } else if (fieldname.equals("Type of Device")) {
                typeOfDevice = reader.nextString();
                foundTypeOfDevice = true;
            } else if (fieldname.equals("Driver Version")) {
                driverVersion = reader.nextString();
                foundDriverVersion = true;
            } else if (fieldname.equals("Support e-mail")) {
                supportEmail = reader.nextString();
                foundSupportEmail = true;
            } else if (fieldname.equals("Serial Number")) {
                serialNumber = reader.nextString();
                foundSerialNumber = true;
            } else if (fieldname.equals("MAC Address")) {
                macAddress = reader.nextString();
                foundMacAddress = true;
            } else {
                throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
            }
        }
        if (!foundDeviceName
                || !foundTypeOfDevice
                || !foundDriverVersion
                || !foundSupportEmail
                || !foundSerialNumber
                || !foundMacAddress) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        reader.endObject();
        TokenRequest tokenRequest = new TokenRequest(
                deviceName,
                typeOfDevice,
                driverVersion,
                supportEmail,
                serialNumber,
                macAddress
        );
        this.doConstraints(tokenRequest);
        return tokenRequest;
    }

    /**
     * Constraint checker. This function is impure, but tests are too, since Gson and tests are not
     * fully compatible, we have to do some parkour here =).
     * @param tokenRequest T obj.
     * @throws JsonSyntaxException If there is any related error.
     */
    @Override
    public void doConstraints(TokenRequest tokenRequest) throws JsonSyntaxException {

        TypeChecker typeChecker = new TypeChecker();
        if (tokenRequest.getDeviceName() == null
                && tokenRequest.getTypeOfDevice() == null
                && tokenRequest.getSerialNumber() == null
                && tokenRequest.getSupportEMail() == null
                && tokenRequest.getDriverVersion() == null
                && tokenRequest.getMacAddress() == null) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        if (tokenRequest.getDeviceName() == null
                || tokenRequest.getTypeOfDevice() == null
                || tokenRequest.getSerialNumber() == null
                || tokenRequest.getSupportEMail() == null
                || tokenRequest.getDriverVersion() == null
                || tokenRequest.getMacAddress() == null
                || typeChecker.isInteger(tokenRequest.getSerialNumber())
                || typeChecker.isInteger(tokenRequest.getDriverVersion())
                || typeChecker.isInteger(tokenRequest.getMacAddress())
                || typeChecker.isInteger(tokenRequest.getTypeOfDevice())
                || typeChecker.isInteger(tokenRequest.getSupportEMail())
                || typeChecker.isInteger(tokenRequest.getDeviceName())
        ) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        PatternChecker patternChecker = new PatternChecker();
        if (!patternChecker.checkLengthBetween(tokenRequest.getDeviceName(), 1, 20)) {
            throw new JsonSyntaxException("Error: invalid String length for device name.");
        }

        if (patternChecker.checkRegex(tokenRequest.getSerialNumber(), RegexConstants.SERIAL_NUMBER)) {
            throw new JsonSyntaxException("Error: invalid String length for serial number.");
        }
        if (!patternChecker.checkLengthBetween(tokenRequest.getDriverVersion(), 1, 25)
                || patternChecker.checkRegex(tokenRequest.getDriverVersion(), RegexConstants.DRIVER_VERSION)) {
            throw new JsonSyntaxException("Error: invalid String length for driver version.");
        }

        if (patternChecker.checkRegex(tokenRequest.getSupportEMail(), RegexConstants.EMAIL_RFC822)) {
            throw new JsonSyntaxException("Error: invalid E-mail data in JSON structure.");
        }

        if (!patternChecker.checkValueInAccepted(tokenRequest.getTypeOfDevice(), RegexConstants.VALID_TYPE_OF_DEVICE)) {
            throw new JsonSyntaxException("Error: invalid type of sensor.");
        }

        if (patternChecker.checkRegex(tokenRequest.getMacAddress(), RegexConstants.MAC_ADDRESS)) {
            throw new JsonSyntaxException("Error: invalid MAC Address data in JSON structure.");
        }
    }

    /**
     * From object to json.
     * @param writer JsonWriter inhetired from Gson's TypeAdapter.
     * @param tokenRequest T object
     * @throws IOException If there is any issue.
     */
    @Override
    public void write(JsonWriter writer, TokenRequest tokenRequest) throws IOException {
        writer.beginObject();
        writer.name("Device Name");
        writer.value(tokenRequest.getDeviceName());
        writer.name("Type of Device");
        writer.value(tokenRequest.getTypeOfDevice());
        writer.name("Driver Version");
        writer.value(tokenRequest.getDriverVersion());
        writer.name("Support e-mail");
        writer.value(tokenRequest.getSupportEMail());
        writer.name("Serial Number");
        writer.value(tokenRequest.getSerialNumber());
        writer.name("MAC Address");
        writer.value(tokenRequest.getMacAddress());
        writer.endObject();
    }
}
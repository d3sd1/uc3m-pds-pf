package Transport4Future.TokenManagement;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.controller.TokenManager;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExecuteActionTest {

    private final TokenManager myManager;

    public ExecuteActionTest() {
        myManager = TokenManager.getInstance();
    }

    private void resetTokenStore() throws TokenManagementException {
        String storePath = Constants.TOKEN_STORAGE_FILE;
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(storePath);
            fileWriter.close();
        } catch (IOException e) {
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }

    private void insertFirstToken () throws TokenManagementException {
        this.resetTokenStore();
        String InputFile = System.getProperty("user.dir") + "/TestData/TokenRequestTest/CorrectTokenRequest.json";
        myManager.RequestToken(InputFile);
    }

    private void insertSecondToken () throws TokenManagementException {
        String InputFile = System.getProperty("user.dir") + "/TestData/TokenRequestTest/SecondCorrectTokenRequest.json";
        myManager.RequestToken(InputFile);
    }


    @Test
    @Order(1)
    @DisplayName("RF05 - EA1-Actuator - Operación SendInformationFromSensor como actuator")
    void ea1Actuator() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/SensorOpWithActuator.json");
        });
        assertEquals ("La operación solicitada no puede ser realizada con el token adjuntado a la solicitud.",ex.getMessage());
    }

    @Test
    @Order(2)
    @DisplayName("RF05 - EA1-Sensor - Operación SendInformationFromSensor como sensor")
    void ea1Sensor() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        boolean res = myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/SensorOpWithSensor.json");
        assertEquals(true, res);
    }

    @Test
    @Order(3)
    @DisplayName("RF05 - EA2-Actuator - Operación SendRequestToActuator como actuator")
    void ea2Actuator() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        boolean res = myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/SendRequestOpWithActuator.json");
        assertEquals(true, res);
    }
    @Test
    @Order(4)
    @DisplayName("RF05 - EA2-Sensor - Operación SendRequestToActuator como sensor")
    void ea2Sensor() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/SendRequestOpWithSensor.json");
        });
        assertEquals ("La operación solicitada no puede ser realizada con el token adjuntado a la solicitud.",ex.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("RF05 - EA3-Actuator - Operación CheckState como actuator")
    void ea3Sensor() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        boolean res = myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/CheckStateOpWithActuator.json");
        assertEquals(true, res);
    }
    @Test
    @Order(6)
    @DisplayName("RF05 - EA3-Sensor - Operación CheckState como sensor")
    void ea3Actuator() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        boolean res = myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/CheckStateOpWithSensor.json");
        assertEquals(true, res);
    }
    @Test
    @Order(7)
    @DisplayName("RF05 - EA4 - Sin tipo de operación")
    void ea4() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/InvalidCaseNoOp.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }
    @Test
    @Order(8)
    @DisplayName("RF05 - EA5 - Tipo de operación inválido")
    void ea5() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/InvalidCaseOpRandom.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }
    @Test
    @Order(9)
    @DisplayName("RF05 - EA5 - Tipo de operación inválido")
    void ea6() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/InvalidCaseNoTokenValue.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }
}

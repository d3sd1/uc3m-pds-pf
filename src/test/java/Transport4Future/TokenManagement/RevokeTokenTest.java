package Transport4Future.TokenManagement;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.controller.TokenManager;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RevokeTokenTest {
    private final TokenManager myManager;

    public RevokeTokenTest() {
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
    @DisplayName("RF04 - RT1 - Caso válido (Revocación Temporal)")
    void checkValidCaseTemporal() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        String email = myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/ValidCaseTemporal.json");
        assertEquals("autonomous@vehicle.com", email);
    }

    @Test
    @Order(2)
    @DisplayName("RF04 - RT2 - Caso válido (Revocación Final)")
    void checkValidCaseFinal() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        String email = myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/SensorOpWithActuator.json");
        assertEquals("autonomous@vehicle.com", email);
    }

    @Test
    @Order(3)
    @DisplayName("RF04 - RT3 - Token revocado de la misma manera (temporal)")
    void tokenAlreadyRevokedTemporal() throws TokenManagementException {
        //TODO: pasar del tokenRequest a Token el tipo
    }


    @Test
    @Order(4)
    @DisplayName("RF04 - RT4 - Token revocado de la misma manera (final)")
    void tokenAlreadyRevokedFinal() throws TokenManagementException {
        //TODO: pasar del tokenRequest a Token el tipo
    }


    @Test
    @Order(5)
    @DisplayName("RF04 - RT5 - Token a revocar no existe")
    void tokenToRevokeDoesNotExists() throws TokenManagementException {
        this.resetTokenStore();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/TokenDoesNotExists.json");
        });
        assertEquals ("El token que se quiere revocar no existe.",ex.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("RF04 - RT6 - Token a revocar existe pero está expirado")
    void tokenToRevokeExpired() throws TokenManagementException {
        //TODO
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/TokenExpired.json");
        });
        assertEquals ("El token que se quiere revocar ya ha caducado.",ex.getMessage());
    }


    @Test
    @Order(7)
    @DisplayName("RF04 - RT7 - Motivo de expiración del token demasiado largo")
    void tokenRevokeReasonTooLarge() throws TokenManagementException {
        //TODO
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/TokenExpired.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }


    @Test
    @Order(8)
    @DisplayName("RF04 - RT8 - Tipo de revocación del token inválido")
    void tokenRevokeTypeInvalid() throws TokenManagementException {
        //TODO
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/TokenExpired.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }

}

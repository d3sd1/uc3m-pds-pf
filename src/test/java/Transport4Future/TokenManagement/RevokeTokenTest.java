package Transport4Future.TokenManagement;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.controller.TokenManager;
import Transport4Future.TokenManagement.database.TokensStore;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRevokeType;
import Transport4Future.TokenManagement.service.TokenCodificator;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RevokeTokenTest {
    private final TokenManager myManager;
    private final TokensStore tokensStore;

    public RevokeTokenTest() {
        myManager = TokenManager.getInstance();
        tokensStore = TokensStore.getInstance();
    }

    private void setTokenTemporal () throws TokenManagementException, NoSuchAlgorithmException {
        TokenCodificator tokenCodificator = new TokenCodificator();
        Token token = tokenCodificator.decode(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IlBEUyIsImRldmljZVR5cGUiOiJTZW5zb3IiLCJpYXQiOjE1OTA2NjQ3ODA1MzUsImV4cCI6MTY1NjI2OTU4MDUzNSwidG9rZW5SZXF1ZXN0IjoiNjNjZmNmZDc0ODBhMTY2YTU0MWFlZjYwOTIzYjk2NTkiLCJyZXF1ZXN0RGF0ZSI6IjMwLTA5LTIwMjAgMDA6MDA6MDAiLCJub3RpZmljYXRpb25FbWFpbCI6ImF1dG9ub21vdXNAdmVoaWNsZS5jb20iLCJ0b2tlblJldm9rZVR5cGUiOiJGaW5hbCIsInRva2VuUmV2b2tlUmVhc29uIjoiQSBmdWxsIGRlIG1hbmdvIGFzIGdhaXRhcyBkbyBHYWxlZ29zIn0="
        );
        token.setTokenRevokeType(TokenRevokeType.Temporal);
        this.tokensStore.add(token);
    }

    private void setTokenFinal () throws TokenManagementException, NoSuchAlgorithmException {
        TokenCodificator tokenCodificator = new TokenCodificator();
        Token token = tokenCodificator.decode(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IlBEUyIsImlhdCI6MTU5MDY2NDc4MDUzNSwiZXhwIjoxNjU2MjY5NTgwNTM1LCJ0b2tlblJlcXVlc3QiOiI1NzY1OWJiMDk3OGQ0MTBiM2I3OGE4OTJjNjA0Y2NiNCIsInJlcXVlc3REYXRlIjoiMjktMDItMjAyMCAwMDowMDowMCIsIm5vdGlmaWNhdGlvbkVtYWlsIjoiYXV0b25vbW91c0B2ZWhpY2xlLmNvbSJ9"
        );
        token.setTokenRevokeType(TokenRevokeType.Final);
        this.tokensStore.add(token);
    }

    private void addTokenExpired () throws TokenManagementException, NoSuchAlgorithmException {
        TokenCodificator tokenCodificator = new TokenCodificator();
        Token token = tokenCodificator.decode(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IlBEUyIsImRldmljZVR5cGUiOiJBY3R1YXRvciIsImlhdCI6MCwiZXhwIjoxLCJ0b2tlblJlcXVlc3QiOiI1NzY1OWJiMDk3OGQ0MTBiM2I3OGE4OTJjNjA0Y2NiNCIsInJlcXVlc3REYXRlIjoiMjktMDItMjAyMCAwMDowMDowMCIsIm5vdGlmaWNhdGlvbkVtYWlsIjoiYXV0b25vbW91c0B2ZWhpY2xlLmNvbSIsInRva2VuUmV2b2tlUmVhc29uIjoiIn0="
        );
        token.setTokenRevokeType(TokenRevokeType.Final);
        this.tokensStore.add(token);
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
        String email = myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/ValidCaseFinal.json");
        assertEquals("autonomous@vehicle.com", email);
    }

    @Test
    @Order(3)
    @DisplayName("RF04 - RT3 - Token revocado de la misma manera (temporal)")
    void tokenAlreadyRevokedTemporal() throws TokenManagementException, NoSuchAlgorithmException {
        this.resetTokenStore();
        this.setTokenTemporal();
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/InvalidCaseSameRevokeTemporal.json");
        });
        assertEquals ("El token que se quiere revocar ya está revocado en la misma modalidad.",ex.getMessage());
    }


    @Test
    @Order(4)
    @DisplayName("RF04 - RT4 - Token revocado de la misma manera (final)")
    void tokenAlreadyRevokedFinal() throws TokenManagementException, NoSuchAlgorithmException {
        this.resetTokenStore();
        this.setTokenFinal();
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/InvalidCaseSameRevokeFinal.json");
        });
        assertEquals ("El token que se quiere revocar ya está revocado en la misma modalidad.",ex.getMessage());
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
    void tokenToRevokeExpired() throws TokenManagementException, NoSuchAlgorithmException {
        this.resetTokenStore();
        this.addTokenExpired();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/TokenExpired.json");
        });
        assertEquals ("El token que se quiere revocar ya ha caducado.",ex.getMessage());
    }


    @Test
    @Order(7)
    @DisplayName("RF04 - RT7 - Motivo de expiración del token demasiado largo")
    void tokenRevokeReasonTooLarge() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/InvalidTokenRevokeMessage.json");
        });
        assertEquals ("Error: invalid reason length",ex.getMessage());
    }


    @Test
    @Order(8)
    @DisplayName("RF04 - RT8 - Tipo de revocación del token inválido")
    void tokenRevokeTypeInvalid() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/InvalidTokenRevokeType.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }

}

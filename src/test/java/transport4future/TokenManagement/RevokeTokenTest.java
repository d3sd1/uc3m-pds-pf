package transport4future.TokenManagement;

import transport4future.tokenManagement.config.Constants;
import transport4future.tokenManagement.controller.TokenManager;
import transport4future.tokenManagement.database.TokensStore;
import transport4future.tokenManagement.exception.TokenManagementException;
import transport4future.tokenManagement.model.Token;
import transport4future.tokenManagement.model.TokenRevokeType;
import transport4future.tokenManagement.service.TokenCodificator;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests para RevokeToken.
 * Denotar que se guardan en tokenStore, pero se hace flush del contenido de dicho fichero al finalizar los test.
 */
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

    /* Caso de Prueba: RT1
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Equivalencia
     * Técnica de prueba: Clases de Equivalencia
     * Resultado Esperado: autonomous@vehicle.com
     */
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

    /* Caso de Prueba: RT2
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Equivalencia
     * Técnica de prueba: Clases de Equivalencia
     * Resultado Esperado: autonomous@vehicle.com
     */
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

    /* Caso de Prueba: RT3
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Valor límite
     * Técnica de prueba: Valor Límite
     * Resultado Esperado: Excepción
     */
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


    /* Caso de Prueba: RT4
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Valor límite
     * Técnica de prueba: Valor Límite
     * Resultado Esperado: Excepción
     */
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


    /* Caso de Prueba: RT5
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Valor límite
     * Técnica de prueba: Valor Límite
     * Resultado Esperado: Excepción
     */
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

    /* Caso de Prueba: RT6
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Equivalencia
     * Técnica de prueba: Equivalencia
     * Resultado Esperado: Excepción
     */
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


    /* Caso de Prueba: RT7
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Valor límite
     * Técnica de prueba: Valor Límite
     * Resultado Esperado: Excepción
     */
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


    /* Caso de Prueba: RT8
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Valor límite
     * Técnica de prueba: Valor Límite
     * Resultado Esperado: Excepción
     */
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


    /* Caso de Prueba: RT9
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(9)
    @DisplayName("RF04 - RT9 - Sintáctico - sin Token Value")
    void syntaxNoTokenValue() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/EstructuralNoTokenValue.json");
        });
        assertEquals ("Error: invalid input data in JSON structure.",ex.getMessage());
    }


    /* Caso de Prueba: RT10
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(10)
    @DisplayName("RF04 - RT10 - Sintáctico - sin Type Of Revocation")
    void syntaxNoTypeOfRevocation() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/EstructuralNoTypeOfRevocation.json");
        });
        assertEquals ("Error: invalid input data in JSON structure.",ex.getMessage());
    }
    /* Caso de Prueba: RT11
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(11)
    @DisplayName("RF04 - RT11 - Sintáctico - sin motivo")
    void syntaxNoReason() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/EstructuralNoReason.json");
        });
        assertEquals ("Error: invalid input data in JSON structure.",ex.getMessage());
    }


    /* Caso de Prueba: RT12
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(12)
    @DisplayName("RF04 - RT12 - Sintáctico - fichero no encontrado")
    void syntaxFileNotFound() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/imlost");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }


    /* Caso de Prueba: RT13
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(13)
    @DisplayName("RF04 - RT13 - Fichero JSON vacío")
    void syntaxFileEmpty() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/Empty.json");
        });
        assertEquals ("Error: invalid input data in JSON structure.",ex.getMessage());
    }


    /* Caso de Prueba: RT14
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico <- En este caso englobamos todos los errores de sintaxis ya que tendrían el mismo comportamiento.
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(14)
    @DisplayName("RF04 - RT14 - Fichero JSON sintaxis mala")
    void syntaxWrong() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RevokeToken(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/WrongSyntax.json");
        });
        assertEquals ("Error: JSON object cannot be created due to incorrect representation",ex.getMessage());
    }

}

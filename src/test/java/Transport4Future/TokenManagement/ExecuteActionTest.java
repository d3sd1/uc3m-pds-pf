package Transport4Future.TokenManagement;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.controller.TokenManager;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

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

    /* Caso de Prueba: EA1-Actuator
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Equivalencia
     * Técnica de prueba: Clases de equivalencia
     * Resultado Esperado: Excepción
     */
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
    /* Caso de Prueba: EA1-Sensor
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Equivalencia
     * Técnica de prueba: Clases de equivalencia
     * Resultado Esperado: bool(true)
     */
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

    /* Caso de Prueba: EA2-Actuator
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Equivalencia
     * Técnica de prueba: Clases de equivalencia
     * Resultado Esperado: bool(true)
     */
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

    /* Caso de Prueba: EA2-Sensor
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Equivalencia
     * Técnica de prueba: Clases de equivalencia
     * Resultado Esperado: Excepción
     */
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


    /* Caso de Prueba: EA3-Actuator
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Equivalencia
     * Técnica de prueba: Clases de Equivalencia
     * Resultado Esperado: bool(true)
     */
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

    /* Caso de Prueba: EA3-Sensor
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Equivalencia
     * Técnica de prueba: Clases de Equivalencia
     * Resultado Esperado: bool(true)
     */
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

    /* Caso de Prueba: EA4
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Valor límite
     * Técnica de prueba: Valor límite
     * Resultado Esperado: Excepción
     */
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
    /* Caso de Prueba: EA5
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Valor límite
     * Técnica de prueba: Valor Límite
     * Resultado Esperado: Excepción
     */
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

    /* Caso de Prueba: EA6
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Valor límite
     * Técnica de prueba: Valor límite
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(9)
    @DisplayName("RF05 - EA6 - Tipo de operación inválido")
    void ea6() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/InvalidCaseNoTokenValue.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }

    /* Caso de Prueba: EA7
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Valor límite
     * Técnica de prueba: Valor límite
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(10)
    @DisplayName("RF05 - EA7 - Tipo de operación inválido")
    void ea() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/InvalidCaseNoTokenValue.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }

    /* Caso de Prueba: RT9
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(11)
    @DisplayName("RF04 - RT11 - Sintáctico - sin Token Value")
    void syntaxNoTokenValue() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/EstructuralNoTokenValue.json");
        });
        assertEquals ("Error: invalid input data in JSON structure.",ex.getMessage());
    }


    /* Caso de Prueba: RT10
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(12)
    @DisplayName("RF04 - RT12 - Sintáctico - sin Type Of Operation")
    void syntaxNoTypeOfRevocation() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenExecuteTest/EstructuralNoTypeOfOperation.json");
        });
        assertEquals ("Error: invalid input data in JSON structure.",ex.getMessage());
    }

    /* Caso de Prueba: RT12
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(13)
    @DisplayName("RF04 - RT13 - Sintáctico - fichero no encontrado")
    void syntaxFileNotFound() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/imlost");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }


    /* Caso de Prueba: RT13
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(14)
    @DisplayName("RF04 - RT14 - Fichero JSON vacío")
    void syntaxFileEmpty() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/Empty.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }


    /* Caso de Prueba: RT14
     * Clase de Equivalencia, Valor Límite o Nodo del Árbol de Derivación Asociado: Arbol derivación
     * Técnica de prueba: Análisis Sintáctico <- En este caso englobamos todos los errores de sintaxis ya que tendrían el mismo comportamiento.
     * Resultado Esperado: Excepción
     */
    @Test
    @Order(15)
    @DisplayName("RF04 - RT15 - Fichero JSON sintaxis mala")
    void syntaxWrong() throws TokenManagementException {
        this.resetTokenStore();
        this.insertFirstToken();
        this.insertSecondToken();

        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.ExecuteAction(System.getProperty("user.dir") + "/TestData/TokenRevokeTest/WrongSyntax.json");
        });
        assertEquals ("El fichero de entrada tiene algún problema de formato o de acceso.",ex.getMessage());
    }
}

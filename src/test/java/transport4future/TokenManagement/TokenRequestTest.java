package transport4future.TokenManagement;

import transport4future.tokenManagement.controller.TokenManager;
import transport4future.tokenManagement.exception.TokenManagementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenRequestTest {

    private final TokenManager myManager;

    public TokenRequestTest() {
        myManager = TokenManager.getInstance();
    }

    @DisplayName("Caso de prueba - Eliminación de Llave Inicial")
    @Test
    void inicio() {
        String InputFilePath = "./TestData/TokenRequestTest/WithoutInitialBrace.json";
		String expectedMessage = "Error: JSON object cannot be created due to incorrect representation";
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RequestToken(InputFilePath);
        });
		assertEquals (expectedMessage,ex.getMessage());

	}
	
	@DisplayName ("Invalid Test Cases")
	@ParameterizedTest(name = "{index} -with the input ''{0}'' error expected is ''{1}''") 
	@CsvFileSource(resources = "/invalidTestCasesRequestTokenTestReduced.csv")
	void InvalidTestCases(String InputFilePath, String expectedMessage) {
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
            myManager.RequestToken(InputFilePath);
        });
		assertEquals (expectedMessage,ex.getMessage());
	}
	
	@DisplayName ("Valid Test Cases")
	@ParameterizedTest(name = "{index} -with the input ''{0}'' output expected is ''{1}''")
	@CsvFileSource(resources = "/validTestCasesRequestTokenTest.csv")
	void ValidTestCases(String InputFilePath, String Result) throws TokenManagementException {
        String myResult = myManager.RequestToken(InputFilePath);
        assertEquals(Result, myResult);
    }
}

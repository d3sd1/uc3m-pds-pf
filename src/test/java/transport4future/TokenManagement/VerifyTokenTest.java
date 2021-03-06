package transport4future.TokenManagement;

import transport4future.tokenManagement.config.Constants;
import transport4future.tokenManagement.controller.TokenManager;
import transport4future.tokenManagement.exception.TokenManagementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VerifyTokenTest {
    private final TokenManager myManager;

    public VerifyTokenTest() {
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
	@DisplayName("RF03 - TC01 - Buscar un token en un almacén de tokens vacío")
	void VerifyTokenEmptyTokenStore() throws TokenManagementException {
        this.resetTokenStore();
        String tokenToVerify = "ABxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTI3OGU3ZTI3NzMyYzRlNTM5NDZjZjIwMDU4YWQ4MTM4XG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MjUtMDMtMjAyMCAxMDoyMjoyMGIzYWMwYjRkNjQyOTE1OGRhMTI4NzYxZTk4Y2U4MzE3ZmE5MjhkNDJjNzEwYTlkMDZmNjRjOTY2N2JkZDM3MWM=\"";
        boolean result = myManager.VerifyToken(tokenToVerify);
        assertEquals(false, result);
    }
	
	@Test
	@DisplayName("RF03 - TC02 - Buscar un token que no existe en un almacén de registros que contiene un elemento")
	void VerifyTokenNonExistingToken() throws TokenManagementException {
        this.insertFirstToken();
        String tokenToVerify = "ABxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTI3OGU3ZTI3NzMyYzRlNTM5NDZjZjIwMDU4YWQ4MTM4XG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MjUtMDMtMjAyMCAxMDoyMjoyMGIzYWMwYjRkNjQyOTE1OGRhMTI4NzYxZTk4Y2U4MzE3ZmE5MjhkNDJjNzEwYTlkMDZmNjRjOTY2N2JkZDM3MWM=\"";
        boolean result = myManager.VerifyToken(tokenToVerify);
        assertEquals(false, result);
    }
	
	@Test
	@DisplayName("RF03 - TC03 - Buscar un token que existe en almacén con registros")
	void VerifyTokenCorrectTest() throws TokenManagementException {
        this.insertFirstToken();
        String tokenToVerify = "QWxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTI3OGU3ZTI3NzMyYzRlNTM5NDZjZjIwMDU4YWQ4MTM4XG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MjUtMDMtMjAyMCAxMDoyMjoyMGIzYWMwYjRkNjQyOTE1OGRhMTI4NzYxZTk4Y2U4MzE3ZmE5MjhkNDJjNzEwYTlkMDZmNjRjOTY2N2JkZDM3MWM=\"";
        boolean result = myManager.VerifyToken(tokenToVerify);
        assertEquals(false, result);
    }

	@Test
	@DisplayName("RF03 - TC04 - Buscar un token cuando el almacén no existe")
	void VerifyTokenNonExitingTokenStore() throws TokenManagementException {
        String storePath = Constants.TOKEN_STORAGE_FILE;
        File file = new File(storePath);
        file.delete();
        String tokenToVerify = "ABxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTI3OGU3ZTI3NzMyYzRlNTM5NDZjZjIwMDU4YWQ4MTM4XG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MjUtMDMtMjAyMCAxMDoyMjoyMGIzYWMwYjRkNjQyOTE1OGRhMTI4NzYxZTk4Y2U4MzE3ZmE5MjhkNDJjNzEwYTlkMDZmNjRjOTY2N2JkZDM3MWM=";
        boolean result = myManager.VerifyToken(tokenToVerify);
        assertEquals(false, result);
    }
	
	@Test
	@DisplayName("RF03 - TC05 - Buscar un token válido")
	void VerifyValidToken() throws TokenManagementException {
        this.insertFirstToken();
        this.insertSecondToken();
        String tokenToVerify = "eyJhbGciOiJIUzI1NiIsInR5cCI6IlBEUyIsImRldmljZVR5cGUiOiJBY3R1YXRvciIsImlhdCI6MTU5MDY2NDc4MDUzNSwiZXhwIjoxNjU2MjY5NTgwNTM1LCJ0b2tlblJlcXVlc3QiOiI1NzY1OWJiMDk3OGQ0MTBiM2I3OGE4OTJjNjA0Y2NiNCIsInJlcXVlc3REYXRlIjoiMjktMDItMjAyMCAwMDowMDowMCIsIm5vdGlmaWNhdGlvbkVtYWlsIjoiYXV0b25vbW91c0B2ZWhpY2xlLmNvbSIsInRva2VuUmV2b2tlUmVhc29uIjoiIn0=";
        boolean result = myManager.VerifyToken(tokenToVerify);
        assertEquals(true, result);
    }
}

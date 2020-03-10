package bank.test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/*
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
*/
import bank.logic.*;

public class AccountTest {
	Account source;
	Account destination;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		source = new Account();
		destination = new Account();
	}

	@AfterEach
	public void tearDown() throws Exception {
		source = null;
		destination = null;
	}

	@Test
	public void testInit() {
		assertEquals(0.00f, source.getBalance(), 0.005f);
		assertEquals(0.00f, destination.getBalance(), 0.005f);
	}

	@Test
	public void testDeposit() {
		source.deposit(150.00f);
		assertEquals(150.00f, source.getBalance(), 0.005f);
	}

	@Test
	void testDepositNegativeAmount() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(-150.00f);
	  });
	}
	
	@Test
	void testDepositZero() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(0.00f);
	  });
	}
	
	@Test
	public void testWithdraw() {
		source.deposit(125.00f);
		source.withdraw(25.00f);
		assertEquals(100.00, source.getBalance(), 0.005f);
	}

	@Test
	void testWithdrawInsufficientFunds() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(25.00f);
	    source.withdraw(125.00f);
	  });
	}	
	
	@Test
	void testWithdrawNegativeAmount() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(125.00f);
	    source.withdraw(-25.00f);
	  });
	}	
	
	@Test
	void testWithdrawZero() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(125.00f);
	    source.withdraw(0.00f);
	  });
	}		
	
	@Test
	public void testWithdrawAllFunds() {
		source.deposit(125.00f);
		source.withdraw(125.00f);
		assertEquals(0.00f, source.getBalance(), 0.005f);
	}
	
	@Test
	public void testTransferFunds() throws BankException {
		source.deposit(125.00f);
        source.transferFunds(destination, 25.00f);
        assertEquals(100.00f, source.getBalance(), 0.005f);		
        assertEquals(25.00f, destination.getBalance(), 0.005f);
	}

	@Test                //Aquí probamos que sí se lanza la excepción
	void testTransferFunds2() {
		Assertions.assertThrows(BankException.class, () -> {
		source.deposit(125.00f);
        source.transferFunds(destination, 120.00f);
		});
	}	
	
	@Test
	void testTransferFundsException() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(125.00f);
	    source.transferFunds(destination, 200.00f);
	  });
	}			
	
	@Test
	public void testTransferFundsAfterException() throws BankException {
		source.deposit(125.00f);
		try {
			source.transferFunds(destination, 200.00f);
			fail("An exception should have been raised!");
		}
		catch(BankException exception)
		{
	    	assertEquals("Insufficient funds in the source account.", exception.toString());
		}
		
		assertEquals(125.00f, source.getBalance(), 0.005f);
		assertEquals(0.00f, destination.getBalance(), 0.005f);
	}

	@Test
	void testTransferFundsNegativeAmount() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(125.00f);
	    source.transferFunds(destination, -25.00f);
	  });
	}				

	@Test
	void testTransferFundsZero() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(125.00f);
	    source.transferFunds(destination, 0.00f);
	  });
	}					
	
	@Test
	public void testTransferFundsJustBelowTheLimit() throws BankException {
		source.deposit(125.00f);
		source.transferFunds(destination, 25.00f);
		assertEquals(100.00f, source.getBalance(), 0.005f);
		assertEquals(25.00f, destination.getBalance(), 0.005f);
	}
	

	@Test
	void testTransferFundsJustAboveTheLimit() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(125.00f);
	    source.transferFunds(destination, 115.01f);
	  });
	}						
	
	@Test
	void testTransferFundsNullDestination() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(125.00f);
	    source.transferFunds(null, 25.00f);
	  });
	}							
	
	@Test
	void testTransferFundsToSameAccount() {
	 
	  Assertions.assertThrows(BankException.class, () -> {
	    source.deposit(125.00f);
	    source.transferFunds(source, 25.00f);
	  });
	}								
}
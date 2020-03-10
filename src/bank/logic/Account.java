package bank.logic;

public class Account {
    private float balance;
    private float minimumBalance = 10.00f;

    public float getBalance() {
        return balance;
    }
    
    public void deposit(float amount) {
        balance += amount;
    }

    public void withdraw(float amount) {
        balance -= amount;
    }

    public void transferFunds(Account destination, float amount) throws BankException {	
        if (balance - amount < minimumBalance) throw new BankException();
        destination.deposit(amount);
        withdraw(amount);
    }
}
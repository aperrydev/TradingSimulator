import java.math.BigDecimal;

public class Account {

    private String firstName;
    private String lastName;
    private int age;
    private BigDecimal balance = BigDecimal.ZERO;

    Account() {
    }

    Account(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal viewBalance(){
        return balance;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Deposit amount can not be negative.");
            return;
        }
        balance = balance.add(amount);
        System.out.printf("Deposited $%.2f successfully.%n", amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Withdraw amount can not be negative.");
            return;
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("Withdraw amount must be above 0.");
            return;
        }
        if (amount.compareTo(balance) > 0) {
            System.out.println("Insufficient funds.");
            return;
        }
        balance = balance.subtract(amount);
        System.out.printf("Withdrew $%.2f successfully.%n", amount);
    }
}


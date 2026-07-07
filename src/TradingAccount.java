import java.math.BigDecimal;
import java.util.HashMap;

public class TradingAccount {
    private Account account;
    private HashMap<String, Integer> holdings;

    TradingAccount(String firstName, String lastName, int age) {
       this.account = new Account(firstName, lastName, age);
        this.holdings = new HashMap<>();
    }
    void buyStock(String ticker, int shares, BigDecimal price){
        if(price.multiply(BigDecimal.valueOf(shares)).compareTo(account.viewBalance()) > 0){
            System.out.println("Insufficient Funds");
            return;
        }
        account.withdraw(price.multiply(BigDecimal.valueOf(shares)));
        if(holdings.containsKey(ticker)){
            holdings.put(ticker, holdings.get(ticker) + shares);
        }
        else{
            holdings.put(ticker, shares);
        }

    }
    void sellStock(String ticker, int shares, BigDecimal price){
        if(holdings.containsKey(ticker)){
            if(holdings.get(ticker) >= shares){
                account.deposit(price.multiply(BigDecimal.valueOf(shares)));
                holdings.put(ticker, holdings.get(ticker)-shares);
                if(holdings.get(ticker) == 0){
                    holdings.remove(ticker);
                }
            }
            else{
                System.out.println("You do not own enough shares of that stock.");
                return;
            }
        }
        else{
            System.out.println("You do not own shares of that stock.");
            return;
        }
    }
    void viewHoldings(){
        for(HashMap.Entry<String, Integer> entry : holdings.entrySet()){
            System.out.println(entry.getKey() + " | Shares: " + entry.getValue());
        }
    }
    BigDecimal getBalance() {
        System.out.printf("Balance: $%.2f%n",account.viewBalance());
        return account.viewBalance();
    }
    void deposit(BigDecimal amount) {
        account.deposit(amount);
    }
}

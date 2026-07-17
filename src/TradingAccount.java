import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;

public class TradingAccount {
    private Account account;
    private HashMap<String, Position> holdings;
    private long id;

    long getId(){
       return id;
   }
    void setId(long id){
        this.id = id;
    }
    Collection<Position> getPositions(){
        return holdings.values();
   }
    void addPosition(Position pos){
        holdings.put(pos.getTicker(), pos);
   }
    Position getPosition(String ticker) {
        return holdings.get(ticker.trim().toUpperCase());
    }
    TradingAccount(String firstName, String lastName, int age) {
        this.account = new Account(firstName, lastName, age);
        this.holdings = new HashMap<>();
    }
    TradingAccount(long id, String firstName, String lastName, int age, BigDecimal balance) {
        this.id = id;
        this.account = new Account(firstName, lastName, age, balance);
        this.holdings = new HashMap<>();
    }
    void buyStock(String ticker, int shares, BigDecimal price){
        ticker = ticker.trim().toUpperCase();
        if (shares <= 0) {
            System.out.println("Shares must be a positive number.");
            return;
        }

        if(price.multiply(BigDecimal.valueOf(shares)).compareTo(account.viewBalance()) > 0){
            System.out.println("Insufficient Funds");
            return;
        }
        account.withdraw(price.multiply(BigDecimal.valueOf(shares)));
        if(holdings.containsKey(ticker)){
           Position pos = holdings.get(ticker);
           int oldShares = pos.getShares();
           BigDecimal oldAvg = pos.getAvgCost();

           BigDecimal oldValue = oldAvg.multiply(BigDecimal.valueOf(oldShares));
           BigDecimal newValue = price.multiply(BigDecimal.valueOf(shares));
           int totalShares = oldShares + shares;

           BigDecimal newAvg = oldValue.add(newValue).divide(BigDecimal.valueOf(totalShares), 4, RoundingMode.HALF_UP);

           pos.setShares(totalShares);
           pos.setAvgCost(newAvg);
        }
        else{
            holdings.put(ticker, new Position(ticker, shares, price));
        }

    }
    void sellStock(String ticker, int shares, BigDecimal price){

        ticker = ticker.trim().toUpperCase();
            if (shares <= 0) {
                System.out.println("Shares must be a positive number.");
                return;
            }
            if (!holdings.containsKey(ticker)) {
                System.out.println("You do not own shares of that stock.");
                return;
            }

            Position pos = holdings.get(ticker);

            if (pos.getShares() < shares) {
                System.out.println("You do not own enough shares of that stock.");
                return;
            }

               account.deposit(price.multiply(BigDecimal.valueOf(shares)));
               BigDecimal realizedPNL = price.subtract(pos.getAvgCost()).multiply(BigDecimal.valueOf(shares));
               pos.setShares(pos.getShares() - shares);
               if(pos.getShares() == 0){
                   holdings.remove(ticker);
                   System.out.println("You no longer own any shares of |" + ticker);
               }
        System.out.println("Realized P&L: $" + realizedPNL);
    }
    void viewHoldings(){
        if (holdings.isEmpty()) {
            System.out.println("You don't own any stocks yet.");
            return;
        }
        for(HashMap.Entry<String, Position> entry : holdings.entrySet()){
            Position pos = entry.getValue();
            System.out.println(pos.getTicker() + " | Shares: " + pos.getShares()
                    + " | Avg Cost: $" + pos.getAvgCost());
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

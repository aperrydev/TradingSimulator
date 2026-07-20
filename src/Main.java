import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static void intro(AccountRepository repo) throws Exception{
        System.out.println("Welcome to Tony's Trading Simulator!");
        boolean active = true;
        while(active) {
            System.out.println("What would you like to do?");
            System.out.println("1. Log-In");
            System.out.println("2. Create an account");
            System.out.println("3. Exit");
            String introInput = scanner.nextLine();
            if (!introInput.matches("\\d+")) {
                System.out.println("Please enter a number from the menu.");
                continue;
            }
            int loginChoice = Integer.parseInt(introInput);

            switch(loginChoice){

                case 1:
                    System.out.println("Enter your username: ");
                    String usernameInput = scanner.nextLine();
                    TradingAccount account = repo.loadAccount(usernameInput);
                    if(account == null){
                        System.out.println("No account found with that username");
                        break;
                    }
                    repo.loadPositions(account);
                    System.out.println("Welcome back, " +account.getFirstName()+ "!");
                    tradingMenu(account,repo);
                    break;

                case 2:
                    TradingAccount newAccount = createAccount(repo);
                    if(newAccount != null){
                        tradingMenu(newAccount, repo);
                    }
                    break;

                case 3:
                    System.out.println("Goodbye and have a nice day!");
                    active = false;
                    break;

                default:
                    System.out.println("Please pick a number 1-3");
                    break;

            }
        }
    }
    public static TradingAccount createAccount(AccountRepository repo) throws Exception{
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Would you like to create an account with us?: ");
            String choice = scanner.nextLine();
            choice = choice.toLowerCase();
            if (choice.contains("y")) {
                String firstName = "";
                String lastName = "";
                while(true){
                    System.out.print("Enter your first and last name: ");
                    String name = scanner.nextLine().trim();
                    String[] nameParts = name.split("\\s+");
                    if(nameParts.length >= 2){
                        firstName= nameParts[0];
                        lastName = nameParts[1];
                        break;
                    }
                    System.out.println("Please enter your first AND last name.");
                }
                System.out.print("Enter your age: ");
                int age = scanner.nextInt();
                scanner.nextLine();
                if (age < 16) {
                    System.out.println("You must be 16+ to create an account.\nGoodbye and have a nice day!");
                    return null;
                }
                System.out.print("Choose a username: ");
                String username = scanner.nextLine().trim();
                while (repo.usernameExists(username)) {
                    System.out.print("That username is taken. Choose another: ");
                    username = scanner.nextLine().trim();
                }
                TradingAccount account = new TradingAccount(firstName, lastName, age);
                account.deposit(new BigDecimal("10000"));
                long id = repo.createAccount(username, firstName, lastName, age, account.getBalance());
                account.setId(id);
                System.out.println("Account created! Welcome to the simulator, " + firstName + "!");
                System.out.println("You get a starting bonus of $10,000. Good luck!!!");
                return account;
            } else if (choice.contains("n")) {
                System.out.println("Goodbye and have a nice day!");
                isRunning = false;
            } else {
                System.out.print("Please enter yes or no.\n");
            }
        }
        return null;
    }
    public static void tradingMenu(TradingAccount account, AccountRepository repo) throws Exception{
        boolean active = true;
        String ticker = "";
        BigDecimal price;
        int shares;

        while(active){
            System.out.println("Tony's Trading Simulator");
            System.out.println("1. ---------Buy---------");
            System.out.println("2. ---------Sell--------");
            System.out.println("3. ----View Holdings----");
            System.out.println("4. ----View Balance-----");
            System.out.println("5. ---------Exit--------");
            System.out.print("What would you like to do today?: ");

            String tradingInput = scanner.nextLine();
            if(!tradingInput.matches("\\d+")){
                System.out.println("Please enter a number from the menu.");
                continue;
            }
            int tradingChoice = Integer.parseInt(tradingInput);

            switch(tradingChoice){


                case 1:
                    System.out.print("What stock would you like to purchase?: ");
                    ticker = scanner.nextLine();
                    try{
                        price = StockFetcher.getPrice(ticker);
                    } catch (Exception e) {
                        System.out.println("Couldn't get a price: " +e.getMessage());
                        break;
                    }

                    System.out.println(ticker +"| $" +price);
                    System.out.print("How many shares of "+ticker+" would you like to purchase?: ");
                    String sharesInput = scanner.nextLine();
                    if(!sharesInput.matches("\\d+")){
                        System.out.println("Please enter a number");
                        break;
                    }
                    shares = Integer.parseInt(sharesInput);
                    account.buyStock(ticker,shares,price);
                    repo.savePositions(account.getId(), account.getPositions());
                    repo.updateBalance(account.getId(), account.getBalance());

                    break;

                case 2:
                    System.out.print("What stock would you like to sell?: ");
                    ticker = scanner.nextLine();
                    try {
                        price = StockFetcher.getPrice(ticker);
                    } catch (Exception e) {
                        System.out.println("Couldn't get a price: " + e.getMessage());

                        break;
                    }

                    System.out.println(ticker + "| $" + price);
                    System.out.print("How many shares of " + ticker + " would you like to sell?: ");
                    String sellSharesInput = scanner.nextLine();
                    if (!sellSharesInput.matches("\\d+")) {
                        System.out.println("Please enter a number");

                        break;
                    }

                    shares = Integer.parseInt(sellSharesInput);
                    account.sellStock(ticker, shares, price);
                    repo.savePositions(account.getId(), account.getPositions());
                    repo.updateBalance(account.getId(), account.getBalance());

                    break;

                case 3:
                   account.viewHoldings();

                   break;

                case 4:
                    account.getBalance();

                    break;

                case 5:
                    System.out.println("Goodbye and have a nice day!");
                    repo.savePositions(account.getId(), account.getPositions());
                    repo.updateBalance(account.getId(), account.getBalance());
                    active = false;

                    break;

                default:
                    System.out.println("That's not a valid option, pick 1-5.");

                    break;
            }

        }
    }
    public static void main(String[] args) throws Exception {
        AccountRepository repo = new AccountRepository();
        repo.initialize();
        intro(repo);


    }
}

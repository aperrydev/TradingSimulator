import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static void intro(){
        System.out.println("Welcome to Tony's Trading Simulator!");
    }
    public static TradingAccount createAccount(){
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
                TradingAccount account = new TradingAccount(firstName, lastName, age);
                System.out.println("Account created! \nWelcome to the simulator, " + firstName + "!");
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
    public static void tradingMenu(TradingAccount account) throws Exception{
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

            String choiceInput = scanner.nextLine();
            if(!choiceInput.matches("\\d+")){
                System.out.println("Please enter a number from the menu.");
                continue;
            }
            int choice = Integer.parseInt(choiceInput);

            switch(choice){


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
                    break;

                case 3:
                   account.viewHoldings();
                   break;

                case 4:
                    account.getBalance();
                    break;

                case 5:
                    System.out.println("Goodbye and have a nice day!");
                    active = false;
                    break;

                default:
                    System.out.println("That's not a valid option, pick 1-5.");
                    break;
            }

        }
    }
    public static void main(String[] args) throws Exception {
        intro();
        TradingAccount account = createAccount();
        if(account != null){
            System.out.println("You get a starting bonus of $10,000\nGood luck!!!");
            BigDecimal bonus = new BigDecimal("10000");
            account.deposit(bonus);
            tradingMenu(account);
        }


    }
}

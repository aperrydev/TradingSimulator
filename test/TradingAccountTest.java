import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TradingAccountTest {

    @Test
    void buyingReducesBalance() {
        TradingAccount account = new TradingAccount("Test", "User", 19);
        account.deposit(new BigDecimal("10000"));

        account.buyStock("AAPL", 10, new BigDecimal("100"));

        assertEquals(0, new BigDecimal("9000").compareTo(account.getBalance()));
    }

    @Test
    void buyingTwiceAveragesCost() {
        TradingAccount account = new TradingAccount("Test", "User", 19);
        account.deposit(new BigDecimal("10000"));

        account.buyStock("AAPL", 10, new BigDecimal("100"));
        account.buyStock("AAPL", 10, new BigDecimal("200"));

        Position pos = account.getPosition("AAPL");
        assertNotNull(pos);
        assertEquals(20, pos.getShares());
        assertEquals(0, new BigDecimal("150").compareTo(pos.getAvgCost()));
    }

    @Test
    void sellingReducesShares() {
        TradingAccount account = new TradingAccount("Test", "User", 19);
        account.deposit(new BigDecimal("10000"));

        account.buyStock("AAPL", 10, new BigDecimal("100"));
        account.sellStock("AAPL", 4, new BigDecimal("150"));

        Position pos = account.getPosition("AAPL");
        assertNotNull(pos);
        assertEquals(6, pos.getShares());
        assertEquals(0, new BigDecimal("100").compareTo(pos.getAvgCost()));
    }

    @Test
    void sellingAllSharesRemovesPosition() {
        TradingAccount account = new TradingAccount("Test", "User", 19);
        account.deposit(new BigDecimal("10000"));

        account.buyStock("AAPL", 5, new BigDecimal("100"));
        account.sellStock("AAPL", 5, new BigDecimal("100"));

        assertNull(account.getPosition("AAPL"));
    }

    @Test
    void buyingNegativeSharesDoesNothing() {
        TradingAccount account = new TradingAccount("Test", "User", 19);
        account.deposit(new BigDecimal("10000"));

        account.buyStock("AAPL", -5, new BigDecimal("100"));

        assertEquals(0, new BigDecimal("10000").compareTo(account.getBalance()));
        assertNull(account.getPosition("AAPL"));
    }
    @Test
    void buyingWithInsufficientFundsDoesNothing(){
        TradingAccount account = new TradingAccount("Test", "User", 19);
        account.deposit(new BigDecimal("500"));

        account.buyStock("AAPL", 10, new BigDecimal("100"));
        assertEquals(0, new BigDecimal("500").compareTo(account.getBalance()));
        assertNull(account.getPosition("AAPL"));

    }
}

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StockFetcher {
        public StockFetcher() {
        }
        public static double getPrice(String symbol) throws Exception {
            String apiKey = "EULJ47F66P394KTM";
            String urlString = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();

            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            JSONObject json = new JSONObject(response.toString());
            JSONObject quote = json.getJSONObject("Global Quote");
            double price = quote.getDouble("05. price");
            return price;
        }
    }

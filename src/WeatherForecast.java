import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast {
    public static void main(String[] args) {
        try {
            double lat = 39.168804;
            double lon = -86.536659;
            String infoRate = "temperature_2m";
            String tempUnit = "Fahrenheit";
            String tempUnitSymbol = "F°";
            String timeZone = "EST";
            int argsI = 0;
            while (argsI < args.length) {
                if (args[argsI].equals("--latitude")) {
                    argsI++;
                    lat = Double.parseDouble(args[argsI]);
                    argsI++;
                } else if (args[argsI].equals("--longitude")) {
                    argsI++;
                    lon = Double.parseDouble(args[argsI]);
                    argsI++;
                } else if (args[argsI].equals("--unit")) {
                    argsI++;
                    if (args[argsI].equals("C")) {
                        tempUnit = "Celsius";
                        tempUnitSymbol = "C°";
                    } else if (args[argsI].equals("F")) {
                        tempUnit = "Fahrenheit";
                        tempUnitSymbol = "F°";
                    } else {
                        System.out.println("Invalid Unit");
                    }
                    argsI++;
                } else {
                    System.out.println("Invalid");
                }
            }
            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&hourly=" + infoRate + "&" +
                    "temperature_unit=" + tempUnit.toLowerCase() + "&timezone=" + timeZone;
            URL ourUrl = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) ourUrl.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder requestData = new StringBuilder();
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        } else {
                            requestData.append(line);
                        }
                    }
                    JsonElement result = JsonParser.parseString(requestData.toString());

                    JsonObject mainObj = result.getAsJsonObject().get("hourly").getAsJsonObject();
                    JsonArray times = mainObj.get("time").getAsJsonArray();
                    JsonArray temps = mainObj.get("temperature_2m").getAsJsonArray();

                    System.out.println("7-Day Forecast in " + tempUnit + ":");

                    int i = 0;
                    while (i < times.size() - 2) {
                        String thisDate = times.get(i).getAsString().substring(0, 10);
                        String thisTime = times.get(i).getAsString().substring(11);
                        double thisTemp = temps.get(i).getAsDouble();
                        System.out.println("Forecast for " + thisDate + ":");
                        System.out.println(thisTime + ": " + thisTemp + tempUnitSymbol);
                        while (!thisTime.equals("21:00")) {
                            i += 3;
                            thisTime = times.get(i).getAsString().substring(11);
                            thisTemp = temps.get(i).getAsDouble();
                            System.out.println(thisTime + ": " + thisTemp + tempUnitSymbol);
                        }
                        i += 3;
                    }
                }
            } else {
                throw new IOException("Connection failed!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

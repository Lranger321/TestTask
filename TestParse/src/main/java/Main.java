import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        StringBuilder json = new StringBuilder();
        File file = new File("tickets.json");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.lines().forEach(json::append);
        TicketList tickets = new Gson().fromJson(json.toString(), TicketList.class);
        double avgTime = tickets.getTickets()
                .stream()
                .filter(ticket -> ticket.isFromTo("Владивосток", "Тель-Авив"))
                .mapToDouble(Ticket::countTime).average().getAsDouble();
        double[] flight = tickets.getTickets().stream()
                .filter(ticket -> ticket.isFromTo("Владивосток", "Тель-Авив"))
                .mapToDouble(Ticket::countTime).toArray();
        System.out.println("Avg time: " + avgTime);
        System.out.println("90 Percentile: " + countPercentile(flight));
    }

    private static double countPercentile(double[] flight) {
        Arrays.sort(flight);
        int flightLength = flight.length;
        double n = (flightLength + 1) * 0.90;
        if (n == 1) {
            return flight[0];
        }
        if (n == flightLength) {
            return flight[flightLength - 1];
        }
        double fractional = n - (int) n;
        return flight[(int) (n - 1)] + fractional * (flight[(int) n] - flight[(int) n - 1]);
    }


}

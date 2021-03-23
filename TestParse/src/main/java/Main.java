import com.google.gson.Gson;
import java.util.List;

import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException{
        StringBuilder json = new StringBuilder();
        File file = new File("tickets.json");
        BufferedReader writer = new BufferedReader(new FileReader(file));
        writer.lines().forEach(json::append);
        TicketList tickets = new Gson().fromJson(json.toString(),TicketList.class);
        double avgTime = tickets.getTickets()
                .stream()
                .filter(ticket -> ticket.isFromTo("Владивосток","Тель-Авив"))
                .mapToDouble(Ticket::countTime).average().getAsDouble();
        double[] flight = tickets.getTickets().stream()
                .filter(ticket -> ticket.isFromTo("Владивосток","Тель-Авив"))
                .mapToDouble(Ticket::countTime).toArray();
        System.out.println("Avg time: "+avgTime);
        System.out.println("90 Percentile: "+ countPercentile(flight));
    }

    private static double countPercentile(double[] flight){
        Arrays.sort(flight);
        int flightLength = flight.length;
        double n = (flightLength+1)*0.90;
        if(n == 1){
            return flight[0];
        }
        if(n == flightLength){
            return flight[flightLength-1];
        }
        double fractional =  n - (int) n;
        return flight[(int) (n - 1)] + fractional * (flight[(int) n] - flight[(int) n -1]);
    }


}

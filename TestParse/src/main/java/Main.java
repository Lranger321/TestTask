import org.json.simple.JSONObject;

import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        File file = new File("tickets.json");
        JSONObject jsonObject = new JSONObject();

    }

    private static double countPercentile(double[] flight){
        Arrays.sort(flight);
        int flightLength = flight.length;
        double n = (flightLength+1)*90;
        if(n == 1){
            return flight[0];
        }
        if(n == flightLength){
            return flight[flightLength-1];
        }
        double fractional =  n - (int) n;
        return flight[(int) (Math.round(n) - 1)] + fractional * (flight[(int) n] - flight[(int) n -1]);
    }


}

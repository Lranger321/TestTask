import com.google.gson.annotations.SerializedName;
import lombok.Value;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Value
public class Ticket {

    String origin;

    @SerializedName("origin_name")
    String originName;

    String destination;

    @SerializedName("destination_name")
    String destinationName;

    @SerializedName("departure_date")
    String departureDate;

    @SerializedName("departure_time")
    String departureTime;

    @SerializedName("arrival_date")
    String arrivalDate;

    @SerializedName("arrival_time")
    String arrivalTime;

    String carrier;

    int stops;

    double price;

    public int countTime() {
        int departureHours = Integer.parseInt(departureTime.split(":")[0]);
        int departureMinutes = Integer.parseInt(departureTime.split(":")[1]);
        int arrivalHours = Integer.parseInt(arrivalTime.split(":")[0]);
        int arrivalMinutes = Integer.parseInt(arrivalTime.split(":")[1]);
        int time;
        if (departureHours < arrivalHours) {
            time = (arrivalHours - departureHours);
        } else {
            time = (daysBetween() * 24) - departureHours + arrivalHours;
        }
        time += ((Math.abs(departureMinutes - arrivalMinutes) > 40) ? 1 : 0);
        return time;
    }

    private int daysBetween() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
            long timestamp = (dateFormat.parse(departureDate).getTime() - (dateFormat.parse(departureDate).getTime()));
            return (int) (timestamp / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isFromTo(String from, String to) {
        if (originName.equals(from) && destinationName.equals(to)) {
            return true;
        }
        return false;
    }

}

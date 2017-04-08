package ds.wit.dylan.bandfind.Model;

import java.io.Serializable;

public class Band implements Serializable {

    public static int autoid = 1;
    public int bandId;
    public String name;
    public String genre;
    public boolean bandfav;
    public double longitude;
    public double latitude;
    public String address;
    public double price;


    public Band() {
    }

    public Band(String name, String genre, boolean fav, double latitude, double longitude, String address, double price) {
        this.bandId = autoid++;
        this.name = name;
        this.genre = genre;
        this.bandfav = fav;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Band [name = " + name + ", genre = " + genre + ", fav = " + bandfav +
                ", latitude = " + latitude + ", longitude = " + longitude + ", address = " + address + "price = " + price +"]";
    }
}

package org.example.deadCold;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.deadCold.structure.Node;

public class Main {
    private static final  double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static int GetDistance(double lon1,double lat1,double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS/1000;
        return (int) Math.round(s);
    }

    public static void main(String[] args) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("src\\data\\Cities.csv"));
        //Read CSV line by line and use the string array as you want
        String[] nextLine = reader.readNext();
        Node[] arrayNodes = new Node[1113];
        for (int i = 0; i < 1112; i++) {
            nextLine = reader.readNext();
            arrayNodes[i] = new Node(nextLine);
        }
        System.out.println(GetDistance(arrayNodes[681].getLongitude(),arrayNodes[681].getLatitude(),arrayNodes[677].getLongitude(),arrayNodes[677].getLatitude()));

    }
}

package org.example.deadCold.structure;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

public class PreparationOfInformation {
    public static JSONObject cookData(Graph graph) {
        JSONObject objectToOut = new JSONObject();
        objectToOut.put("FirstShortestWay", graph.getFirstShortestWay());
        objectToOut.put("SecondShortestWay", graph.getSecondShortestWay());
        JSONArray wayArray = new JSONArray();
        Arrays.stream(graph.getShortestWay()).forEach(item -> {
                    if (item == graph.getMatrix().size()-1) {
                        wayArray.add(graph.getNodeToDuple());
                    } else {
                        wayArray.add(item);
                    }
                }
        );
        objectToOut.put("Way", wayArray);
        return objectToOut;
    }
}

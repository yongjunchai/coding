package model;

import java.util.HashMap;
import java.util.Map;

public class Node {
    public String name;
    public int index;
    public Map<String, Integer> incomingEdges = new HashMap<>();
    public Map<String, Integer> outgoingEdges = new HashMap<>();
}

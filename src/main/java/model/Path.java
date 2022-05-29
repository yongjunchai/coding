package model;

import java.util.ArrayList;
import java.util.List;

public class Path {
    public String src;
    public String target;
    public int totalLength;
    public List<Edge> edges = new ArrayList<>();
}

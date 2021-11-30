package net.brian.islandcore.crop.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Requirement {

    List<String> permissions = new ArrayList<>();
    HashMap<String,Integer> cropsHarvest = new HashMap<>();
    HashMap<String,Integer> cropPrice = new HashMap<>();

}

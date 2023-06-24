import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class TravelMap {
    public int total = 0;
    public PriorityQueue<Trail> pq;
    public PriorityQueue<Trail> mst;
    public boolean[] marked;

    // Maps a single Id to a single Location.
    public Map<Integer, Location> locationMap = new HashMap<>();

    // List of locations, read in the given order
    public List<Location> locations = new ArrayList<>();

    // List of trails, read in the given order
    public List<Trail> trails = new ArrayList<>();

    // TODO: You are free to add more variables if necessary.

    public void initializeMap(String filename) {
        // Read the XML file and fill the instance variables locationMap, locations and trails.

        try {
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList locationList = doc.getElementsByTagName("Location");
            for (int i = 0; i < locationList.getLength(); i++) {
                Node locationNode = locationList.item(i);
                if (locationNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element locationElement = (Element) locationNode;
                    int id = Integer.parseInt(locationElement.getElementsByTagName("Id").item(0).getTextContent());
                    String name = locationElement.getElementsByTagName("Name").item(0).getTextContent();
                    Location location = new Location(name, id);
                    locations.add(location);
                    locationMap.put(id, location);
                }
            }

            NodeList nList = doc.getElementsByTagName("Trails");


            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    for(int i=0;i<eElement.getElementsByTagName("Trail").getLength();i++){
                        String[] arr = eElement.getElementsByTagName("Trail")
                                .item(i).getTextContent().split("\\s+");
                        //System.out.println("Trail : "
                          //      + arr[1] + " " + arr[2] + " " + arr[3]);

                        Trail trail = new Trail(locations.get(Integer.parseInt(arr[1])),
                                locations.get(Integer.parseInt(arr[2])),
                                Integer.parseInt(arr[3]));
                        trails.add(trail);
                        locations.get(Integer.parseInt(arr[1])).adj.add(trail);
                        locations.get(Integer.parseInt(arr[2])).adj.add(trail);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: Your code here
    }

    public List<Trail> getSafestTrails() {
        List<Trail> safestTrails = new ArrayList<>();

        Comparator<Trail> compareByDanger = new Comparator<Trail>() {
            @Override
            public int compare(Trail o1, Trail o2) {
                return Integer.compare(o1.danger,o2.danger);
            }
        };

        pq = new PriorityQueue<>(compareByDanger);
        mst = new PriorityQueue<>(compareByDanger);
        marked = new boolean[locations.size()];
        visit(0);

        while (!pq.isEmpty())
        {
            Trail e = pq.poll();
            int v = e.source.id, w = e.destination.id;
            if (marked[v] && marked[w]) continue;
            mst.add(e);
            if (!marked[v]) visit(v);
            if (!marked[w]) visit(w);
        }


        for(int i=0;i<locations.size()-1;i++){
            safestTrails.add(mst.poll());
            total+=safestTrails.get(i).danger;
        }

        // TODO: Your code here
        return safestTrails;
    }

    void visit(int v){
        marked[v] = true;
        for(Trail w : locations.get(v).adj){
            if (!marked[w.other(v)])
                pq.add(w);
        }
        // tüm edgelerini dolaşmam lazım
    }

    public void printSafestTrails(List<Trail> safestTrails) {
        // Print the given list of safest trails conforming to the given output format.
        System.out.println("Safest trails are:");
        for(Trail t : safestTrails){
            System.out.println("The trail from " + t.source.name + " to " + t.destination.name + " with danger "+t.danger);
        }
        System.out.println("Total Danger: "+total);
        // TODO: Your code here
    }
}


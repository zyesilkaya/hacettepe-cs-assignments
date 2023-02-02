import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class ListJsonReader{
     
    int id;
    String name;
    int count=0;
    Cards[] changeCards = new Cards[6];
    Cards[] communityCards = new Cards[11];
    public ListJsonReader(){

        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("C:\\Users\\y\\Documents\\NetBeansProjects\\Assignment2\\src\\assignment2\\list.json")){
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray chanceList = (JSONArray) jsonfile.get("chanceList");
            for(Object i:chanceList){
		    name = ((String)((JSONObject)i).get("item"));
		    changeCards[count]= new ChanceCard();
            count++;
            }
            count=0;
            JSONArray communityChestList = (JSONArray) jsonfile.get("communityChestList");
            for(Object i:communityChestList){
                
                name = (String)((JSONObject)i).get("item");
		communityCards[count] = new CommunityCard();
                count++;                
            }
        }catch (IOException | ParseException e){
        }
     }
}



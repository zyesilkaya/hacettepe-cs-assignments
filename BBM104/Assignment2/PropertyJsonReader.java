
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class PropertyJsonReader {

    public PropertyJsonReader(properties[] board){

        int id;
        String name;
        int cost;
        JSONParser processor = new JSONParser();
         
        try (Reader file = new FileReader("property.json")){
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray Land = (JSONArray) jsonfile.get("1");
            JSONArray Railroads = (JSONArray) jsonfile.get("2");
            JSONArray company = (JSONArray) jsonfile.get("3");
             
            for(Object i : Railroads){
                id = Integer.parseInt((String)((JSONObject)i).get("id"));   
                name = (String)((JSONObject)i).get("name");
                cost = Integer.parseInt((String)((JSONObject)i).get("cost"));
                properties obj = new Railroads(cost,name);
                
                board[id] = obj;
            }
             
            for(Object i : company){
                id = Integer.parseInt((String)((JSONObject)i).get("id"));   
                name = (String)((JSONObject)i).get("name");
                cost = Integer.parseInt((String)((JSONObject)i).get("cost"));
                properties obj = new Company(cost,name);
                board[id] = obj;
            }

            for(Object i: Land){               

                id = Integer.parseInt((String)((JSONObject)i).get("id"));   
                name = (String)((JSONObject)i).get("name");
                cost = Integer.parseInt((String)((JSONObject)i).get("cost"));
                
                properties obj = new Land(cost,name);
                board[id] = obj;
            }
             
            } catch (IOException | ParseException e){
        }
     }

}
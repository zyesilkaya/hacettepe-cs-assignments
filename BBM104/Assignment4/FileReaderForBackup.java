import javafx.scene.image.Image;

import java.io.*;
import java.util.*;

public class FileReaderForBackup {
    static int maxError;
    static int blockTime;
    static int discount;
    static String title;
    static Properties prop = new Properties();
    static String logoFile = new File("assets\\icons\\logo.png").toURI().toString(); //todo src
    public static Image logo = new Image(logoFile);

    public static void readerForBackup() throws FileNotFoundException {
        FileReader file = new FileReader("assets\\data\\backup.dat");

        Scanner sc = new Scanner(file);

        while (sc.hasNext()){
            String[] lineArray = sc.nextLine().trim().replace("\n","").split("\t");
            switch (lineArray[0]){
                case "user":
                    User.userMap.put(lineArray[1],new User(lineArray[1],lineArray[2],Boolean.parseBoolean(lineArray[4]),Boolean.parseBoolean(lineArray[3])));
                    break;
                case "film":
                    Film.filmMap.put(lineArray[1], new Film(lineArray[1],Integer.parseInt(lineArray[3]),lineArray[2]));
                    break;
                case "seat":
                    boolean disable=false;
                    User username = User.userMap.get(lineArray[5]);
                    if(username!=null) { disable = true;}
                    Hall hall = Hall.hallMap.get(lineArray[2]);
                    Hall.hallMap.get(lineArray[2]).seatArraylist.add(new Seat(Integer.parseInt(lineArray[3]), Integer.parseInt(lineArray[4]),disable,username,hall, Integer.parseInt(lineArray[6])));
                    break;
                case "hall":
                    Hall.hallMap.put(lineArray[2], new Hall(Integer.parseInt(lineArray[4]),Integer.parseInt(lineArray[5]), lineArray[2] ,Integer.parseInt(lineArray[3]), lineArray[1]));
                    break;
            }
        }

        for(Film film: Film.filmMap.values()){
            Hall.hallMap.forEach((k, v) ->{
                if(v.getShownFilm().equals(film.getName())){

                    film.hallList.put(k,v);
                }
            });
        }


        FileReader file2 = new FileReader("extras\\comments.txt");
        Scanner scan = new Scanner(file2);
        while (scan.hasNext()){
            //username,comment,star sayısı
            String[] lineArray = scan.nextLine().replace("\n","").split("\t");
            Film.filmMap.get(lineArray[3]).commentForFilm.put(lineArray[0],new Comment(lineArray[1],Integer.parseInt(lineArray[2]),lineArray[0],Film.filmMap.get(lineArray[3])));
        }
    }

    public static void propertiesReader() {
        try {
            FileInputStream fis = new FileInputStream("assets\\data\\properties.dat");
            prop.load(fis);
            title = prop.getProperty("title");
            discount =Integer.parseInt(prop.getProperty("discount-percentage"));
            blockTime = Integer.parseInt(prop.getProperty("block-time"));
            maxError = Integer.parseInt(prop.getProperty("maximum-error-without-getting-blocked"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readAllFile() {
        try {
            readerForBackup();

        } catch (FileNotFoundException e) {
            User admin = new User("admin", User.hashPassword("password"), true, true);
            User.userMap.put("admin", admin);
        }
        propertiesReader();
    }
}

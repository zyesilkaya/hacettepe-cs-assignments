import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    void writer() throws IOException {
        File file = new File("assets\\data\\backup.dat");
        FileWriter fileWriter = new FileWriter(file);

        File file2 = new File("extras\\comments.txt");
        FileWriter fileWriterComment = new FileWriter(file2);

        for(User user:User.userMap.values()){
            fileWriter.write("user"+ "\t"+user.getUsername()+ "\t"+user.getPassword()+ "\t"+user.isClubMember()+"\t"+user.isAdmin()+"\n");
        }

        try {
            for(Film film:Film.filmMap.values()){
                fileWriter.write("film" + "\t" + film.getName() + "\t" + film.getAddress() + "\t" + film.getDuration()+"\n");

                for (Hall hall : film.getHallList().values()) {
                    fileWriter.write(("hall" + "\t" + hall.getShownFilm() + "\t" + hall.getName() + "\t" + hall.getPricePerSeat() + "\t" + hall.getRow() + "\t" + hall.getCol()+"\n"));
                    for (Seat seat : hall.seatArraylist) {
                        String owner = (seat.getOwner()==null) ? "null"  :seat.getOwner().getUsername();
                        fileWriter.write("seat" + "\t" + hall.getShownFilm() + "\t" + hall.getName() + "\t" + seat.getRowIndex() + "\t" + seat.getColIndex() + "\t" +owner+ "\t" + seat.getCost()+"\n");
                    }
                }
            }

            for(Film film:Film.filmMap.values()){
                film.commentForFilm.forEach((k,comment)->{
                    try {
                        fileWriterComment.write(comment.getUsername()+"\t"+comment.getComment()+"\t"+comment.getStars()+"\t"+comment.getFilm().getName()+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        }
        catch (Exception e){

        }
        fileWriter.close();
        fileWriterComment.close();
    }
}

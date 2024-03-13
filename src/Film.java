import java.io.FileWriter;

public class Film {
    private String Name;
    private int Year;
    private String Genre; // жанр
    private int DurationOfFilm;
    private String Format;
    private int NumberOfFilm=0;
    Film (String newName, int newYear, String newGenre, int newDurationOfFilm, String newFormat, int NewNum) {
        Name=newName;
        Year=newYear;
        Genre=newGenre;
        DurationOfFilm=newDurationOfFilm;
        Format=newFormat;
        NumberOfFilm=NewNum;
    }
    public void Save (String FileName,String sep)throws Exception{
        FileWriter fin= new FileWriter(FileName,true);
        fin.write(Name+sep+Year+sep+Genre+sep+DurationOfFilm+sep+Format+sep+NumberOfFilm+"\n");
        fin.close();
    }
    public void ShowInf(){
        System.out.println("Информация о фильме №"+NumberOfFilm);
        System.out.println("Название: \""+Name+"\", год выхода: "+Year+", жанр: "+Genre);
        System.out.println("Продолжительность: \""+DurationOfFilm+"\", формат: "+Format);
        System.out.println("------------------");
    }
    Film (){}
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public int getDurationOfFilm() {
        return DurationOfFilm;
    }

    public void setDurationOfFilm(int durationOfFilm) {
        DurationOfFilm = durationOfFilm;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
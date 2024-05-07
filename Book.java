package p2;

public class Book implements Comparable<Book> {
    private String identificador;
    private String ISBN;
    private String autor;
    private String disponible;
    private String titulo;

    public Book(String identificador, String ISBN, String autor, String disponible, String titulo) {
        this.identificador = identificador;
        this.ISBN = ISBN;
        this.autor = autor;
        this.disponible = disponible;
        this.titulo = titulo;

    }

    public String getTitulo() {
        return titulo;
    }

    // identificador
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    // ISBN
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    // autor
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    // disponible
    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String toString() {
        return "lo que quieras";
    }

    public int compareTo(Book l) {
        int compareResult = this.getIdentificador().compareTo(l.getIdentificador());
        if (compareResult > 0) {
            compareResult = 1;
        } else if (compareResult < 0) {
            compareResult = -1;
        } else
            compareResult = 0;
        return compareResult;
    }

}

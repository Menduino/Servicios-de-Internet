package p2;

public class Author implements Comparable<Author> {
    private String identificador;
    private String nacimiento;
    private String pais;
    private String nombre;

    public Author(String identificador, String nacimiento, String pais, String nombre) {
        this.identificador = identificador;
        this.nacimiento = nacimiento;
        this.pais = pais;
        this.nombre = nombre;

    }

    public String getNombre() {
        return nombre;
    }

    // identificador
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    // nacimiento
    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    // pais
    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String toString() {
        return "lo que quieras";
    }

    public int compareTo(Author author) {
        return identificador.compareTo(author.identificador);
    }

}

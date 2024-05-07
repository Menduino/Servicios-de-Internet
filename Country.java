package p2;

public class Country implements Comparable<Country> {
    private String identificador;
    private String nombre;

    public Country(String identificador, String nombre) {
        this.identificador = identificador;
        this.nombre = nombre;

    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int compareTo(Country p) {
        int compareResult = this.getIdentificador().compareTo(p.getIdentificador());
        if (compareResult > 0) {
            compareResult = 1;
        } else if (compareResult < 0) {
            compareResult = -1;
        } else
            compareResult = 0;
        return compareResult;
    }
}

package p2;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

public class FrontEnd {

    public static void generatePhase0(PrintWriter out, HttpServletRequest request) {
        // Example dynamic values
        String lmlFileName = DataModel.getProcessedFile();
        String clientIPAddress = request.getRemoteAddr();
        String clientBrowserInfo = request.getHeader("User-Agent");
        String serverIPAddress;
        try {
            serverIPAddress = (InetAddress.getLocalHost()).getHostAddress();

            // Generating HTML content
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Welcome to Information Query Service</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Welcome to Information Query Service</h2>");
            out.println("<p>File processed: " + lmlFileName + "</p>");
            out.println("<p>Client's IP: " + clientIPAddress + "</p>");
            out.println("<p>Client's browser: " + clientBrowserInfo + "</p>");
            out.println("<p>Server IP: " + serverIPAddress + "</p>");
            out.println("<br>");
            out.println("<form id=\"form\" action=\"/sint155/P2Lib?fase=1\">");
            out.println("<input type=\"submit\" value=\"Next\" method=\"get\">");
            out.println("<input type=\"hidden\" name=\"fase\" value=\"1\" >");

            out.println("</form>");
            out.println("<hr>");
            out.println("Author: Daniel Vázquez Otero");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generatePhase1(PrintWriter out, ArrayList<Country> countries) {

        // Generating HTML content
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>List of Known Countries</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>List of Known Countries</h2>");
        out.println("<form action=\"P2Lib\" method=\"get\">");
        out.println("<label>Please select a country:</label>");

        // Generating dynamic options for known countries
        for (Country country : countries) {
            out.println("<p><a href=\"/sint155/P2Lib?fase=2&pais=" + country.getIdentificador() + "\">"
                    + country.getNombre()
                    + "</a></p>");
        }

        out.println("<br><br>");
        out.println("<form id=\"form\" action=\"/sint155/P2Lib?fase=0\">");
        out.println("<input type=\"submit\" value=\"Back\" method=\"get\">");
        out.println("<input type=\"hidden\" name=\"fase\" value=\"0\" >");

        out.println("</form>");
        out.println("<p> Author:Daniel Vázquez Otero </p>");
        out.println("</body>");
        out.println("</html>");
    }

    public static void generatePhase2(PrintWriter out, String countryId, ArrayList<Author> authors) {
        // Generating HTML content
        Country country = DataModel.getCountry(countryId);
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>List of Authors in a Country</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>List of Authors in a Country</h2>");
        out.println("<p>Country: " + country.getNombre() + "</p>");
        out.println("<label>Please select an author:</label>");

        // Generating dynamic options for authors in the selected country
        for (Author author : authors) {
            out.println("<p><a href=\"/sint155/P2Lib?fase=3&autor=" + author.getIdentificador() + "\">"
                    + author.getNombre()
                    + "</a> Born in " + author.getNacimiento()
                    + " </p>");

        }
        out.println("<br><br>");
        out.println("<form id=\"form\" action=\"/sint155/P2Lib?fase=1\">");
        out.println("<input type=\"submit\" value=\"Back\" method=\"get\"></input>");
        out.println("<input type=\"hidden\" value=1 name=\"fase\" value=\"1\"></input>");
        out.println("</form>");
        out.println("<br>");
        out.println("<p> Author:Daniel Vázquez Otero </p>");
        out.println("</body>");
        out.println("</html>");
    }

    public static void generatePhase3(PrintWriter out, String authorId, ArrayList<Book> books) {
        Author author = DataModel.getAuthor(authorId);
        Country country = DataModel.getCountry(author.getPais());
        // Generating HTML content
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>List of Books from the Author</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>List of Books from the Author</h2>");
        out.println("<p>Country: " + country.getNombre() + "</p>");
        out.println("<p>Author: " + author.getNombre() + "</p>");
        out.println("<p>Book list</p>");
        out.println("<ul>");

        // Generating dynamic list of books from the selected author
        for (Book book : books) {
            String available = book.getDisponible();
            if (available.compareTo("no") == 0) {
                out.println(
                        "<li><a class=\"NoDisp\">" + book.getTitulo() + "</a> ISBN: " + book.getISBN() + "</li>");

            } else {
                out.println("<li><a  class=\"Disp\">" + book.getTitulo() + "</a>  ISBN: "
                        + book.getISBN() + "</li>");

            }
        }

        out.println("</ul>");
        out.println("<form id=\"form\" action=\"/sint155/P2Lib\">");
        out.println("<input type=\"submit\" value=\"Back\" method=\"get\"></input>");
        out.println("<input type=\"hidden\" value=2 name=\"fase\"></input>");
        out.println("<input type=\"hidden\" value=" + country.getIdentificador() + " name=\"pais\"></input>");
        out.println("</form>");
        out.println("<br>");
        out.println("<p> Author:Daniel Vázquez Otero </p>");
        out.println("</body>");
        out.println("</html>");
    }

    public static void generatePhaseError() {
        PrintWriter out = new PrintWriter(System.out, true);
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Error</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"#f0f0f0\">");
        out.println("<h1 align=\"center\">Error</h1>");
        out.println("<p>Lo siento, la fase solicitada no es válida.</p>");
        out.println("</body>");
        out.println("</html>");
    }
}

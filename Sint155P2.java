package p2;

import java.io.*;
import java.net.InetAddress;
import java.util.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import org.json.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.net.URL;
import java.io.File;

// The class definition for the Servlet
public class Sint155P2 extends HttpServlet {

    // Initialization method called when the servlet is first created
    public void init() throws ServletException {
        // Creating an instance of DataModel during servlet initialization
        DataModel dataModel = new DataModel();
        Datamodel examen = new Exam();
    }

    // Handling HTTP GET requests
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get the request URI
        String requestURL = request.getRequestURI();

        // Check if the request URL contains "v1"
        if ((requestURL.contains("v1"))) {
            // If so, handle it as a REST API request
            this.handleRestAPIRequest(requestURL, response);
            return;
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        // Get the value of the 'fase' parameter from the request
        String fase = request.getParameter("fase");

        // Set 'fase' to "0" if it is null
        if (fase == null) {
            fase = "0";
        }

        // Switch statement to handle different phases of the application
        switch (fase) {
            case "0":
                // Generate HTML content for Phase 0
                String comment = DataModel.getComment();
                FrontEnd.generatePhase0(out, request, comment);
                break;

            case "1":
                // Generate HTML content for Phase 1 and retrieve countries from the DataModel
                ArrayList<Country> countries = DataModel.getCountries();
                FrontEnd.generatePhase1(out, countries);
                break;

            case "2":
                // Get the 'pais' parameter from the request
                String countryId = request.getParameter("pais");
                // Retrieve authors for the specified country from the DataModel
                ArrayList<Author> authors = DataModel.getAuthors(countryId);
                // Generate HTML content for Phase 2
                FrontEnd.generatePhase2(out, countryId, authors);
                break;

            case "3":
                // Get the 'autor' parameter from the request
                String authorId = request.getParameter("autor");
                // Retrieve books for the specified author from the DataModel
                ArrayList<Book> books = DataModel.getBooks(authorId);
                // Generate HTML content for Phase 3
                FrontEnd.generatePhase3(out, authorId, books);
                break;

            default:
                // Generate HTML content for an error phase
                FrontEnd.generatePhaseError();
                break;
        }
    }

    // Method to handle REST API requests
    private void handleRestAPIRequest(String requestURL, HttpServletResponse response) {
        try {
            // Set response content type to JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Extract path segments from the request URL
            String[] pathSegments = extractPathSegments(requestURL);
            // Process the request and get a JSON response
            JSONArray jsonResponse = processRequest(pathSegments);

            // Write the JSON response to the output stream
            response.getWriter().println(jsonResponse.toString());

            // Set HTTP status code based on whether the response contains data
            int statusCode = jsonResponse.length() > 0 ? 200 : 404;
            response.setStatus(statusCode);
        } catch (Exception e) {
            // Handle exceptions that may occur during processing
            handleException(e);
        }
    }

    // Method to extract path segments from the request URL
    private String[] extractPathSegments(String requestURL) {
        return requestURL.substring(requestURL.lastIndexOf("v1") + 3).split("/");
    }

    // Method to process REST API requests and generate a JSON response
    private JSONArray processRequest(String[] pathSegments) {
        JSONArray resultArray = new JSONArray();
        ArrayList<Country> countries;

        try {
            // Check if the path is valid
            if (isValidPath(pathSegments)) {
                // Get the entity ID from the path segments
                String entityId = (pathSegments.length == 3) ? pathSegments[2] : null;

                // Switch statement to handle different types of entities in the request
                switch (pathSegments[0]) {
                    case "libros":
                        // Retrieve books data from the DataModel and convert it to JSON
                        resultArray = new JSONArray(DataModel.getBooks(entityId));
                        break;
                    case "autores":
                        // Retrieve authors data from the DataModel and convert it to JSON
                        resultArray = new JSONArray(DataModel.getAuthors(entityId));
                        break;
                    case "paises":
                        // Retrieve countries data from the DataModel and convert it to JSON
                        countries = DataModel.getCountries();
                        resultArray = new JSONArray(countries);
                        break;
                    case "libro":
                        // Retrieve data for a specific book and convert it to JSON
                        JSONObject bookJson = new JSONObject();
                        Book book = DataModel.getBook(pathSegments[1]);
                        if (book != null) {
                            bookJson.put("ISBN", book.getISBN());
                            bookJson.put("autor", book.getAutor());
                            bookJson.put("disponible", book.getDisponible());
                            bookJson.put("titulo", book.getTitulo());
                            bookJson.put("identificador", book.getIdentificador());
                        }
                        resultArray.put(bookJson);
                        break;
                    case "autor":
                        // Retrieve data for a specific author and convert it to JSON
                        JSONObject authorJson = new JSONObject();
                        Author author = DataModel.getAuthor(pathSegments[1]);
                        if (author != null) {
                            authorJson.put("identificador", author.getIdentificador());
                            authorJson.put("nacimiento", author.getNacimiento());
                            authorJson.put("nombre", author.getNombre());
                            authorJson.put("pais", author.getPais());
                        }
                        resultArray.put(authorJson);
                        break;
                    case "pais":
                        // Retrieve data for a specific country and convert it to JSON
                        JSONObject countryJson = new JSONObject();
                        Country country = DataModel.getCountry(pathSegments[1]);
                        if (country != null) {
                            countryJson.put("identificador", country.getIdentificador());
                            countryJson.put("nombre", country.getNombre());
                        }
                        resultArray.put(countryJson);
                        break;
                }
            }
        } catch (Exception e) {
            // Handle exceptions that may occur during processing
            handleException(e);
        }
        return resultArray;
    }

    // Method to check if the path in the REST API request is valid
    private boolean isValidPath(String[] pathSegments) {
        return (pathSegments.length == 1 || pathSegments.length == 2 || pathSegments.length == 3);
    }

    // Method to handle exceptions during processing
    private void handleException(Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
        e.printStackTrace();
    }
}

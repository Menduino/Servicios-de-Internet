package p2;

import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;

// DataModel class responsible for managing data retrieval from an XML source
public class DataModel {
    // URL of the XML data source
    private static final String XML_URL = "http://manolo.webs.uvigo.gal/SINT/libreria.xml";

    private static final String path = "fich.xml";

    // Static members to store parsed XML elements
    private static Element xmlDocument;
    private static String fileProcessed;
    private static NodeList countryList;
    private static NodeList bookList;
    private static NodeList authorList;

    private static String textout;

    private static NodeList live;
    private static NodeList lang;
    private static NodeList enero;

    // Constructor to initialize DataModel and parse XML data
    public DataModel() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(XML_URL).openStream());

            // Get and store relevant XML elements
            fileProcessed = XML_URL.substring(XML_URL.lastIndexOf('/') + 1, XML_URL.length());
            xmlDocument = doc.getDocumentElement();
            authorList = xmlDocument.getElementsByTagName("autor");
            bookList = xmlDocument.getElementsByTagName("libro");
            countryList = xmlDocument.getElementsByTagName("pais");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Exam() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new FileInputString(path));

            // Get and store relevant XML elements
            enero= xmlDocument.getElementsByTagName("enero");
            NodeList comment =enero.item(0).getChildNodes();
            for (int i = 0; i < comment.getLength(); i++) {
                if(comment.item(i).getNodeType()==Node.TEXT_NODE){
                    if(!(comment.item(i).getTextContent().trim()==NULL)){
                        textout=comment.item(i).getTextContext().trim();
                    }

                }
     
            }
            fileProcessed = path.substring(path.lastIndexOf('/') + 1, path.length());
            xmlDocument = doc.getDocumentElement();
            live = xmlDocument.getElementsByTagName("live");
            lang = xmlDocument.getElementsByTagName("lang");
            String langString = lang.item(0).getAtributtes("value");
            String liveString = live.item(0).getTextContext();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getComemnt() {
        return textout;
    }

    // Method to retrieve authors based on the specified country ID
    public static ArrayList<Author> getAuthors(String countryID) {
        ArrayList<Author> authors = new ArrayList<>();
        try {
            for (int i = 0; i < authorList.getLength(); i++) {
                Element authorElement = (Element) authorList.item(i);
                String id = authorElement.getAttribute("identificador");
                String name = authorElement.getTextContent();
                String birth = authorElement.getAttribute("nacimiento");
                String authorCountry = authorElement.getAttribute("pais");

                // Filter authors by country if countryID is specified
                if (!(countryID == null)) {
                    if (authorCountry.compareTo(countryID) == 0) {
                        Author author = new Author(id, birth, authorCountry, name);
                        authors.add(author);
                    }
                } else {
                    authors.add(new Author(id, birth, authorCountry, name));
                }
            }

            // Sort the list of authors
            Collections.sort(authors);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return authors;
    }

    // Method to retrieve books based on the specified author ID
    public static ArrayList<Book> getBooks(String authorID) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            for (int i = 0; i < bookList.getLength(); i++) {
                Element bookElement = (Element) bookList.item(i);
                String nombre = bookElement.getTextContent();
                String isbn = bookElement.getAttribute("ISBN");
                String autor = bookElement.getAttribute("autor");
                String id = bookElement.getAttribute("identificador");
                String disponible = bookElement.getAttribute("disponible");

                // Filter books by author if authorID is specified
                if (!(authorID == null)) {
                    if (autor.compareTo(authorID) == 0) {
                        Book book = new Book(id, isbn, autor, disponible, nombre);
                        books.add(book);
                    }
                } else {
                    books.add(new Book(id, isbn, autor, disponible, nombre));
                }
            }

            // Sort the list of books
            Collections.sort(books);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    // Method to retrieve a list of countries
    public static ArrayList<Country> getCountries() {
        ArrayList<Country> countries = new ArrayList<>();

        for (int i = 0; i < countryList.getLength(); i++) {
            Element countryElement = (Element) countryList.item(i);
            String id = countryElement.getAttribute("identificador");
            String name = countryElement.getTextContent();

            // Create Country objects and add them to the list
            Country country = new Country(id, name);
            countries.add(country);
        }

        // Sort the list of countries
        Collections.sort(countries);
        return countries;
    }

    // Method to retrieve a specific author by ID
    public static Author getAuthor(String authorId) {
        Element author = null;
        for (int i = 0; i < authorList.getLength(); i++) {
            author = (Element) authorList.item(i);
            if (author.getAttribute("identificador").compareTo(authorId) == 0) {
                break;
            }
        }
        String name = author.getTextContent();
        String country = author.getAttribute("pais");
        String birth = author.getAttribute("nacimiento");
        String id = author.getAttribute("identificador");

        return new Author(id, birth, country, name);
    }

    // Method to retrieve a specific country by ID
    public static Country getCountry(String countryId) {
        Element country = null;
        for (int i = 0; i < countryList.getLength(); i++) {
            country = (Element) countryList.item(i);
            if (country.getAttribute("identificador").compareTo(countryId) == 0) {
                break;
            }
        }
        String name = country.getTextContent();
        String id = country.getAttribute("identificador");

        return new Country(id, name);
    }

    // Method to retrieve a specific book by ID
    public static Book getBook(String bookId) {
        Element book = null;
        for (int i = 0; i < bookList.getLength(); i++) {
            book = (Element) bookList.item(i);
            if (book.getAttribute("identificador").compareTo(bookId) == 0) {
                break;
            }
        }
        String titulo = book.getTextContent();
        String isbn = book.getAttribute("ISBN");
        String autor = book.getAttribute("autor");
        String id = book.getAttribute("identificador");
        String disponible = book.getAttribute("disponible");

        return new Book(id, isbn, autor, disponible, titulo);
    }

    // Method to retrieve the name of the processed file
    public static String getProcessedFile() {
        return fileProcessed;
    }
}

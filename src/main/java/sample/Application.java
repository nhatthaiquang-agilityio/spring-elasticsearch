package sample;

import sample.Book;
import sample.BookService;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Map;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ElasticsearchOperations es;

    @Autowired
    private DevProperties devProperties;

    @Autowired
    private BookService bookService;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        printElasticSearchInfo();

        bookService.save(new Book("1001", "Elasticsearch Basics", "Nhat Thai", "23-FEB-2017"));
        bookService.save(new Book("1002", "Apache Lucene Basics", "Nhat Thai", "13-MAR-2017"));
        bookService.save(new Book("1003", "Apache Solr Basics", "Nhat Thai", "21-MAR-2017"));

        //fuzzey search
        Page<Book> books = bookService.findByAuthor("Nhat", new PageRequest(0, 10));
        books.forEach(x -> System.out.println(x));

        System.out.println("Find books by Title");
        List<Book> foundBooks = bookService.findByTitle("Apache");
        foundBooks.forEach(x -> System.out.println(x));

        System.out.println("Find book");
        Book book = bookService.findOne("1003");
        System.out.println(book);

        System.out.println("Dev Properties");
        System.out.println(devProperties);
    }

    //useful for debug, print elastic search details
    private void printElasticSearchInfo() {

        System.out.println("--ElasticSearch--");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("--ElasticSearch--");
    }

}
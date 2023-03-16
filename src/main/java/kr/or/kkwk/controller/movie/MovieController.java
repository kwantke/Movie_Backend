package kr.or.kkwk.controller.movie;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import kr.or.kkwk.model.dto.movie.MovieDto;
import kr.or.kkwk.model.dto.movie.MovieSectionDto;
import kr.or.kkwk.service.movie.MovieService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MovieController {

  MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService){
    this.movieService=movieService;
  }

  @RequestMapping("/getMovieSection")
  public List<MovieSectionDto> getMovieSection(MovieSectionDto movieSectionDto){
    return movieService.getMovieSectionList(movieSectionDto);
  }

  @RequestMapping("/getMovieList")
  public List<MovieDto> getMovieDtoList(int section){
    return movieService.getMovieList(section);
  }

  @GetMapping("/getMovieDetail")
  public MovieDto getMovieDetail(Long id){
    MovieDto movieDto = movieService.getMovieInfo(id);
    return movieDto;
  }
  @RequestMapping("/test")
  public String test() throws IOException {

    // Create the low-level client
    RestClient restClient = RestClient.builder(
            new HttpHost("localhost", 9200)).build();

// Create the transport with a Jackson mapper
    ElasticsearchTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper());

// And create the API client
    ElasticsearchClient client = new ElasticsearchClient(transport);

    //String json = "{\"test\":1}";
    /*String json ="{                                         "
            +"  \"mappings\": {                        "
            +"    \"test2\"                            "
            +"    \"properties\": {                    "
            +"      \"field1\": { \"type\": \"text\" } "
            +"    }                                    "
            +"  }                                      "
            +"}                                        ";*/
    /*String json="{                                              "
            +"  \"mappings\": {                             "
            +"    \"test2\":{                               "
            +"      \"properties\": {                       "
            +"        \"id\": { \"type\": \"integer\" },     "
            +"        \"data\": { \"type\": \"text\"}       "
            +"      }                                       "
            +"    }                                         "
            +"  }                                           "
            +"}                                             ";*/
    String json="{                                                      "
            +"  \"mappings\": {                                     "
            +"    \"test3\":{                                       "
            +"      \"properties\": {                               "
            +"        \"id\": { \"type\": \"integer\" },            "
            +"        \"data\": { \"type\": \"string\"},            "
            +"        \"product\": {                                "
            +"          \"type\": \"nested\",                       "
            +"          \"properties\": {                           "
            +"            \"id\":    { \"type\": \"integer\"  },    "
            +"            \"data\": { \"type\": \"string\"  }       "
            +"          }                                           "
            +"        }                                             "
            +"      }                                               "
            +"    }                                                 "
            +"  }                                                   "
            +"}                                                     ";
    Reader input = new StringReader(json);
    IndexRequest<JsonData> request = IndexRequest.of(i -> i
            .index("test3")  //set index
            .withJson(input)
    );

    /*List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(new Ingredient(1,"실","빨강색"));
    ingredients.add(new Ingredient(2,"가죽","갈색"));
    List<Product> products = new ArrayList<>();
    products.add(new Product(1,"양말",ingredients));
    products.add(new Product(2,"양말",ingredients));
    Test test = new Test(1, "신발",products);
    IndexRequest<Test> request = IndexRequest.of(i -> i
            .index("products")
            .id("1")
            .document(test)
    );*/

    IndexResponse response = client.index(request);
    System.out.println(response.version());

   // client.indices().create(c -> c.index("products"));
    return "";
  }

    @RequestMapping("/test2")
    public String tes4dt() throws IOException {

      // Create the low-level client
      RestClient restClient = RestClient.builder(
              new HttpHost("localhost", 9200)).build();

// Create the transport with a Jackson mapper
      ElasticsearchTransport transport = new RestClientTransport(
              restClient, new JacksonJsonpMapper());

// And create the API client
      ElasticsearchClient client = new ElasticsearchClient(transport);
     // BulkRequestBuilder bulkRequest = client.prepareBulk();


      //IndexResponse response = client.index(esrequest);
     // System.out.println(response.version());
      /*BulkRequest request = new BulkRequest();
      request.add(new IndexRequest("test", "_doc", "1").source(XContentType.JSON,"code", "100", "title","넘버원","date",new Date()));
      request.add(new UpdateRequest("test", "_doc", "2").doc(XContentType.JSON,"code", "101", "title","넘버투","date",new Date()));
      request.add(new DeleteRequest("test", "_doc", "3"));
      request.add(new IndexRequest("test", "_doc", "4").source(XContentType.JSON,"code", "103", "title","넘버포","date",new Date()));

      BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
      for (BulkItemResponse bulkItemResponse : bulkResponse) {
        DocWriteResponse itemResponse = bulkItemResponse.getResponse();

        switch (bulkItemResponse.getOpType()) {
          case INDEX:
          case CREATE:
            IndexResponse indexResponse = (IndexResponse) itemResponse;
            break;
          case UPDATE:
            UpdateResponse updateResponse = (UpdateResponse) itemResponse;
            break;
          case DELETE:
            DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
        }
        System.out.println(itemResponse);*/
        // client.indices().create(c -> c.index("products"));
        return "";
      }
}

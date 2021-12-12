package com.rb.authentication.service.impl;

import com.rb.authentication.service.BookService;
import com.rb.books.client.api.model.BookDTO;
import com.rb.books.client.api.model.BookDTOList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class BooksServiceImpl implements BookService {

    @Value("${book.service.url}")
    private String serviceUrl;

    private final RestTemplate restTemplate;

    public BooksServiceImpl(RestTemplate restTemplate) {
        super();
        this.restTemplate = restTemplate;
    }

    @Override
    public BookDTOList getBooks() {
        String url = serviceUrl + "/books";
        HttpEntity<String> request = new HttpEntity<>(null, null);
        return this.restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<BookDTOList>(){ }).getBody();
    }

    @Override
    public BookDTO getBookDetails(String bookISBN) {
        String url = serviceUrl + "/books/" + bookISBN;
        HttpEntity<String> request = new HttpEntity<>(null, null);
        return this.restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<BookDTO>(){ }).getBody();
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        String url = serviceUrl + "/books";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<BookDTO> entity = new HttpEntity<>(bookDTO, httpHeaders);
        return this.restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<BookDTO>(){ }).getBody();
    }

    @Override
    public void delete(String bookISBN) {
        String url = serviceUrl + "/books/" + bookISBN;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        this.restTemplate.exchange(url, HttpMethod.DELETE, entity, new ParameterizedTypeReference<Void>(){ }).getBody();
    }
}

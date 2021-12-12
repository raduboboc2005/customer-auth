package com.rb.authentication.service;

import com.rb.books.client.api.model.BookDTO;
import com.rb.books.client.api.model.BookDTOList;

public interface BookService {
    BookDTOList getBooks();
    BookDTO getBookDetails(String bookISBN);
    BookDTO addBook(BookDTO bookDTO);
    void delete(String bookISBN);
}

package com.rb.authentication.controller;

import com.rb.authentication.service.BookService;
import com.rb.books.client.api.model.BookDTO;
import com.rb.books.client.api.model.BookDTOList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books")
    public String getUsers(Model model) {
        BookDTOList books = this.bookService.getBooks();
        model.addAttribute("books", books.getBooks());
        return "books";
    }

    @RequestMapping(value = "/new")
    public String showNewBookPage(Model model) {
        BookDTO bookDTO = new BookDTO();
        model.addAttribute("bookDTO", bookDTO);

        return "addBook";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveNewBook(@ModelAttribute("bookDTO") BookDTO bookDTO) {
        bookService.addBook(bookDTO);

        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{isbn}")
    public String deleteProduct(@PathVariable(name = "isbn") String isbn) {
        bookService.delete(isbn);
        return "redirect:/books";
    }

    @RequestMapping(value = "/edit/{isbn}")
    public ModelAndView showEditProductPage(@PathVariable(name = "isbn") String isbn) {
        ModelAndView modelAndView = new ModelAndView("edit_book");
        BookDTO book = bookService.getBookDetails(isbn);
        modelAndView.addObject("bookDTO", book);

        return modelAndView;
    }
}

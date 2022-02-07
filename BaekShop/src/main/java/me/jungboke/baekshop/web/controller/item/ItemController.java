package me.jungboke.baekshop.web.controller.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jungboke.baekshop.web.controller.form.BookForm;
import me.jungboke.baekshop.web.controller.form.BookSaveForm;
import me.jungboke.baekshop.web.controller.form.BookUpdateForm;
import me.jungboke.baekshop.domain.item.Book;
import me.jungboke.baekshop.domain.item.Item;
import me.jungboke.baekshop.service.item.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        log.info("Book 생성");
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String create(@Validated @ModelAttribute("form") BookSaveForm bookForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }
        Book book = Book.createBook(bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity(),
                bookForm.getAuthor(), bookForm.getIsbn());

        log.info("book 생성완료");
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String ListItem(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        log.info("ListBook");
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String EditItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book findItem = (Book) itemService.findOne(itemId);
        BookForm bookForm = new BookForm(findItem.getName(), findItem.getPrice(), findItem.getStockQuantity(),
                findItem.getAuthor(), findItem.getIsbn());

        model.addAttribute("form", bookForm);
        log.info("Book 업데이트 시작");
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String EditItem(@PathVariable("itemId") Long itemId, @Validated @ModelAttribute("form") BookUpdateForm bookForm,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "items/updateItemForm";
        }
        itemService.updateItem(itemId, bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());
        return "redirect:/items";
    }
}

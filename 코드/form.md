## Form
BookForm
````
@Getter
@Setter
@NoArgsConstructor
public class BookForm {

    private String name;
    private Integer price;
    private Integer stockQuantity;
    private String author;
    private String isbn;

    public BookForm(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
````
BookSaveForm
````
@Getter
@Setter
@NoArgsConstructor
public class BookSaveForm {

    @NotBlank
    private String name;
    @NotNull
    @Range(min=1000, max=100000)
    private Integer price;
    @NotNull
    @Max(9999)
    private Integer stockQuantity;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;

    public BookSaveForm(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
````
BookUpdateForm
````
@Getter
@Setter
@NoArgsConstructor
public class BookUpdateForm {

    @NotBlank
    private String name;
    @NotNull
    @Range(min=1000, max=100000)
    private Integer price;
    @NotNull
    private Integer stockQuantity;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;

    public BookUpdateForm(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
````
LoginForm
````
@Getter
@Setter
@NoArgsConstructor
public class LoginForm {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
````
MemberForm
````
@Getter
@Setter
@NoArgsConstructor
public class MemberForm {

    private String name;

    private String city;
    private String street;
    private String zipcode;

    private String loginId;
    private String password;
}
````
MemberSaveForm
````
@Getter
@Setter
@NoArgsConstructor
public class MemberSaveForm {

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
````
OrderForm
````
@Getter
@Setter
@NoArgsConstructor
public class OrderForm {

    @NotNull
    private Long memberId;
    @NotNull
    private Long itemId;
    @NotNull
    private Integer count;
}
````

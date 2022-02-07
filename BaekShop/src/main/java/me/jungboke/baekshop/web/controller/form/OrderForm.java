package me.jungboke.baekshop.web.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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

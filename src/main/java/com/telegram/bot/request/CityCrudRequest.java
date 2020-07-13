package com.telegram.bot.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CityCrudRequest {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[?!,.а-яА-ЯёЁ0-9\\s]+$")
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 255)
    private String description;
}

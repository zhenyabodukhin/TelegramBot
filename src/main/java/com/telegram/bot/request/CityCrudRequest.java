package com.telegram.bot.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CityCrudRequest {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[А-Яа-я]+")
    private String name;

    @NotNull
    @NotEmpty
    private String description;
}

package com.telegram.bot.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CityCrudRequest {

    @NotNull
    @NotEmpty
    private String cityName;

    @NotNull
    @NotEmpty
    private String cityDescription;
}

package com.github.fabriciolfj.ifodd.mp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PratoCarrinho {

    private String cliente;
    private Long prato;
}

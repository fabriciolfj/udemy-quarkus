package com.github.fabriciolfj.ifood.cadastro.infra;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ConstraintViolationResponse {

    private final List<ConstraintViolationImpl> violacoes = new ArrayList<>();

    private ConstraintViolationResponse(final ConstraintViolationException e) {
        e.getConstraintViolations().forEach(violation -> violacoes.add(ConstraintViolationImpl.of(violation)));
    }

    public static ConstraintViolationResponse of(final ConstraintViolationException e) {
        return new ConstraintViolationResponse(e);
    }

    public List<ConstraintViolationImpl> getViolacoes() {
        return violacoes;
    }
}

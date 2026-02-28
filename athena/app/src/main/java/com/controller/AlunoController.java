package com.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.domain.aluno.usecase.RegistrarAlunoUseCase;
import com.infra.UriResponseBuilder;
import com.query.domain.aluno.app.AlunoQueryAppService;
import com.query.domain.aluno.projection.Aluno;
import com.core.sk.identifiers.AlunoId;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/alunos", produces = APPLICATION_JSON_VALUE)
public class AlunoController {

    private final RegistrarAlunoUseCase registrarAlunoAppService;
    private final AlunoQueryAppService alunoQueryAppService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrar(@RequestBody RegistrarAlunoUseCase.RegistrarAluno cmd) {

        AlunoId id = registrarAlunoAppService.handle(cmd);

        return UriResponseBuilder.builder()
            .fullPath(fromCurrentRequest().toUriString())
            .id(id.asString())
            .build()
            .toCreated();
    }

    @GetMapping(path = "/{id}")
    public Aluno buscaPorId(@PathVariable UUID id) {
        return alunoQueryAppService.recuperarProduto(id);
    }

}

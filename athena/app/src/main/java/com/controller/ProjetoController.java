package com.controller;

import com.core.domain.aluno.usecase.RegistrarAlunoUseCase;
import com.core.domain.aluno.usecase.RegistrarProjetoUseCase;
import com.core.sk.identifiers.AlunoId;
import com.core.sk.identifiers.ProjetoId;
import com.infra.UriResponseBuilder;
import com.query.domain.aluno.app.AlunoQueryAppService;
import com.query.domain.aluno.projection.Aluno;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/projetos", produces = APPLICATION_JSON_VALUE)
public class ProjetoController {

    private final RegistrarProjetoUseCase registrarProjetoAppService;
    //private final ProjetoQueryAppService alunoQueryAppService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrar(@RequestBody RegistrarProjetoUseCase.RegistrarProjeto cmd) {

        ProjetoId id = registrarProjetoAppService.handle(cmd);

        return UriResponseBuilder.builder()
                .fullPath(fromCurrentRequest().toUriString())
                .id(id.asString())
                .build()
                .toCreated();
    }

   /* @GetMapping(path = "/{id}")
    public Aluno buscaPorId(@PathVariable UUID id) {
        return alunoQueryAppService.recuperarProduto(id);
    }*/




}

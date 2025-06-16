package com.megaproject.mega_api_web.controller;


import com.megaproject.mega_api_web.enums.EstrategiaGeracao;
import com.megaproject.mega_api_web.service.GeradorJogoService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Max;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/jogos")
@RequiredArgsConstructor
@Validated
public class JogoController {

    private final GeradorJogoService jogoService;

    @GetMapping("/gerar")
    public ResponseEntity<List<Integer>> gerarJogo(

            @RequestParam
            @Min(6)
            @Max(20)
            int quantidade,

            @RequestParam
            EstrategiaGeracao estrategia,

            @RequestParam(required = false)
            Integer ultimos
    ){
        log.info("Gerando jogo com {} números usando estratégia {} e últimos {} concursos",
                quantidade, estrategia, ultimos != null ? ultimos : "todos");
        List<Integer> jogo = jogoService.gerarJogo(quantidade, estrategia, ultimos);
        return ResponseEntity.ok(jogo);
    }

    @GetMapping("/gerar/aleatorio")
    public ResponseEntity<List<Integer>> gerarAleatorio(
            @RequestParam
            @Min(6)
            @Max(20)
            int quantidade) {

        log.info("Gerando jogo ALEATÓRIO com {} números (endpoint dedicado)", quantidade);
        List<Integer> jogo = jogoService.gerarAleatorio(quantidade);
        return ResponseEntity.ok(jogo);
    }
}

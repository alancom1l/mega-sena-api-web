package com.megaproject.mega_api_web.service;

import com.megaproject.mega_api_web.entity.Concurso;
import com.megaproject.mega_api_web.enums.EstrategiaGeracao;
import com.megaproject.mega_api_web.repository.ConcursoRepository;
import com.megaproject.mega_api_web.util.NumeroUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeradorJogoService {

    private final ConcursoRepository concursoRepository;

    public List<Integer> gerarJogo(int quantidade, EstrategiaGeracao estrategia, Integer ultimos) {
        log.info("Iniciando geração de jogo: estratégia={}, quantidade={}, últimos={} concursos",
                estrategia, quantidade, ultimos);

        int ultimoConcurso = concursoRepository.findUltimoConcurso();
        int idMinimo = (ultimos != 0) ? ultimoConcurso - ultimos + 1 : 1;

        log.debug("Buscando concursos com ID entre {} e {}", idMinimo, ultimoConcurso);

        List<Concurso> concursosRecentes = concursoRepository.findConcursosRecentes(idMinimo);

        Map<Integer, Long> contagem = concursosRecentes.stream()
                .flatMap(c -> Arrays.stream(c.getDezenas()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Integer> todosNumeros = IntStream.rangeClosed(1, 60).boxed().toList();

        List<Integer> resultado;

        switch (estrategia) {
            case MAIS_SORTEADOS -> {
                resultado = todosNumeros.stream()
                        .sorted(Comparator.comparingLong(n -> -contagem.getOrDefault(n, 0L)))
                        .limit(quantidade)
                        .collect(Collectors.toList());
            }
            case MENOS_SORTEADOS -> {
                resultado = todosNumeros.stream()
                        .sorted(Comparator.comparingLong(n -> contagem.getOrDefault(n, 0L)))
                        .limit(quantidade)
                        .collect(Collectors.toList());
            }
            case PRIMOS_MAIS_SORTEADOS -> {
                resultado = todosNumeros.stream()
                        .filter(NumeroUtil::ehPrimo)
                        .sorted(Comparator.comparingLong(n -> -contagem.getOrDefault(n, 0L)))
                        .limit(quantidade)
                        .collect(Collectors.toList());
            }
            case PRIMOS_MENOS_SORTEADOS -> {
                resultado = todosNumeros.stream()
                        .filter(NumeroUtil::ehPrimo)
                        .sorted(Comparator.comparingLong(n -> contagem.getOrDefault(n, 0L)))
                        .limit(quantidade)
                        .collect(Collectors.toList());
            }
            default -> {
                log.error("Estratégia inválida: {}", estrategia);
                throw new IllegalArgumentException("Estratégia inválida");
            }
        }

        Collections.shuffle(resultado);
        List<Integer> jogoFinal = resultado.stream().limit(quantidade).sorted().toList();

        log.info("Jogo gerado com sucesso: {}", jogoFinal);
        return jogoFinal;
    }

    public List<Integer> gerarAleatorio(int quantidade) {
        log.debug("Gerando jogo aleatório localmente com {} números", quantidade);
        List<Integer> numeros = IntStream.rangeClosed(1, 60).boxed().collect(Collectors.toList());
        Collections.shuffle(numeros);
        List<Integer> jogo = numeros.subList(0, quantidade).stream().sorted().toList();
        log.info("Jogo aleatório gerado com sucesso: {}", jogo);
        return jogo;
    }
}
package br.com.alura.ecomart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorizador")
public class CategorizadorDeProdutosController {

    private final ChatClient chatClient;

    public CategorizadorDeProdutosController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping
    public String categorizar(String produto) {
        var system = """
                Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado
               
                Escolha uma categoria dentra a lista abaixo:
               
                1. Higiene pessoal
                2. Eletronicos
                3. Esportes
                4. Outros
               
                ###### exemplo de uso:
               
                Pergunta: Bola de futebol
                Resposta: Esportes
                """;

        return this.chatClient.prompt()
                .system(system)
                .user(produto)
                .options(ChatOptionsBuilder
                        .builder()
                        .withTemperature(0.8f)
                        .build())
                .call()
                .content();
    }

}

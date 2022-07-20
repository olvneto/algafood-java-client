package com.algaworks.algafood.client;

import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestauranteClient;
import com.algaworks.algafood.client.model.RestauranteModel;
import com.algaworks.algafood.client.model.input.CidadeIdInput;
import com.algaworks.algafood.client.model.input.CozinhaIdInput;
import com.algaworks.algafood.client.model.input.EnderecoInput;
import com.algaworks.algafood.client.model.input.RestauranteInput;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class InclusaoRestauranteMain {
    public static void main(String[] args) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            RestauranteClient restauranteClient =
                    new RestauranteClient(restTemplate, "http://localhost:8080");

            var cozinha = new CozinhaIdInput();
            cozinha.setId(1L);
            var cidade = new CidadeIdInput();
            cidade.setId(1L);

            var endereco = new EnderecoInput();
            endereco.setBairro("SHIS");
            endereco.setCep("00000-000");
            endereco.setCidade(cidade);
            endereco.setComplemento("xxx");
            endereco.setLogradouro("Rua A");
            endereco.setNumero("2A");

            var restaurante = new RestauranteInput();
            restaurante.setNome("O podrão");
            restaurante.setEndereco(endereco);
            restaurante.setCozinha(cozinha);
            restaurante.setTaxaFrete(new BigDecimal(12));

            RestauranteModel restauranteModel = restauranteClient.adicionar(restaurante);
            System.out.println(restauranteModel);

        } catch (ClientApiException e) {
            if (e.getProblem() != null) {
                System.out.println(e.getProblem().getUserMessage());
                e.getProblem().getObjects().stream()
                        .forEach(p -> System.out.println(p.getUserMessage()));
            } else {
                System.out.println("Erro desconhecido.");
                e.printStackTrace();
            }
        }
    }
}

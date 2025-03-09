package br.com.fiap.restaurantes.gerenciamento.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CardapioRequest {

    @NotBlank(message = "O nome do item é obrigatório")
    private String nome;

    @NotBlank(message = "A descrição do item é obrigatória")
    private String descricao;

    @NotNull(message = "O preço do item é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @NotNull(message = "A disponibilidade de consumo é obrigatória.")
    @Pattern(regexp = "LIVRE|LOCAL", message = "A disponibilidade de consumo deve ser LIVRE ou LOCAL")
    private String disponibilidadeConsumo;

    private String caminhoImagem;
}

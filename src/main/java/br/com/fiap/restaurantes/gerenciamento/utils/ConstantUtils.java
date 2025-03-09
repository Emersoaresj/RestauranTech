package br.com.fiap.restaurantes.gerenciamento.utils;

import lombok.Data;

import java.util.Set;

@Data
public class ConstantUtils {

    private ConstantUtils() {
        throw new IllegalStateException("Classe Utilitária");
    }

    //ERROS
    public static final String ERRO_LOGIN = "Login inválido!";
    public static final String ERRO_SENHA = "Senha inválida!";
    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado!";
    public static final String USUARIO_EXISTENTE = "Usuário já cadastrado!";
    public static final String LOGIN_CADASTRADO = "Login já cadastrado!";
    public static final String TIPO_USUARIO_EXISTENTE = "Tipo de usuário já cadastrado!";
    public static final String TIPO_USUARIO_INVALIDO = "Tipo de usuário inválido!";
    public static final String TIPO_USUARIO_NAO_ENCONTRADO = "Tipo de usuário não encontrado!";
    public static final String DONO_NAO_ENCONTRADO = "Dono de restaurante não encontrado!";
    public static final String RESTAURANTE_EXISTENTE = "Restaurante já cadastrado!";
    public static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante não encontrado!";
    public static final String CARDAPIO_EXISTENTE = "Cardápio já cadastrado!";
    public static final String CARDAPIO_NAO_ENCONTRADO = "Cardápio não encontrado!";
    public static final String DISPONIBILIADADE_CONSUMO_INVALIDA = "Disponibilidade de consumo inválida!";


    //AUX
    public static final String TIPO_DONO_RESTAURANTE = "Dono de Restaurante";
    public static final String TIPO_CLIENTE = "Cliente";
    public static final Set<String> TIPOS_USUARIO_VALIDOS = Set.of(TIPO_DONO_RESTAURANTE, TIPO_CLIENTE);




    //SUCESSO
    public static final String USUARIO_CADASTRADO = "Usuário cadastrado com sucesso!";
    public static final String TIPO_USUARIO_CADASTRADO = "Tipo de usuário cadastrado!";
    public static final String USUARIO_ATUALIZADO = "Usuário atualizado com sucesso!";
    public static final String TIPO_USUARIO_ATUALIZADO = "Tipo de usuário atualizado com sucesso!";
    public static final String USUARIO_DELETADO = "Usuário deletado com sucesso!";
    public static final String TIPO_USUARIO_DELETADO = "Tipo usuário deletado com sucesso!";
    public static final String SENHA_ALTERADA = "Senha alterada com sucesso!";
    public static final String LOGIN_VALIDADO = "Login validado!";
    public static final String TIPO_USUARIO_ASSOCIADO = "Tipo de usuário associado com sucesso!";
    public static final String RESTAURANTE_CADASTRADO = "Restaurante cadastrado com sucesso!";
    public static final String RESTAURANTE_ATUALIZADO = "Restaurante atualizado com sucesso!";
    public static final String RESTAURANTE_DELETADO = "Restaurante deletado com sucesso!";
    public static final String CARDAPIO_CADASTRADO = "Cardápio cadastrado com sucesso!";
    public static final String CARDAPIO_ATUALIZADO = "Cardápio atualizado com sucesso!";
    public static final String CARDAPIO_DELETADO = "Cardápio deletado com sucesso!";

}

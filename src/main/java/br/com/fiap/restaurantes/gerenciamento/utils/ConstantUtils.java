package br.com.fiap.restaurantes.gerenciamento.utils;

import lombok.Data;

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



    //SUCESSO
    public static final String USUARIO_CADASTRADO = "Usuário cadastrado com sucesso!";
    public static final String USUARIO_ATUALIZADO = "Usuário atualizado com sucesso!";
    public static final String USUARIO_DELETADO = "Usuário deletado com sucesso!";
    public static final String SENHA_ALTERADA = "Senha alterada com sucesso!";
    public static final String LOGIN_VALIDADO = "Login validado!";

}

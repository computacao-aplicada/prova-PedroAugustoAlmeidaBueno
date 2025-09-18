public class Validador {

    /**
     * Valida um CPF (Cadastro de Pessoa Física).
     *
     * @param cpf número do CPF (pode conter pontos e traços)
     * @return true se for válido, false caso contrário
     */
    public static boolean validarCPF(String cpf) {
        if (!entradaValida(cpf)) {
            return false;
        }

        String limpo = sanitizar(cpf);

        if (!formatoValido(limpo)) {
            return false;
        }

        if (todosDigitosIguais(limpo)) {
            return false;
        }

        return checarDigitosVerificadores(limpo);
    }

    /**
     * Verifica se a entrada é nula ou vazia.
     */
    private static boolean entradaValida(String cpf) {
        return cpf != null && !cpf.trim().isEmpty();
    }

    /**
     * Remove pontos e traços do CPF.
     */
    private static String sanitizar(String cpf) {
        return cpf.trim().replaceAll("[.\\-]", "");
    }

    /**
     * Verifica se o CPF possui exatamente 11 dígitos numéricos.
     */
    private static boolean formatoValido(String cpf) {
        return cpf.matches("\\d{11}");
    }

    /**
     * Verifica se todos os dígitos do CPF são iguais
     * (casos inválidos como "11111111111").
     */
    private static boolean todosDigitosIguais(String cpf) {
        return cpf.chars().distinct().count() == 1;
    }

    /**
     * Calcula e valida os dois dígitos verificadores (DV) do CPF.
     */
    private static boolean checarDigitosVerificadores(String cpf) {
        int[] digitos = cpf.chars().map(c -> c - '0').toArray();

        int dv1 = calcularDV(digitos, 9, 10); // 1º dígito verificador
        if (digitos[9] != dv1) return false;

        int dv2 = calcularDV(digitos, 10, 11); // 2º dígito verificador
        return digitos[10] == dv2;
    }

    /**
     * Regra de cálculo do dígito verificador do CPF:
     * - Multiplica cada dígito por um peso decrescente.
     * - Soma os resultados.
     * - Obtém o resto da divisão por 11.
     * - Se resto < 2, DV = 0; senão DV = 11 - resto.
     *
     * @param digitos array de dígitos do CPF
     * @param limite quantos dígitos considerar (9 para o DV1, 10 para o DV2)
     * @param pesoInicial peso inicial da multiplicação (10 para DV1, 11 para DV2)
     * @return dígito verificador calculado
     */
    private static int calcularDV(int[] digitos, int limite, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < limite; i++) {
            soma += digitos[i] * (pesoInicial - i);
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}

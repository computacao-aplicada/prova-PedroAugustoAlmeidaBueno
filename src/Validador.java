public class Validador {

    public static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()){

            return false;
        }
        String limpo = cpf.trim().replaceAll("[.-]", "");
        if (!limpo.matches("\\d{11}")) {
            return false;
        }
        if (limpo.chars().distinct().count() == 1) {
            return false;
        }
        // TODO: calcular DV
        return true;

    }
}
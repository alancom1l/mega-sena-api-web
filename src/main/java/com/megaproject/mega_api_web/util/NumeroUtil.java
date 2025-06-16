package com.megaproject.mega_api_web.util;

public class NumeroUtil {

    public static boolean ehPrimo(int numero) {
        if (numero <= 1) return false;
        if (numero == 2) return true;
        if (numero % 2 == 0) return false;
        int limite = (int) Math.sqrt(numero);
        for (int i = 3; i <= limite; i += 2) {
            if (numero % i == 0) return false;
        }
        return true;
    }
}

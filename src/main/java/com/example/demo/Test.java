package com.example.demo;

import java.util.Optional;

public class Test {

    static String stworzDomyslny() {
        System.out.println(">>> stworzDomyslny() zostało wywołane!");
        return "brak";
    }

    public static void main(String[] args) {
        Optional<String> pelne = Optional.of("Jan");

        System.out.println("--- orElse na pełnym Optionalu ---");
        String a = pelne.orElse(stworzDomyslny());
        System.out.println("wynik: " + a);

        System.out.println("--- orElseGet na pełnym Optionalu ---");
        String b = pelne.orElseGet(Test::stworzDomyslny);
        System.out.println("wynik: " + b);
    }

}

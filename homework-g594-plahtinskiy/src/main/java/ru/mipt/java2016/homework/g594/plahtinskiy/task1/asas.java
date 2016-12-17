package ru.mipt.java2016.homework.g594.plahtinskiy.task1;

import ru.mipt.java2016.homework.base.task1.ParsingException;

/**
 * Created by VadimPl on 17.12.16.
 */
public class asas {
    public static void main(String[] args) {
        Double a;
        MyCalculator calc = new MyCalculator();
        try {
             a = calc.calculate("pow(2,3)");
        } catch (ParsingException e) {
            e.printStackTrace();
        }
    }
    String a = "";
}

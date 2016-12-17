package ru.mipt.java2016.homework.g594.plahtinskiy.task1;

import static java.lang.Math.*;
/**
 * Created by VadimPl on 17.12.16.
 */
public class Pow implements Function {
    @Override
    public double Calculate(Double expression1, Double expression2) {
        return pow(expression1, expression2);
    }
}

package com.xzm.spring.el;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Created by Bradish7Y on 15/7/26.
 */
public class TestELParser {
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser() ;
        Expression exp = parser.parseExpression("'Hello World'.concat('!') ");
        String message = (String) exp.getValue();
        System.out.println("message = " + message);

        exp = parser.parseExpression("'Hello World'.length() ");
        System.out.println("message = " + (int) exp.getValue());

        exp = parser.parseExpression("'Hello World'.toUpperCase() ");
        System.out.println("message = " + (String) exp.getValue());
    }
}

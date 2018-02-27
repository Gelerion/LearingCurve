package com.denis.learning.parser;

import java.util.LinkedList;
import java.util.List;


public class Parser {
    private LinkedList<Token> tokens;
    private Token lookAhead;

    public void parse(List<Token> tokens) {
        this.tokens = new LinkedList<>(tokens);
        lookAhead = this.tokens.getFirst();

        expression();

        if (lookAhead.getToken() != Token.EPSILON)
            throw new RuntimeException("Unexpected symbol " + lookAhead + " found");
    }

    private void expression() {
        // expression -> signed_term sum_op
        signedTerm();
        sumOp();

    }

    private void sumOp() {
        if(lookAhead.getToken() == Token.PLUSMINUS) {
            // sum_op -> PLUSMINUS term sum_op
            nextToken();
            term();
        }
        else
        {
            // sum_op -> EPSILON
        }
    }

    private void signedTerm() {
        if(lookAhead.getToken() == Token.PLUSMINUS) {
            nextToken();
            term();
        }
        else {
            // signed_term -> term
            term();
        }
    }

    private void term() {
        // term -> factor term_op
        factor();
        termOp();
    }

    private void termOp() {
        if (lookAhead.getToken() == Token.MULTDIV) {
            // term_op -> MULTDIV factor term_op
            nextToken();
            signedFactor();
            termOp();
        }
        else {
            // term_op -> EPSILON
        }
    }

    private void signedFactor() {
        if (lookAhead.getToken() == Token.PLUSMINUS) {
            // signed_factor -> PLUSMINUS factor
            nextToken();
            factor();
        }
        else {
            // signed_factor -> factor
            factor();
        }
    }

    private void factor() {
        // factor -> argument factor_op
        argument();
        factorOp();
    }

    private void factorOp() {
        if(lookAhead.getToken() == Token.RAISED) {
            // factor_op -> RAISED expression
            nextToken();
            signedFactor();
        }
        else {
            // factor_op -> EPSILON
        }
    }

    private void argument() {
        if(lookAhead.getToken() == Token.FUNCTION) {
            // argument -> FUNCTION argument
            nextToken();
            argument();
        }
        else if (lookAhead.getToken() == Token.OPEN_BRACKET) {
            // argument -> OPEN_BRACKET sum CLOSE_BRACKET
            nextToken();
            expression();

            if(lookAhead.getToken() == Token.CLOSE_BRACKET)
                throw new RuntimeException("Closing brackets expected and " + lookAhead.getSequence() + " found instead");

            nextToken();
        }
        else {
            // argument -> value
            value();
        }
    }

    private void value() {
        if (lookAhead.getToken() == Token.NUMBER) {
            // argument -> NUMBER
            nextToken();
        }
        else if (lookAhead.getToken() == Token.VARIABLE) {
            // argument -> VARIABLE
            nextToken();
        }
        else
        {
            throw new RuntimeException("Unexpected symbol "+lookAhead.getSequence()+" found");
        }
    }

    private void nextToken() {
        tokens.pop();
        // at the end of input we return an epsilon token
        if(tokens.isEmpty()) lookAhead = new Token(Token.EPSILON, "");
        else lookAhead = tokens.getFirst();
    }
}

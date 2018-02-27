package com.denis.learning.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private List<TokenInfo> tokenInfos;
    private List<Token> tokens;

    public Tokenizer() {
        tokenInfos = new ArrayList<>();
        tokens = new ArrayList<>();
    }

    public void addTokenInfo(String regex, int token) {
        tokenInfos.add(new TokenInfo(Pattern.compile("^(" + regex + ")"), token));
    }

    public void tokenize(String str) {
        String tmp = str.trim();
        tokens.clear();

        while (!tmp.equals("")) {
            boolean match = false;

            for (TokenInfo tokenInfo : tokenInfos) {
                Matcher matcher = tokenInfo.regex.matcher(tmp);
                if(matcher.find()) {
                    match = true;
                    String found = matcher.group().trim();
                    tokens.add(new Token(tokenInfo.token, found));

                    tmp = matcher.replaceFirst("").trim();
                    break;
                }
            }

            if (!match) throw new RuntimeException("Unexpected character in input: "+tmp);
        }

    }

    public List<Token> getTokens() {
        return tokens;
    }

    private static class TokenInfo {
        final Pattern regex;
        final int token;

        private TokenInfo(Pattern regex, int token) {
            this.regex = regex;
            this.token = token;
        }
    }
}

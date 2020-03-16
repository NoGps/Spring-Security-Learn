package com.casa.aems.identity.token;

import org.springframework.beans.factory.annotation.Value;

public class JwtProperties {
    @Value("${identity.jwt.signingkey}")
    private String signingKey;

    private int tokenTTL;

    private int refreshTTL;

    private int longTokenTTL;

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public int getTokenTTL() {
        return tokenTTL;
    }

    public void setTokenTTL(int tokenTTL) {
        this.tokenTTL = tokenTTL;
    }

    public int getRefreshTTL() {
        return refreshTTL;
    }

    public void setRefreshTTL(int refreshTTL) {
        this.refreshTTL = refreshTTL;
    }

    public int getLongTokenTTL() {
        return longTokenTTL;
    }

    public void setLongTokenTTL(int longTokenTTL) {
        this.longTokenTTL = longTokenTTL;
    }
}

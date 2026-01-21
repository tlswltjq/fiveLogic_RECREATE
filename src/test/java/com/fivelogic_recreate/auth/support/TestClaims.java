package com.fivelogic_recreate.auth.support;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.RequiredTypeException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestClaims extends HashMap<String, Object> implements Claims {

    public TestClaims(Map<String, Object> map) {
        super(map);
    }

    public TestClaims() {
        super();
    }

    @Override
    public String getIssuer() {
        return (String) get(ISSUER);
    }

    @Override
    public Claims setIssuer(String iss) {
        put(ISSUER, iss);
        return this;
    }

    @Override
    public String getSubject() {
        return (String) get(SUBJECT);
    }

    @Override
    public Claims setSubject(String sub) {
        put(SUBJECT, sub);
        return this;
    }

    @Override
    public String getAudience() {
        return (String) get(AUDIENCE);
    }

    @Override
    public Claims setAudience(String aud) {
        put(AUDIENCE, aud);
        return this;
    }

    @Override
    public Date getExpiration() {
        return (Date) get(EXPIRATION);
    }

    @Override
    public Claims setExpiration(Date exp) {
        put(EXPIRATION, exp);
        return this;
    }

    @Override
    public Date getNotBefore() {
        return (Date) get(NOT_BEFORE);
    }

    @Override
    public Claims setNotBefore(Date nbf) {
        put(NOT_BEFORE, nbf);
        return this;
    }

    @Override
    public Date getIssuedAt() {
        return (Date) get(ISSUED_AT);
    }

    @Override
    public Claims setIssuedAt(Date iat) {
        put(ISSUED_AT, iat);
        return this;
    }

    @Override
    public String getId() {
        return (String) get(ID);
    }

    @Override
    public Claims setId(String jti) {
        put(ID, jti);
        return this;
    }

    @Override
    public <T> T get(String claimName, Class<T> requiredType) {
        Object value = get(claimName);
        if (value == null) {
            return null;
        }
        if (requiredType.isInstance(value)) {
            return requiredType.cast(value);
        }
        throw new RequiredTypeException(
                "Expected value to be of type: " + requiredType + ", but was: " + value.getClass());
    }
}

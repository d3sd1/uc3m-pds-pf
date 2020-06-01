/*
 * Copyright (c) 2020.
 * Content created by:
 * - Andrei García Cuadra
 * - Miguel Hernández Cassel
 *
 * For the module PDS, on university Carlos III de Madrid.
 * Do not share, review nor edit any content without implicitly asking permission to it's owners, as you can contact by this email:
 * andreigarciacuadra@gmail.com
 *
 * All rights reserved.
 */

package transport4future.tokenManagement.model;

import transport4future.tokenManagement.config.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * The type Token.
 */
public class Token {
    private final TokenAlgorytm alg;
    private final TokenType typ;
    private TokenDeviceType deviceType;
    private final long iat;
    private final long exp;
    private final String tokenRequest;
    private final String requestDate;
    private final String notificationEmail;
    private String signature;
    private TokenRevokeType tokenRevokeType;
    private String tokenRevokeReason;

    /**
     * Instantiates a new Token.
     *
     * @param tokenRequest      the device
     * @param NotificationEmail the notification email
     * @param RequestDate       the request date
     */
    public Token(
            String tokenRequest,
            String NotificationEmail,
            String RequestDate,
            TokenDeviceType tokenDeviceType) {
        this.alg = TokenAlgorytm.HS256;
        this.typ = TokenType.PDS;
        this.tokenRequest = tokenRequest;
        this.requestDate = RequestDate;
        this.notificationEmail = NotificationEmail;
        if (Constants.IS_DEV) {
            this.iat = 1590664780535L;
            this.exp = 1656269580535L;
        } else {
            this.iat = System.currentTimeMillis();
            this.exp = this.iat + 65604800000L;
        }
        this.deviceType = tokenDeviceType;

        this.signature = null;
    }

    /**
     * Gets device.
     *
     * @return the device
     */
    public String getTokenRequest() {
        return tokenRequest;
    }

    /**
     * Gets request date.
     *
     * @return the request date
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * Gets notification email.
     *
     * @return the notification email
     */
    public String getNotificationEmail() {
        return notificationEmail;
    }

    /**
     * Is granted boolean.
     *
     * @return the boolean
     */
    public boolean isGranted() {
        return this.iat < System.currentTimeMillis();
    }

    /**
     * Is expired boolean.
     *
     * @return the boolean
     */
    public boolean isExpired() {
        return this.exp <= System.currentTimeMillis();
    }

    /**
     * Gets header.
     *
     * @return the header
     */
    public String getHeader() {
        return "Alg=" + this.alg + "\\n Typ=" + this.typ + "\\n";
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String getPayload() {
        Date iatDate = new Date(this.iat);
        Date expDate = new Date(this.exp);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        return "Dev=" + this.tokenRequest
                + "\\n iat=" + df.format(iatDate)
                + "\\n exp=" + df.format(expDate);
    }

    /**
     * Gets signature.
     *
     * @return the signature
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     * Sets signature.
     *
     * @param value the value
     */
    public void setSignature(String value) {
        this.signature = value;
    }


    /**
     * Check if token is valid, granted, on database and not expired.
     *
     * @return the boolean
     */
    public boolean isValid() {
        return (!this.isExpired()) && (this.isGranted());
    }

    /**
     * Gets alg.
     *
     * @return the alg
     */
    public TokenAlgorytm getAlg() {
        return alg;
    }

    /**
     * Gets typ.
     *
     * @return the typ
     */
    public TokenType getTyp() {
        return typ;
    }

    /**
     * Gets iat.
     *
     * @return the iat
     */
    public long getIat() {
        return iat;
    }

    /**
     * Gets exp.
     *
     * @return the exp
     */
    public long getExp() {
        return exp;
    }

    /**
     * Gets token revoke type.
     *
     * @return the token revoke type
     */
    public TokenRevokeType getTokenRevokeType() {
        return tokenRevokeType;
    }

    /**
     * Sets token revoke type.
     *
     * @param tokenRevokeType the token revoke type
     */
    public void setTokenRevokeType(TokenRevokeType tokenRevokeType) {
        this.tokenRevokeType = tokenRevokeType;
    }

    /**
     * Gets token revoke reason.
     *
     * @return the token revoke reason
     */
    public String getTokenRevokeReason() {
        return tokenRevokeReason;
    }

    /**
     * Sets token revoke reason.
     *
     * @param tokenRevokeReason the token revoke reason
     */
    public void setTokenRevokeReason(String tokenRevokeReason) {
        this.tokenRevokeReason = tokenRevokeReason;
    }

    public TokenDeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(TokenDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return iat == token.iat &&
                exp == token.exp &&
                Objects.equals(alg, token.alg) &&
                Objects.equals(typ, token.typ) &&
                Objects.equals(getTokenRequest(), token.getTokenRequest()) &&
                Objects.equals(getRequestDate(), token.getRequestDate()) &&
                Objects.equals(getNotificationEmail(), token.getNotificationEmail()) &&
                Objects.equals(getSignature(), token.getSignature());
    }

    @Override
    public int hashCode() {
        return Objects.hash(alg, typ, iat, exp, getTokenRequest(), getRequestDate(), getNotificationEmail(), getSignature());
    }

    @Override
    public String toString() {
        return "Token{" +
                "alg=" + alg +
                ", typ=" + typ +
                ", deviceType=" + deviceType +
                ", iat=" + iat +
                ", exp=" + exp +
                ", tokenRequest='" + tokenRequest + '\'' +
                ", requestDate='" + requestDate + '\'' +
                ", notificationEmail='" + notificationEmail + '\'' +
                ", signature='" + signature + '\'' +
                ", tokenRevokeType=" + tokenRevokeType +
                ", tokenRevokeReason='" + tokenRevokeReason + '\'' +
                '}';
    }
}

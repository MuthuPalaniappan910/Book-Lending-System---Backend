package com.booklending.customer.service; 
 
import com.booklending.customer.exception.JwtException; 
import com.booklending.customer.utils.ApplicationConstants; 
import com.booklending.customer.utils.Claim; 
import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.ExpiredJwtException; 
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.MalformedJwtException; 
import io.jsonwebtoken.SignatureAlgorithm; 
import io.jsonwebtoken.SignatureException; 
import io.jsonwebtoken.UnsupportedJwtException; 
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.stereotype.Service; 
 
import java.nio.charset.StandardCharsets; 
import java.util.ArrayList; 
import java.util.Arrays; 
import java.util.Calendar; 
import java.util.Date; 
import java.util.function.Function; 
import java.util.List; 
 
@Service 
public class TokenServiceImpl implements TokenService { 
 
    @Value("${jwt.secret}") 
    private String secret; 
 
    @Value("${jwt.issuer}") 
    private String issuer; 
 
    @Override 
    public String getToken(Long customerId) { 
        return generateToken(customerId); 
    } 
    @Override 
    public String validateToken(String jwtToken, String claimType) { 
        getExpirationDateFromToken(jwtToken); 
        List<String> appendedClaimList = new ArrayList<>(); 
        String claims = getClaimsFromToken(jwtToken); 
        List<String> claimList = new ArrayList<>(Arrays.asList(claims.split(ApplicationConstants.COMMA))); 
        claimList.forEach(claim -> { 
            claim = claim 
                    .replace(ApplicationConstants.OPEN_ARRAY_BRACKET, ApplicationConstants.REPLACEMENT_STRING) 
                    .replace(ApplicationConstants.CLOSE_ARRAY_BRACKET, ApplicationConstants.REPLACEMENT_STRING) 
                    .replace(ApplicationConstants.EMPTY_STRING, ApplicationConstants.REPLACEMENT_STRING); 
            appendedClaimList.add(claim); 
        }); 
        if (appendedClaimList.stream().anyMatch(claim -> claim.equalsIgnoreCase(claimType))) { 
            return getSubjectFromToken(jwtToken); 
        } 
        throw new JwtException(ApplicationConstants.UNAUTHORIZED_USER); 
    } 
 
    private String getClaimsFromToken(String jwtToken) { 
        return getAllClaimsFromToken(jwtToken).get(ApplicationConstants.CLAIM).toString(); 
    } 
 
    private Claims getAllClaimsFromToken(String jwtToken) { 
        try { 
            return Jwts.parser() 
                    .setSigningKey(secret.getBytes()) 
                    .parseClaimsJws(jwtToken) 
                    .getBody(); 
        } catch (ExpiredJwtException expiredException) { 
            throw new JwtException(ApplicationConstants.TOKEN_EXPIRED); 
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException exception) { 
            throw new JwtException(ApplicationConstants.INVALID_TOKEN); 
        } catch (SignatureException signatureException) { 
            throw new JwtException(ApplicationConstants.UNAUTHORIZED_USER); 
        } 
    } 
 
    private String getSubjectFromToken(String jwtToken) { 
        return getClaimFromToken(jwtToken, Claims::getSubject); 
    } 
 
    private <T> T getClaimFromToken(String jwtToken, Function<Claims, T> claimsResolver)  { 
        Claims claims = getAllClaimsFromToken(jwtToken); 
        return claimsResolver.apply(claims); 
    } 
 
    private Date getExpirationDateFromToken(String jwtToken) { 
        return getClaimFromToken(jwtToken, Claims::getExpiration); 
    } 
 
    private String generateToken(Long customerId) { 
        return Jwts.builder() 
                .setExpiration(setExpirationTime()) 
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8)) 
                .setIssuedAt(Calendar.getInstance().getTime()) 
                .setIssuer(issuer) 
                .claim(ApplicationConstants.CLAIM, setClaims()) 
                .setSubject(String.valueOf(customerId)) 
                .compact(); 
    } 
 
    private List<String> setClaims() { 
        List<String> claims = new ArrayList<>(); 
        claims.add(Claim.UPDATE_PROFILE); 
        return claims; 
    } 
 
    private Date setExpirationTime() { 
        return new Date(System.currentTimeMillis() + 
                ApplicationConstants.JWT_TOKEN_VALIDITY * ApplicationConstants.TIME_ADDON_JWT); 
    } 
} 
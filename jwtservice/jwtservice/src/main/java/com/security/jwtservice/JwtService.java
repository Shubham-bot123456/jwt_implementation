package com.security.jwtservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public  class JwtService  {
    private final String SIGN ="HpP4uSoWwvxv0/ZYMJc+edrJiQuolZQLTraLuJEm5KtHDrP+JSaitEeGnyRRuAbkZ2Nc/PQhdzYepJqeqD+kBhRBl4F6h3sUeKvXOovPjS2SFTnBfciLiywyn4gm+GCCaePG6bADRLiPRV1lW28YWbsdw0TRDAaovrQpKnHcuk6djrrkD8ESZC1GoA60GAXXW+z5oINdNXxNUqHmvHl8X6gJUWQhD00NSTibETiwizy3uTZaFsN/v2X7C/uM2oC5Xo/m+AhfzHh6neTvk2T5CyuMYJSBZkPORfNjPAZGKy0grkMzKhdgCRSlThoGP6m5oMdpCP0qHMayQ7rgJM0odoaq+vCT10yFdXJFpPhkuPI=";
    // validation section

   public boolean isTokenValid(String key, UserDetails userDetails){
      String username=extractUsername(key);
      if(username.equals(userDetails.getUsername()) && ! isTokenExpired(key))
          return true;
      else return false;
   }

    private boolean isTokenExpired(String key) {
       return extractClaim(key, Claims::getExpiration).before(new Date());
    }


    // section regarding generation.
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
      return Jwts.builder()
              .signWith(getSigningKey(), SignatureAlgorithm.HS256)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis()+(1000*60*60*24))).setClaims(extraClaims)
              .compact();
    }
    public String generateTokenOnlyWithUserDetails(UserDetails userDetails){
       return  Jwts.builder().
                setSubject(userDetails.getUsername())
                  .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                  .setIssuedAt(new Date())
                  .setExpiration(new Date(System.currentTimeMillis()+(1000*60*60*24)))
                  .compact();
    }

    // section regarding extraction.
    public String extractUsername(String jwt){
          return extractClaim(jwt, Claims::getSubject);
     }
     public <T> T extractClaim(String jwt, Function<Claims, T> claimExtractor){
         Claims claims = extractAllClaims(jwt);
         return claimExtractor.apply(claims);
     }
     public Claims extractAllClaims(String jwt){
         return Jwts.parser().
                 setSigningKey(getSigningKey()).build().parseSignedClaims(jwt).getBody();
     }


    private Key getSigningKey() {
         byte[] secreatKey= Decoders.BASE64.decode(SIGN);
         return Keys.hmacShaKeyFor(secreatKey);
    }



}

package kr.or.kkwk.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class JwtAuthToken implements AuthToken<Claims>{

  private final String token;
  private final Key key;

  public JwtAuthToken(String id, Key key, String role, Map<String, String> claims, Date expiredDate) {
    this.key = key;
    this.token = String.valueOf(createJwtAuthToken(id, role, claims, expiredDate));
  }

  public JwtAuthToken(String token, Key key) {
    this.token = token;
    this.key = key;
  }

  public String getToken(JwtAuthToken token) {
    return token.token;
  }

  public Optional<String> createJwtAuthToken(String id, String role, Map<String, String> claimsMap, Date expiredDate){
    Claims claims = new DefaultClaims(claimsMap);
    claims.put(JwtAuthToken.AUTHORITIES_KEY, role);
    return Optional.ofNullable(Jwts.builder()
            .setSubject(id)
            .addClaims(claims)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(expiredDate)
            .compact()
    ) ;
  }

  @Override
  public boolean validate() {
    return getData() != null;
  }

  @Override
  public Claims getData() {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (SecurityException e){
      log.info("Invalid JWT signature.");
    } catch (MalformedJwtException e) {
      log.info("Invalid JWT token");
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token");
    } catch (UnsupportedJwtException e){
      log.info("Unsupported JWT token.");
    } catch (IllegalArgumentException e) {
      log.info("JWT toekn compact of handler are invalid.");
    }
    return null;
  }
}

package com.chins.blog.backend.security.utils;

import com.chins.blog.backend.security.entity.SecurityUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

  private String header;
  private String secret;
  private Long expiration;

  /***
   * 生成token
   * @param claims
   * @return
   */
  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder().setClaims(claims)
        .setExpiration(new Date(System.currentTimeMillis() + expiration)).signWith(
            SignatureAlgorithm.HS512, secret).compact();
  }

  /***
   * 从Token中获取声明数据
   * @param token
   * @return
   */
  private Claims getClaimsFromToken(String token) {

    try {
      return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      return null;
    }
  }

  /***
   * 生成Token
   * @param username
   * @return
   */
  public String generateTokenFromUsername(String username) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("sub", username);
    claims.put("created", new Date());

    return generateToken(claims);
  }

  /***
   * 从令牌中获取用户名
   * @param token
   * @return
   */
  public String getUsernameFromToken(String token) {
    try {
      Claims claimsFromToken = getClaimsFromToken(token);
      String username = claimsFromToken.getSubject();

      return username;
    } catch (Exception e) {
      return null;
    }
  }

  /***
   * 刷新Token
   * @param token
   * @return
   */
  public String refreshToken(String token) {
    try {
      Claims claimsFromToken = getClaimsFromToken(token);
      claimsFromToken.put("created", new Date());

      return generateToken(claimsFromToken);
    } catch (Exception e) {
      return null;
    }
  }

  /***
   * 校验令牌是否过期
   * @param token
   * @return
   */
  public Boolean isTokenExpired(String token) {
    try {
      Claims claimsFromToken = getClaimsFromToken(token);
      Date expiration = claimsFromToken.getExpiration();
      return expiration.before(new Date());
    } catch (Exception e) {
      return null;
    }
  }

  /***
   * 验证令牌
   * @param token
   * @param userDetails
   * @return
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    SecurityUserDetails user = (SecurityUserDetails) userDetails;
    String username = getUsernameFromToken(token);

    return username.equals(user.getUsername()) && !isTokenExpired(token);
  }

}

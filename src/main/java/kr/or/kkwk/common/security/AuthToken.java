package kr.or.kkwk.common.security;

public interface AuthToken<T> {
  String AUTHORITIES_KEY = "role";
  boolean validate();
  T getData();
}

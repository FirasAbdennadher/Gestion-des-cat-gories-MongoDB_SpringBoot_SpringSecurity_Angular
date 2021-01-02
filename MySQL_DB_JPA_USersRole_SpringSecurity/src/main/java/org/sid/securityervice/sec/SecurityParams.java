package org.sid.securityervice.sec;

public interface SecurityParams {
    public static final String JWT_HEADER_NAME="Authorization";
    public static final String SECRET="firas@gmail.com";
    public static final long DATEEXPIRATION=10*3600*24*1000;
    public static final String HEADERPREFIX="Bearer ";
}

package com.cw.flink.java.pojo;

import java.io.Serializable;

public class IpAndDomains implements Serializable {
    private String ip;
    private String domain;

    public IpAndDomains(){

    }
    public IpAndDomains(String ip, String domain) {
        this.ip = ip;
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}

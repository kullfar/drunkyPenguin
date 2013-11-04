package net.groster.moex.forts.drunkypenguin.core.fast.config.xml;

import javax.xml.bind.annotation.XmlElement;

public class Connection {

    @XmlElement
    private ConnectionType type;
    @XmlElement
    private Protocol protocol;
    @XmlElement(name = "src-ip")
    private String srcIp;
    @XmlElement
    private String ip;
    @XmlElement
    private int port;
    @XmlElement
    private Feed feed;

    public ConnectionType getType() {
        return type;
    }

    public Feed getFeed() {
        return feed;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

}

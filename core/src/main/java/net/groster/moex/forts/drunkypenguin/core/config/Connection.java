package net.groster.moex.forts.drunkypenguin.core.config;

import javax.xml.bind.annotation.XmlElement;

public class Connection {

    @XmlElement
    private ConnectionType type;
    @XmlElement
    private Protocol protocol;
    @XmlElement
    private String ip;
    @XmlElement
    private int port;
    @XmlElement
    private Feed feed;
}

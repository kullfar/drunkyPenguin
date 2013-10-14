package net.groster.moex.forts.drunkypenguin.core.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Protocol {

    @XmlEnumValue("TCP/IP")
    TCP_IP,
    @XmlEnumValue("UDP/IP")
    UDP_IP
}

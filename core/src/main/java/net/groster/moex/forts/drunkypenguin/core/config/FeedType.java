package net.groster.moex.forts.drunkypenguin.core.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum FeedType {

    @XmlEnumValue("RTS-1")
    RTS_1,
    @XmlEnumValue("RTS-5")
    RTS_5,
    @XmlEnumValue("RTS-20")
    RTS_20,
    @XmlEnumValue("RTS-50")
    RTS_50,
    @XmlEnumValue("RTS-INDEX")
    RTS_INDEX,
    @XmlEnumValue("RTS-INSTR")
    RTS_INSTR,
    @XmlEnumValue("RTS-TRADES")
    RTS_TRADES,
    @XmlEnumValue("RTS-NEWS-SYS")
    RTS_NEWS_SYS
}

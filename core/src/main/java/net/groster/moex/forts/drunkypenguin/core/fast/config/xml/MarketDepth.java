package net.groster.moex.forts.drunkypenguin.core.fast.config.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MarketDepth {

    @XmlEnumValue("1")
    DEPTH_1,
    @XmlEnumValue("5")
    DEPTH_5,
    @XmlEnumValue("20")
    DEPTH_20,
    @XmlEnumValue("50")
    DEPTH_50
}

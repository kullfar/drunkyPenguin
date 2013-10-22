package net.groster.moex.forts.drunkypenguin.core.fast.config.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MarketID {

    @XmlEnumValue("F")
    FUTURES,
    @XmlEnumValue("I")
    INDEXES,
    @XmlEnumValue("O")
    OPTIONS,
    @XmlEnumValue("S")
    SPOT,
    @XmlEnumValue("E")
    STANDARD
}

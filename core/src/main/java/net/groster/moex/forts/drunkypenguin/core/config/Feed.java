package net.groster.moex.forts.drunkypenguin.core.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Feed {

    @XmlEnumValue("A")
    A,
    @XmlEnumValue("B")
    B
}

package net.groster.moex.forts.drunkypenguin.core.config;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Configuration {

    @XmlAttribute
    private String type;
    @XmlAttribute
    private String label;
    @XmlAttribute
    private String marketId;
    @XmlElement(name = "MarketDataGroup")
    private List<MarketDataGroup> marketDataGroups;
}

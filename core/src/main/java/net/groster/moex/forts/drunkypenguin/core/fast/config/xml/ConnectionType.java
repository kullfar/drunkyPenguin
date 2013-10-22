package net.groster.moex.forts.drunkypenguin.core.fast.config.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum ConnectionType {

    @XmlEnumValue("Historical Replay")
    HISTORICAL_REPLAY,
    @XmlEnumValue("Incremental")
    INCREMENTAL,
    @XmlEnumValue("Instrument Incremental")
    INSTRUMENT_INCREMENTAL,
    @XmlEnumValue("Instrument Replay")
    INSTRUMENT_REPLAY,
    @XmlEnumValue("News")
    NEWS,
    @XmlEnumValue("Snapshot")
    SNAPSHOT
}

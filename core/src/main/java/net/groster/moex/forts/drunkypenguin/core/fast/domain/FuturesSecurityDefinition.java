package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.util.List;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.SecurityType;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public interface FuturesSecurityDefinition extends CommonSecurityDefinition {

    SecurityType getSecurityType();

    LocalDate getMaturityDate();

    LocalTime getMaturityTime();

    List<InstrumentLeg> getInstrumentLegs();

}

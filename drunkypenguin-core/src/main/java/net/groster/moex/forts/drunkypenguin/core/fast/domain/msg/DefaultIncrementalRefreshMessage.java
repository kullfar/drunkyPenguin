package net.groster.moex.forts.drunkypenguin.core.fast.domain.msg;

import java.util.ArrayList;
import java.util.List;
import net.groster.moex.forts.drunkypenguin.core.Constants;
import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.AbstractFASTMessage;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.MDEntry;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityPK;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MDEntryTradeType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MDEntryType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MDUpdateAction;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.TrdType;
import org.openfast.GroupValue;
import org.openfast.Message;
import org.openfast.ScalarValue;

public class DefaultIncrementalRefreshMessage extends AbstractFASTMessage {

    private final List<MDEntry> mdEntries;

    public DefaultIncrementalRefreshMessage(final Message fastMessage) {
        super(fastMessage);

        final GroupValue[] mdEntriesArray = fastMessage.getSequence("MDEntries").getValues();
        mdEntries = new ArrayList<>(mdEntriesArray.length);
        for (final GroupValue mdEntryValue : mdEntriesArray) {
            final MDEntry mdEntry = new MDEntry();

            mdEntry.setSecurityPK(new SecurityPK(mdEntryValue.getLong("SecurityID"), mdEntryValue.getInt("SecurityIDSource")));
            mdEntry.setRptSeq(mdEntryValue.getInt("RptSeq"));

            final ScalarValue mdEntryDateScalarValue = mdEntryValue.getScalar("MDEntryDate");
            mdEntry.setMdEntryDate(mdEntryDateScalarValue == null ? null : MessageType.FAST_DATE_UTC_FORMATTER.parseDateTime(Integer.toString(
                    mdEntryDateScalarValue.toInt())).withZone(Constants.MOEX_TIME_ZONE).toLocalDate());

            mdEntry.setMdEntryTime(MessageType.FAST_TIME_UTC_FORMATTER.parseDateTime(Integer.toString(mdEntryValue.getInt("MDEntryTime"))).withZone(
                    Constants.MOEX_TIME_ZONE).toLocalTime());

            mdEntry.setMdUpdateAction(MDUpdateAction.valueOf(mdEntryValue.getInt("MDUpdateAction")));
            mdEntry.setMdEntryType(MDEntryType.valueOfByCode(mdEntryValue.getString("MDEntryType")));

            mdEntry.setSymbol(mdEntryValue.getString("Symbol"));

            final ScalarValue exchangeTradingSessionIDScalarValue = mdEntryValue.getScalar("ExchangeTradingSessionID");
            if (exchangeTradingSessionIDScalarValue != null) {
                mdEntry.setExchangeTradingSessionID(exchangeTradingSessionIDScalarValue.toInt());
            }

            final ScalarValue mdEntryPxScalarValue = mdEntryValue.getScalar("MDEntryPx");
            if (mdEntryPxScalarValue != null) {
                mdEntry.setMdEntryPx(mdEntryPxScalarValue.toBigDecimal());
            }

            final ScalarValue mdEntrySizeScalarValue = mdEntryValue.getScalar("MDEntrySize");
            if (mdEntrySizeScalarValue != null) {
                mdEntry.setMdEntrySize(mdEntrySizeScalarValue.toInt());
            }

            final ScalarValue mdEntryIDScalarValue = mdEntryValue.getScalar("MDEntryID");
            if (mdEntryIDScalarValue != null) {
                mdEntry.setMdEntryID(mdEntryIDScalarValue.toInt());
            }

            final ScalarValue mdEntryTradeTypeScalarValue = mdEntryValue.getScalar("MDEntryTradeType");
            if (mdEntryTradeTypeScalarValue != null) {
                final String mdEntryTradeType = mdEntryTradeTypeScalarValue.toString();
                mdEntry.setMdEntryTradeType(MDEntryTradeType.valueOf(mdEntryTradeType.charAt(0)));
                mdEntry.setMdEntryTradeTypeTradeStatus(MDEntryTradeType.TradeStatus.valueOfByCode(mdEntryTradeType.substring(1)));
            }

            final ScalarValue tradeTypeScalarValue = mdEntryValue.getScalar("TrdType");
            if (tradeTypeScalarValue != null) {
                mdEntry.setTrdType(TrdType.valueOf(tradeTypeScalarValue.toInt()));
            }

            final ScalarValue lastPxScalarValue = mdEntryValue.getScalar("LastPx");
            if (lastPxScalarValue != null) {
                mdEntry.setLastPx(lastPxScalarValue.toBigDecimal());
            }

            final ScalarValue securityGroupScalarValue = mdEntryValue.getScalar("SecurityGroup");
            if (securityGroupScalarValue != null) {
                mdEntry.setSecurityGroup(securityGroupScalarValue.toString());
            }

            final ScalarValue currencyScalarValue = mdEntryValue.getScalar("Currency");
            if (currencyScalarValue != null) {
                mdEntry.setCurrency(currencyScalarValue.toString());
            }

            mdEntries.add(mdEntry);
        }


        /*
         TODO:unprocessed yet fields
         <uInt32 name="MarketDepth" id="264" presence="optional">
         <uInt32 name="MDPriceLevel" id="1023" presence="optional"/>
         <int32 name="NumberOfOrders" id="346" presence="optional"/>
         <int32 name="MDFlags" id="20017" presence="optional"/>
         */
    }

    public List<MDEntry> getMdEntries() {
        return mdEntries;
    }
}

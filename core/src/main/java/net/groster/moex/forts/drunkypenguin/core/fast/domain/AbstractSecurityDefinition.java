package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.openfast.GroupValue;
import org.openfast.Message;
import org.openfast.ScalarValue;

public abstract class AbstractSecurityDefinition extends AbstractFASTMessage {

    private final long securityID;
    private final int securityIDSource;
    private final int totNumReports;
    private final String symbol;
    private final String securityDesc;
    private final String cfiCode;
    private final BigDecimal minPriceIncrement;
    private final String currency;
    private final BigDecimal lowLimitPx;
    private final BigDecimal highLimitPx;
    private final MarketSegmentID marketSegmentID;
    private final MarketID marketID;
    private final Integer securityTradingStatus;
    private final List<MDFeedType> mdFeedTypes;

    public AbstractSecurityDefinition(final Message fastMessage) {
        super(fastMessage);
        securityID = fastMessage.getLong("SecurityID");
        securityIDSource = fastMessage.getInt("SecurityIDSource");
        totNumReports = fastMessage.getInt("TotNumReports");
        symbol = fastMessage.getString("Symbol");
        securityDesc = fastMessage.getString("SecurityDesc");
        cfiCode = fastMessage.getString("CFICode");

        final ScalarValue minPriceIncrementScalarValue = fastMessage.getScalar("MinPriceIncrement");
        minPriceIncrement = minPriceIncrementScalarValue == null ? null : minPriceIncrementScalarValue.toBigDecimal();

        currency = fastMessage.getString("Currency");

        final ScalarValue lowLimitPxScalarValue = fastMessage.getScalar("LowLimitPx");
        lowLimitPx = lowLimitPxScalarValue == null ? null : lowLimitPxScalarValue.toBigDecimal();

        final ScalarValue highLimitPxScalarValue = fastMessage.getScalar("HighLimitPx");
        highLimitPx = highLimitPxScalarValue == null ? null : highLimitPxScalarValue.toBigDecimal();

        marketSegmentID = MarketSegmentID.valueOf(fastMessage.getString("MarketSegmentID"));
        marketID = MarketID.valueOf(fastMessage.getString("MarketID"));

        final ScalarValue securityTradingStatusScalarValue = fastMessage.getScalar("SecurityTradingStatus");
        securityTradingStatus = securityTradingStatusScalarValue == null ? null : securityTradingStatusScalarValue.
                toInt();

        final GroupValue[] mdFeedTypesArray = fastMessage.getSequence("MDFeedTypes").getValues();
        final List<MDFeedType> mdFeedTypesList = new ArrayList<>(mdFeedTypesArray.length);
        for (final GroupValue mdFeedTypeValue : mdFeedTypesArray) {
            final MDFeedType mdFeedType = new MDFeedType();

            mdFeedType.setMdFeedType(mdFeedTypeValue.getString("MDFeedType"));

            final ScalarValue MarketDepthScalarValue = mdFeedTypeValue.getScalar("MarketDepth");
            mdFeedType.setMarketDepth(MarketDepthScalarValue == null ? null : MarketDepthScalarValue.toInt());

            final ScalarValue MDBookTypeScalarValue = mdFeedTypeValue.getScalar("MDBookType");
            mdFeedType.setMdBookType(MDBookTypeScalarValue == null ? null : MDBookTypeScalarValue.toInt());

            mdFeedTypesList.add(mdFeedType);
        }
        mdFeedTypes = mdFeedTypesList;
    }
}

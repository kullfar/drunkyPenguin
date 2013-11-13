package net.groster.moex.forts.drunkypenguin.core.fast.domain;

public class MDFeedType {

    private String mdFeedType;
    private Integer marketDepth;
    private Integer mdBookType;

    public void setMdFeedType(final String mdFeedType) {
        this.mdFeedType = mdFeedType;
    }

    public void setMarketDepth(final Integer marketDepth) {
        this.marketDepth = marketDepth;
    }

    public void setMdBookType(final Integer mdBookType) {
        this.mdBookType = mdBookType;
    }

}

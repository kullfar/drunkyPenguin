package net.groster.moex.forts.drunkypenguin.core.fast.domain;

public class SecurityPK {

    private final long securityID;
    private final int securityIDSource;

    public SecurityPK(final long securityID, final int securityIDSource) {
        this.securityID = securityID;
        this.securityIDSource = securityIDSource;
    }

    @Override
    public int hashCode() {
        return securityIDSource + (int) securityID;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SecurityPK other = (SecurityPK) obj;
        if (securityID != other.getSecurityID()) {
            return false;
        }
        return securityIDSource == other.getSecurityIDSource();
    }

    public long getSecurityID() {
        return securityID;
    }

    public int getSecurityIDSource() {
        return securityIDSource;
    }

}

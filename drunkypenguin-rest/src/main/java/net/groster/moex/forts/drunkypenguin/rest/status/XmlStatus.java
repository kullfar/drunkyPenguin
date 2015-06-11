package net.groster.moex.forts.drunkypenguin.rest.status;

import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "status")
public class XmlStatus {

    @XmlElementWrapper(name = "fast-config-files")
    @XmlElements({
        @XmlElement(name = "fast-config-file")})
    private Set<XmlFastConfigFileUptodateState> xmlFastConfigFileUptodateStates;

    public void setXmlFastConfigFileUptodateStates(
            final Set<XmlFastConfigFileUptodateState> xmlFastConfigFileUptodateStates) {
        this.xmlFastConfigFileUptodateStates = xmlFastConfigFileUptodateStates;
    }
}

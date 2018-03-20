package sylvanas.container.startup;

/**
 * <Sylvanas>
 *     <Connector port="8888" protocol="HTTP/1.1" connectionTimeout="20000"></Connector>
 *
 *      <Host name="localhost"  appBase="webApps" unpackWARs="true" autoDeploy="true">

            <Context path="/index/abc" docBase="Sylvanas" reloadable="true" />
                <Valve className="org.apache.catalina.authenticator.SingleSignOn" />
                <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
            </Context>
            <Valve className="org.apache.catalina.authenticator.SingleSignOn" />
            <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
        </Host>
 *
 * </Sylvanas>
 *
 *
 *
 */
public class HostConfig {

}

window.mallotore = window.mallotore || {};

(function (mallotore) {
    
    var OverviewNetStats = React.createClass({
        render: function() {
            var netStats = this.props.serverStats.netStats;
            return (
                    <div>
                        <h5>Información de red</h5>
                        <ul>
                            <li>Primary Net MAsk: {netStats.primaryNetMAsk}</li>
                            <li>Primary Mac Address: {netStats.primaryMacAddress}</li>
                            <li>Secondary Dns: {netStats.secondaryDns}</li>
                            <li>Domain Name: {netStats.domainName}</li>
                            <li>Default Gateway: {netStats.defaultGateway}</li>
                            <li>Host Name: {netStats.hostName}</li>
                            <li>Primary Dns: {netStats.primaryDns}</li>
                            <li>Primary Ip Address: {netStats.primaryIpAddress}</li>
                            <li>Primary Interface: {netStats.primaryInterface}</li>
                        </ul>
                    </div>
                   );
        }
    });

    var OverviewOperatingSystemInfo = React.createClass({
        render: function() {
            var operatingSystem = this.props.serverStats.operatingSystem;
            return (
                    <div>
                        <h5>Sistema operativo</h5>
                        <ul>
                            <li>vendorVersion: {operatingSystem.vendorVersion}</li>
                            <li>vendor: {operatingSystem.vendor}</li>
                            <li>dataModel: {operatingSystem.dataModel}</li>
                            <li>arch: {operatingSystem.arch}</li>
                            <li>version: {operatingSystem.version}</li>
                            <li>vendorName: {operatingSystem.vendorName}</li>
                            <li>patchLevel: {operatingSystem.patchLevel}</li>
                            <li>description: {operatingSystem.description}</li>
                            <li>name: {operatingSystem.name}</li>
                            <li>cpuEndian: {operatingSystem.cpuEndian}</li>
                        </ul>
                    </div>
                   );
        }
    });

    var OverviewStatsCreationDate = React.createClass({
        render: function() {
            var creationDate = this.props.serverStats.creationDate;
            var formattedDate = creationDate.replace("T", " ").replace("Z", "");
            return (
                    <div>({formattedDate})</div>
                   );
        }
    });

    var OverviewStatsUptime = React.createClass({
        render: function() {
            var uptime = this.props.serverStats.uptimeStats.uptime;
            if(uptime){
                return buildDiv({ color: 'green' }, ("Uptime: " + uptime));
            }
            return buildDiv({ color: 'red' }, this.props.serverStats.uptimeStats.error);

            function buildDiv(style, message){
                return <div style={style}>({message})</div>;
            }
        }
    });

    var OverviewStatsTemperature = React.createClass({
        render: function() {
            var temperature = this.props.temperatureStats.temperature;
            var divStyle = {  
                    background: 'red', 
                    height: '25px', 
                    width:'150px',
                    fontFamily: 'Montserrat,Helvetica Neue,Helvetica,Arial,sans-serif',
                    textAlign: 'center',
                    fontweight: 'bold',
                    background: 'green'
            };

            if(temperature > 27){
                divStyle.background = 'red';
                return buildDiv(divStyle, temperature);
            }
            if(temperature > 25){
                divStyle.background = 'orange';
                return buildDiv(divStyle, temperature);
            }
            return buildDiv(divStyle, temperature);

            function buildDiv(style, message){
                return <div style={style}>{message}ºC</div>;
            }
        }
    });

    var OverviewActiveServices = React.createClass({
        render: function() {
            var activeServices = this.props.serverStats.activeServices;
            return (
                    <div>
                        <h5>Servicios activos</h5>
                        <ul>
                            <li><strong>Apache: </strong><span style={{background: activeServices.apache ? 'green' : 'red'}}>{activeServices.apache ? 'Activado' : 'Desativado'}</span></li>
                            <li><strong>Mysql: </strong><span style={{background: activeServices.mysql ? 'green' : 'red'}}>{activeServices.mysql ? 'Activado' : 'Desativado'}</span></li>
                            <li><strong>Oracle: </strong> <span style={{background: activeServices.oracle ? 'green' : 'red'}}>{activeServices.oracle ? 'Activado' : 'Desativado'}</span></li>
                            <li><strong>SQL Server: </strong> <span style={{background: activeServices.sql ? 'green' : 'red'}}>{activeServices.sql ? 'Activado' : 'Desativado'}</span></li>
                            <li><strong>Http: </strong> <span style={{background: activeServices.http ? 'green' : 'red'}}>{activeServices.http ? 'Activado' : 'Desativado'}</span></li>
                            <li><strong>Ftp: </strong> <span style={{background: activeServices.ftp ? 'green' : 'red'}}>{activeServices.ftp ? 'Activado' : 'Desativado'}</span></li>
                            <li><strong>Tomcat: </strong> <span style={{background: activeServices.tomcat ? 'green' : 'red'}}>{activeServices.tomcat ? 'Activado' : 'Desativado'}</span></li>
                            <li><strong>IIS: </strong> <span style={{background: activeServices.iis ? 'green' : 'red'}}>{activeServices.iis ? 'Activado' : 'Desativado'}</span></li>
                        </ul>
                    </div>
                   );
        }
    });

    function renderOverviewNetStatsInformation(server, serverStats){
        ReactDOM.render(
            <OverviewNetStats server={server} serverStats={serverStats} />,
            document.getElementById('netStatsInformation_' + server.id)
        );
    }

    function renderOverviewOperatingSystemInfo(server, serverStats){
        ReactDOM.render(
            <OverviewOperatingSystemInfo server={server} serverStats={serverStats} />,
            document.getElementById('operatingSystemInfo_' + server.id)
        );
    }

    function renderOverviewStatsCreationDate(server, serverStats){
        ReactDOM.render(
            <OverviewStatsCreationDate server={server} serverStats={serverStats} />,
            document.getElementById('statsCreationDate_' + server.id)
        );
    }

    function renderOverviewStatsUptime(server, serverStats){
        ReactDOM.render(
            <OverviewStatsUptime server={server} serverStats={serverStats} />,
            document.getElementById('uptime_' + server.id)
        );
    }

    function renderOverviewStatsTemperature(temperatureStats){
        ReactDOM.render(
            <OverviewStatsTemperature temperatureStats={temperatureStats}/>,
            document.getElementById('temperature')
        );
    }

    function renderOverviewActiveServices(server, serverStats){
        ReactDOM.render(
            <OverviewActiveServices server={server} serverStats={serverStats} />,
            document.getElementById('activeServices_' + server.id)
        );
    }
    
    mallotore.stats = mallotore.stats || {};
    mallotore.stats.renderOverviewNetStatsInformation = renderOverviewNetStatsInformation;
    mallotore.stats.renderOverviewOperatingSystemInfo = renderOverviewOperatingSystemInfo;
    mallotore.stats.renderOverviewStatsCreationDate = renderOverviewStatsCreationDate;
    mallotore.stats.renderOverviewStatsUptime = renderOverviewStatsUptime;
    mallotore.stats.renderOverviewStatsTemperature = renderOverviewStatsTemperature;
    mallotore.stats.renderOverviewActiveServices = renderOverviewActiveServices;

})(window.mallotore);
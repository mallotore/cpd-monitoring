window.mallotore = window.mallotore || {};

(function (mallotore) {
    
    var HistoricalNetStats = React.createClass({
        render: function() {
            var netStats = this.props.serverStats.netStats;
            return (
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
                   );
        }
    });

    var HistoricalOperatingSystemInfo = React.createClass({
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

    function renderOverviewNetStatsInformation(server, serverStats){
        ReactDOM.render(
            <HistoricalNetStats server={server} serverStats={serverStats} />,
            document.getElementById('netStats_' + server.id)
        );
    }

    function renderHistoricalOperatingSystem(server, serverStats){
        ReactDOM.render(
            <HistoricalOperatingSystemInfo server={server} serverStats={serverStats} />,
            document.getElementById('operatingSystem_' + server.id)
        );
    }
    
    mallotore.stats = mallotore.stats || {};
    mallotore.stats.renderHistoricalNetStatsInformation = renderOverviewNetStatsInformation;
    mallotore.stats.renderHistoricalOperatingSystem = renderHistoricalOperatingSystem;

})(window.mallotore);
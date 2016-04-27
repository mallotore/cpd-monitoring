window.mallotore = window.mallotore || {};

(function (mallotore) {
    
    var OverviewNetStats = React.createClass({
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

    var OverviewOperatingSystemInfo = React.createClass({
        render: function() {
            var operatingSystem = this.props.serverStats.operatingSystem;
            return (
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
            var divStyle = {
              color: 'green'
            };
            if(!uptime){
                divStyle.color = 'red';
                return (
                    <div style={divStyle}>({this.props.serverStats.uptimeStats.error})</div>
                   );
            }
            return (
                    <div style={divStyle}>(Uptime: {uptime})</div>
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
    
    mallotore.stats = mallotore.stats || {};
    mallotore.stats.renderOverviewNetStatsInformation = renderOverviewNetStatsInformation;
    mallotore.stats.renderOverviewOperatingSystemInfo = renderOverviewOperatingSystemInfo;
    mallotore.stats.renderOverviewStatsCreationDate = renderOverviewStatsCreationDate;
    mallotore.stats.renderOverviewStatsUptime = renderOverviewStatsUptime;

})(window.mallotore);
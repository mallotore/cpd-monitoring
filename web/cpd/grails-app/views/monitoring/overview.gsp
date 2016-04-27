<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="default">
        <parameter name="defMonitoringStats" value="1" />
        <parameter name="defMonitoringStatsOverview" value="1" />
        <asset:stylesheet src="config/config.css"/>
        <asset:javascript src="stats/overview.js"/>
    </head>
    <body>
        <div id="jGrowl-container" class="jGrowl top-right"></div>
        <section style="min-height: 721px;">
            <div class="container">
                <table  class="table"> 
                    <tbody id="serverConfigurationTable">
                        <g:each var="server" in="${servers}">
                            <tr>
                                <td style="width: 50%">
                                    <h3>
                                        <div style="float: left;">${server.name}</div>
                                        <div style="margin-left: 10px; float: left;" id="uptime_${server.id}"></div>
                                    </h3>
                                </td>
                                <td style="width: 50%">
                                    <h3>
                                        <div style="float: left">${server.ip}:${server.port}</div>
                                        <div style="float:right;" id="statsCreationDate_${server.id}"></div>
                                    </h3>
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 50%" id="operatingSystemInfo_${server.id}"></td>
                                <td scope="row" style="width: 50%">
                                    <div id="cpuPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 50%" id="netStatsInformation_${server.id}"></td>
                                <td scope="row" style="width: 50%">
                                    <div id="diskPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td scope="row" style="width: 50%">
                                    <div id="ramPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                                <td scope="row" style="width: 50%">
                                    <div id="swapPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td scope="row" style="width: 50%">
                                    <div id="whoList_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td><br/></td>
                            </tr>
                        </g:each>
                    </tbody> 
                </table>
            </div>
        </section>
    </body>
</html>
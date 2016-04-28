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
                <table  class="table" > 
                    <tbody id="serverConfigurationTable">
                        <tr>
                            <td style="border: none;" colspan="2">
                                <label style="width: 30%;font-family: Montserrat,Helvetica Neue,Helvetica,Arial,sans-serif;font-weight: bold;">Autorefresco en segundos</label>
                                <input id="overviewStatsInterval" type="text" style="width: 30%;display:inline;" class="form-control" value="30" placeholder="segundos"/>
                                <button id="updateRefreshInterval" type="button" class="btn btn-default">
                                      <span class="glyphicon glyphicon-time" aria-hidden="true"></span>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td style="border: none;" colspan="2">
                                <h3>
                                    <label style="font-family: Montserrat,Helvetica Neue,Helvetica,Arial,sans-serif;font-weight: bold; float: left;">Temperatura</label>
                                    <div id="temperature" style="margin-left: 10px;float: left;"></div>
                                </h3>
                            </td>
                        </tr>
                        <g:each var="server" in="${servers}">
                            <tr style="border-top:0px !important;">
                                <td style="width: 50%; border: none;">
                                    <h3>
                                        <div style="float: left;">${server.name}</div>
                                        <div style="margin-left: 10px; float: left;" id="uptime_${server.id}"></div>
                                    </h3>
                                </td>
                                <td style="width: 50%; border: none;">
                                    <h3>
                                        <div style="float: left">${server.ip}:${server.port}</div>
                                        <div style="float:right;" id="statsCreationDate_${server.id}"></div>
                                    </h3>
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 50%; border: none;" id="operatingSystemInfo_${server.id}"></td>
                                <td scope="row" style="width: 50%; border: none;">
                                    <div id="cpuPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 50%; border: none;" id="netStatsInformation_${server.id}"></td>
                                <td scope="row" style="width: 50%; border: none;">
                                    <div id="diskPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td scope="row" style="width: 50%; border: none;">
                                    <div id="ramPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                                <td scope="row" style="width: 50%; border: none;">
                                    <div id="swapPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td scope="row" style="width: 50%; border: none;">
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
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
                                <td scope="row" style="width: 25%">
                                    <div id="diskPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                                <td scope="row" style="width: 25%">
                                    <div id="ramPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                                <td scope="row" style="width: 25%">
                                    <div id="swapPercentage_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                        </g:each>
                    </tbody> 
                </table>
            </div>
        </section>
    </body>
</html>
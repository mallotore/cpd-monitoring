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
                <g:each var="server" in="${servers}">
                    <div id="overviewStats_${server.id}"></div>
                </g:each>
            </div>
        </section>
    </body>
</html>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="default">
        <parameter name="defMonitoringStats" value="1" />
        <asset:stylesheet src="config/config.css"/>
        <asset:javascript src="stats/historical.js"/>
    </head>
    <body>
        <div id="jGrowl-container" class="jGrowl top-right"></div>
        <section style="min-height: 721px;">
            <div class="container">
                <g:render template="/monitoring/tabs" model="[selected: 'historical']"/>
                <table class="table" > 
                    <tbody id="serverConfigurationTable">
                        <tr>
                            <td style="border: none;" colspan="2">
                                <div style="margin-left: 10px; float: left;" id="temperature"></div>
                            </td>
                        </tr>
                        <g:each var="server" in="${servers}">
                              <tr style="border-top:0px !important;">
                                <td style="width: 50%; border: none;">
                                    <h3>
                                        <div style="float: left;">${server.name}</div>
                                    </h3>
                                </td>
                                <td style="width: 50%; border: none;">
                                    <h3>
                                        <div style="float: left">${server.ip}:${server.port}</div>
                                    </h3>
                                </td>
                            </tr>
                            <tr>
                                <td scope="row" style="width: 50%; border: none;">
                                    <div id="cpu_${server.id}" style="height: 250px;"></div>
                                </td>
                                <td scope="row" style="width: 50%; border: none;">
                                    <div id="diskSpace_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td scope="row" style="width: 50%; border: none;">
                                    <div id="ram_${server.id}" style="height: 250px;"></div>
                                </td>
                                <td scope="row" style="width: 50%; border: none;">
                                    <div id="swap_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td scope="row" style="width: 50%; border: none;">
                                    <div id="wholist_${server.id}" style="height: 250px;"></div>
                                </td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
        </section>
    </body>
</html>
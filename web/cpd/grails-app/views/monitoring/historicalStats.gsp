<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="default">
        <parameter name="defMonitoringStats" value="1" />
        <asset:stylesheet src="config/config.css"/>
    </head>
    <body>
        <div id="jGrowl-container" class="jGrowl top-right"></div>
        <section style="min-height: 721px;">
            <div class="container">
                <g:render template="/monitoring/tabs" model="[selected: 'historical']"/>
                
            </div>
        </section>
    </body>
</html>
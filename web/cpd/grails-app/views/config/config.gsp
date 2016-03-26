<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="default">
        <asset:stylesheet src="config/config.css"/>
        <asset:javascript src="configuration/configuration.js"/>
    </head>
    <body>
    <div id="jGrowl-container" class="jGrowl top-right"></div>
        <section style="min-height: 721px;">
            
            <g:render template="/config/tabs"/> 

            <div class="container">
                <div class="panel panel-default">  
                    <div class="panel-heading">${servers.size()} ${servers.size() == 1 ? 'Servidor' : 'Servidores'}</div>  
                    <table  class="table"> 
                        <thead> 
                            <tr> 
                                <th>Nombre</th> 
                                <th>Ip</th>
                                <th>Puerto</th>
                                <th>Servicio</th> 
                                <th>
                                    <button id="showAddNewServer" type="button" class="btn btn-default">
                                          <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                    </button>
                                </th> 
                            </tr> 
                        </thead> 
                        <tbody id="serverConfigurationTable">
                            <g:render template="/config/addServer"/>
                            <g:render template="/config/serverConfigurationTemplate"/>
                            <g:each var="server" in="${servers}">
                                <g:render template="/config/serverConfiguration" model="[ server : server ]"/>
                            </g:each>
                        </tbody> 
                    </table> 
                </div>
            </div>
        </section>
    </body>
</html>
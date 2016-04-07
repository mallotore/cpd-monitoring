<div class="panel panel-default">
    <div class="panel-heading">Temperatura</div>  
    <table class="table">
        <tbody id="temperatureConfigurationTable">
            <tr style="font-weight: normal !important;"> 
                <th scope="row" style="width: 20%">
                    <span style="float: left;">
                        <label style="font-weight: normal;" for="temperature_interval_text">Intervalo en segundos</label>
                    </span>
                </th>
                <td  style="width: 65%">
                    <span style="float: left; margin-left:10px">
                        <label id="temperature_interval_label" style="font-weight: normal;">${temperatureProbeIntervalInSeconds ?: 'Sin definir'}</label>
                        <input id="create_temperature_interval_text" type="text" class="form-control" style="display:none;" placeholder="Intervalo en segundos">
                        <input id="edit_temperature_interval_text" value="${temperatureProbeIntervalInSeconds}" type="text" class="form-control" style="display:none;" placeholder="Intervalo en segundos">
                    </span>
                </td> 
                <td style="width: 15%">
                    <div id="showCreationActionButtons_temperature" style="${temperatureProbeIntervalInSeconds ? 'display:none;' : ''}">
                        <button id="showCreateTemperatureIntervalButton" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        </button>
                    </div>
                    <div id="creationActionButtons_temperature" style="display:none;">
                        <button id="createTemperatureIntervalButton" type="button" class="btn btn-default">
                          <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                        </button>
                        <button id="cancelTemperatureIntervalCreationButton" type="button" class="btn btn-default">
                          <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </button>
                    </div>
                    <div id="actionButtons_temperature" style="${!temperatureProbeIntervalInSeconds ? 'display:none;' : ''}">
                        <button id="showEditTemperatureIntervalButton" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
                        <button id="deleteTemperatureIntervalButton" type="button" class="btn btn-default">
                              <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </button>
                    </div>
                    <div id="editTemperatureIntervalButtons" style="display:none;">
                        <button id="updateTemperatureIntervalButton" type="button" class="btn btn-default">
                          <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                        </button>
                        <button id="cancelTemperatureIntervalEditionButton" type="button" class="btn btn-default">
                          <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </button>
                    </div>
                </td> 
            </tr>
        </tbody> 
    </table>
</div>
<table class="table"> 
    <thead> 
        <tr> 
            <th>Intervalo de sondeo en segundos</th>
        </tr> 
    </thead> 
    <tbody id="temperatureConfigurationTable">
        <tr style="font-weight: normal !important;"> 
            <th scope="row" style="width: 80%">
                <span>
                    <label id="temperature_interval_label" style="font-weight: normal;">${temperatureProbeInterval}</label>
                    <input id="temperature_interval_text" value="${temperatureProbeInterval}" type="text" class="form-control" style="display:none;" placeholder="Intervalo en segundos">
                </span>
            </th>
            <td style="width: 20%">
                <div id="actionButtons_temperature">
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
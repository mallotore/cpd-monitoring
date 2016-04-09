<div class="panel panel-default">
    <div class="panel-heading">Temperatura</div>  
    <table class="table">
        <tbody>
            <tr style="font-weight: normal !important;"> 
                <th scope="row" style="width: 20%">
                    <span style="float: left;">
                        <label style="font-weight: normal;" for="temperature_interval_text">Intervalo en segundos</label>
                    </span>
                </th>
                <td id="temperature_interval_container" style="width: 65%">
                    <span id="temperature_interval_container">
                        <label id="temperature_interval_label" style="font-weight: normal;">${temperatureProbeIntervalInSeconds ?: 'Sin definir'}</label>
                        <input id="create_temperature_interval_text" type="text" class="form-control" style="display:none;width: 15% !important;" placeholder="segundos" maxlength="10">
                        <input id="edit_temperature_interval_text" value="${temperatureProbeIntervalInSeconds}" type="text" class="form-control" style="display:none;width: 15% !important;" placeholder="segundos" maxlength="10">
                    </span>
                </td> 
                <td style="width: 15%">
                    <div>
                        <button id="showCreateTemperatureIntervalActionsButton" type="button" class="btn btn-default" style="${temperatureProbeIntervalInSeconds ? 'display:none;' : ''}">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        </button>
                        <button id="createTemperatureIntervalButton" type="button" class="btn btn-default" style="display:none;">
                          <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
                        </button>
                        <button id="cancelTemperatureIntervalCreationButton" type="button" class="btn btn-default" style="display:none;">
                          <span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>
                        </button>
                        <button id="showEditTemperatureIntervalButton" type="button" class="btn btn-default" style="${!temperatureProbeIntervalInSeconds ? 'display:none;' : ''}">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
                        <button id="removeTemperatureIntervalButton" type="button" class="btn btn-default" style="${!temperatureProbeIntervalInSeconds ? 'display:none;' : ''}">
                              <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                        </button>
                        <button id="editTemperatureIntervalButton" type="button" class="btn btn-default" style="display:none;">
                          <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
                        </button>
                        <button id="cancelTemperatureIntervalEditionButton" type="button" class="btn btn-default" style="display:none;">
                          <span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>
                        </button>
                    </div>
                </td> 
            </tr>
        </tbody> 
    </table>
</div>
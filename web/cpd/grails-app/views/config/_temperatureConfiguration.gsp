<div class="panel panel-default">
    <div class="panel-heading">Temperatura</div>  
    <table class="table">
        <thead>
            <tr> 
                <th style="width: 40%">Intervalo en segundos</th> 
                <th style="width: 45%">Alertas</th>
                <th style="width: 15%"></th>
            </tr> 
        </thead>
        <tbody>
            <tr style="font-weight: normal !important;">
                <td id="temperature_interval_container" style="width: 40%">
                    <span id="temperature_interval_container">
                        <label id="temperature_interval_label" style="font-weight: normal;">${temperature?.probeIntervalInSeconds ?: 'Sin definir'}</label>
                        <input id="create_temperature_interval_text" type="text" class="form-control" style="display:none;width: 40% !important;" placeholder="segundos" maxlength="10">
                        <input id="edit_temperature_interval_text" value="${temperatureProbeIntervalInSeconds}" type="text" class="form-control" style="display:none;width: 40% !important;" placeholder="segundos" maxlength="10">
                    </span>
                </td>
                <td style="width: 45%">
                    <table> 
                        <tr>
                            <td>
                                <label>Temperatura</label>
                            </td>
                            <td>
                                <label id="temperature_alert_label" style="width: 100%;font-weight: normal;margin-left:10px">${temperature?.overTemperatureAlert ?: 'Desactivada'}</label>
                                <input id="create_temperature_alert_text" type="text" class="form-control" style="display:none;width: 70% !important;" placeholder="temperatura">
                                <input id="edit_temperature_alert_text" type="text" class="form-control" style="display:none;width: 70% !important;" placeholder="temperatura">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Conectividad</label>
                            </td>
                            <td>
                                <label id="connectivity_alert_temperature_label" style="width: 100%;font-weight: normal;margin-left:10px">${temperature?.connectivityAlert ? 'Activada' : 'Desactivada'}</label>
                                <input id="connectivity_alert_temperature_text" type="checkbox" style="display:none; margin-left:10px" checked="${temperature?.connectivityAlert ? 'checked' : ''}">
                            </td>
                        </tr>
                    </table>
                </td>
                <td style="width: 15%">
                    <div>
                        <button id="showCreateTemperatureIntervalActionsButton" type="button" class="btn btn-default" style="${temperature?.probeIntervalInSeconds ? 'display:none;' : ''}">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        </button>
                        <button id="createTemperatureIntervalButton" type="button" class="btn btn-default" style="display:none;">
                          <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
                        </button>
                        <button id="cancelTemperatureIntervalCreationButton" type="button" class="btn btn-default" style="display:none;">
                          <span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>
                        </button>
                        <button id="showEditTemperatureIntervalButton" type="button" class="btn btn-default" style="${!temperature?.probeIntervalInSeconds ? 'display:none;' : ''}">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
                        <button id="removeTemperatureIntervalButton" type="button" class="btn btn-default" style="${!temperature?.probeIntervalInSeconds ? 'display:none;' : ''}">
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
<tr id="server_configuration_container_template" style="display:none;">
    <th scope="row" style="width: 15%">
        <span>
            <label id="name_label_#server.id#" style="font-weight: normal;"></label>
            <input id="name_text_#server.id#" type="text" class="form-control" style="display:none;" placeholder="Nombre">
        </span>
    </th>
    <td  style="width: 15%">
        <span>
            <label id="ip_label_#server.id#" style="width: 100%;font-weight: normal;"></label>
            <input id="ip_text_#server.id#" type="text" class="form-control" style="display:none;" placeholder="IP">
        </span>
    </td> 
    <td  style="width: 10%">
        <span>
            <label id="port_label_#server.id#" style="width: 100%;font-weight: normal;"></label>
            <input id="port_text_#server.id#"type="text" class="form-control" style="display:none;" placeholder="Puerto">
        </span>
    </td> 
    <td style="width: 20%">
        <span>
            <label id="probeInterval_label_#server.id#" style="width: 100%;font-weight: normal;"></label>
            <input id="probeInterval_text_#server.id#" type="text" class="form-control" style="display:none;" placeholder="Intervalo">
        </span>
    </td>
    <td style="width: 25%">
        <table> 
            <tr>
                <td>
                    <label>Espacio (%)</label>
                </td>
                <td>
                    <label id="diskSpaceAlert_label_#server.id#" style="width: 100%;font-weight: normal;margin-left:10px"></label>
                    <input id="diskSpaceAlert_text_#server.id#" style="display:none;margin-left:10px"type="text" class="form-control" placeholder="porcentaje">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Conectividad</label>
                </td>
                <td>
                    <label id="connectivityAlert_label_#server.id#" style="width: 100%;font-weight: normal;margin-left:10px"></label>
                    <input id="connectivityAlert_text_#server.id#" type="checkbox" style="display:none; margin-left:10px">
                </td>
            </tr>
        </table>
    </td>
    <td style="width: 15%">
        <div id="actionButtons_#server.id#">
            <button id="showEditServerButton_#server.id#" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
            </button>
            <button id="deleteServerButton_#server.id#" type="button" class="btn btn-default">
                  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
        </div>
        <div id="editButtons_#server.id#" style="display:none;">
            <button id="updateServerButton_#server.id#" type="button" class="btn btn-default">
              <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
            </button>
            <button id="cancelServerEditionButton_#server.id#" type="button" class="btn btn-default">
              <span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>
            </button>
        </div>
    </td>
</tr>